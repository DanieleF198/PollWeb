/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.controllers;

import com.mycompany.pollweb.dao.PollWebDataLayer;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mycompany.pollweb.result.TemplateManagerException;
import com.mycompany.pollweb.result.TemplateResult;
import com.mycompany.pollweb.data.DataException;
import com.mycompany.pollweb.impl.GruppoImpl;
import com.mycompany.pollweb.impl.UtenteImpl;
import com.mycompany.pollweb.model.Domanda;
import com.mycompany.pollweb.model.Gruppo;
import com.mycompany.pollweb.model.Risposta;
import com.mycompany.pollweb.model.RispostaDomanda;
import com.mycompany.pollweb.model.Sondaggio;
import com.mycompany.pollweb.model.Utente;
import com.mycompany.pollweb.result.FailureResult;
import com.mycompany.pollweb.result.SplitSlashesFmkExt;
import com.mycompany.pollweb.result.StreamResult;
import com.mycompany.pollweb.security.SecurityLayer;
import static com.mycompany.pollweb.security.SecurityLayer.checkSession;
import com.opencsv.CSVWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import javax.mail.*;
import javax.mail.internet.*;
import org.json.JSONArray;
/**
 *
 * @author joker
 */

public class Dashboard extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, DataException{
         try {
            HttpSession s = checkSession(request);
            if (s!= null) {
                if ((Integer)s.getAttribute("groupid") == 3){
                    action_redirect_adminDashboard(request, response);
                    return;
                }
                if ((Integer)s.getAttribute("groupid") == 1){
                    response.sendRedirect("partecipantDashboard");
                }
                else{
                    if(request.getParameter("header-search-tuoi-sondaggi") != null){
                        System.out.println("REQUEST HEADER_SEARCH (tuoi): " + request.getParameter("header-search-tuoi-sondaggi"));
                        action_tuoi_sondaggi_search(request, response);
                        return;
                    }else if(request.getParameter("header-search-sondaggi-privati") != null){
                        System.out.println("REQUEST HEADER_SEARCH(privati): " + request.getParameter("header-search-sondaggi-privati"));
                        action_sondaggi_privati_search(request, response);
                        return;

                    }else if(request.getParameter("header-search-sondaggi-compilati") != null){
                        System.out.println("REQUEST HEADER_SEARCH(compilati): " + request.getParameter("header-search-sondaggi-compilati"));
                        action_sondaggi_compilati_search(request, response);
                        return;

                    } else if(request.getParameter("changeVisibility")!=null){
                        PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
                        Sondaggio sondaggio = dl.getSondaggioDAO().getSondaggio(Integer.parseInt(request.getParameter("changeVisibility")));
                        if(!(sondaggio.isVisibilita())){
                            ArrayList<Domanda> domande =(ArrayList<Domanda>) dl.getDomandaDAO().getDomandaByIdSondaggio(sondaggio.getKey());
                            Collections.sort(domande);
                            boolean errors = false;
                            if(domande.isEmpty()){
                                errors = true;
                            } else {
                                for (int i = 0; i < domande.size(); i++){
                                    Domanda d = domande.get(i);
                                    if(d.getTitolo().isBlank()){
                                        errors = true;
                                    }
                                    if (d.getTipo().equals("closeSingle") || d.getTipo().equals("closeMultiple")){
                                        JSONArray opzioni = d.getOpzioni().getJSONArray("opzioni");
                                        if(opzioni.length()<2){
                                            errors = true;
                                        }
                                    }
                                    if (d.getTipo().equals("openShort")){
                                        if(d.getVincolo()!=null && !d.getVincolo().isBlank()){
                                            Pattern p = Pattern.compile("\\d+");
                                            Matcher m = p.matcher(d.getVincolo());
                                            if(m.find(0)){
                                                String constraint = m.group(0);
                                                int value = Integer.parseInt(constraint);
                                                if(value > 64){
                                                    errors = true;
                                                }
                                            }
                                        }
                                    }
                                    if (d.getTipo().equals("openLong")){
                                        if(d.getVincolo()!=null && !d.getVincolo().isBlank()){
                                            Pattern p = Pattern.compile("\\d+");
                                            Matcher m = p.matcher(d.getVincolo());
                                            if(m.find(0)){
                                                String constraint = m.group(0);
                                                int value = Integer.parseInt(constraint);
                                                if(value > 256){
                                                    errors = true;
                                                }
                                            }
                                        }
                                    }
                                    if(d.getTipo().equals("openNumber")){
                                        if(d.getVincolo()!=null && !d.getVincolo().isBlank()){
                                            if(d.getVincolo().contains("-") && !(d.getVincolo().contains("Null"))){
                                                int vincoloNumber1 = Integer.parseInt(d.getVincolo().substring(d.getVincolo().indexOf(":")+2, d.getVincolo().indexOf("-")-1));
                                                int vincoloNumber2 = Integer.parseInt(d.getVincolo().substring(d.getVincolo().indexOf("-")+3));
                                                if((vincoloNumber1 > vincoloNumber2) ||  (vincoloNumber1 == vincoloNumber2)){
                                                    errors = true;
                                                }
                                            }
                                        }
                                    }
                                    if(d.getTipo().equals("openDate")){
                                        if(d.getVincolo()!=null && !d.getVincolo().isBlank()){
                                            if(d.getVincolo().contains("--") && !(d.getVincolo().contains("Null"))){
                                                String vincoloDate1 = d.getVincolo().substring(12,22);
                                                String vincoloDate2 = d.getVincolo().substring(26,36);
                                                Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(vincoloDate1);
                                                Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(vincoloDate2);
                                                if((date1.after(date1)) ||  (date1.equals(date2))){
                                                    errors = true;
                                                }
                                            }
                                        }
                                    }


                                }
                            }
                            if(errors){
                                response.sendRedirect("dashboard");
                                return;
                            }
                            sondaggio.setVisibilita(true);
                            dl.getSondaggioDAO().storeSondaggio(sondaggio);
                            if(sondaggio.isPrivato()){
                                //simulazione invio della email (il codice è questo, solo non vi è un server locale per il SMTP, quindi stampiamo su file esterno il risultato finale
                                ArrayList<Utente> partecipants = (ArrayList<Utente>) dl.getUtenteDAO().getListaPartecipantiWithMailToSendBySondaggioId(Integer.parseInt(request.getParameter("changeVisibility")));
                                final String from = "daniele.fossemo@outlook.it";
                                
                                final String pass = "Password2021!";
                                String host = "localhost";
                                Properties properties = System.getProperties();
                                properties.put("mail.smtp.starttls.enable", "true");
                                properties.put("mail.smtp.ssl.enable", "true");
                                properties.put("mail.smtp.host", host);
                                properties.put("mail.smtp.port", "587");
                                properties.put("mail.smtp.auth", "true");
                                Authenticator auth = new Authenticator() {
                                        //override the getPasswordAuthentication method
                                        protected PasswordAuthentication getPasswordAuthentication() {
                                                return new PasswordAuthentication(from, pass);
                                        }
                                };
                                Session session = Session.getDefaultInstance(properties, auth);
                                response.setContentType("text/html");
                                PrintWriter out = response.getWriter();
                                Utente u = dl.getUtenteDAO().getUtente(sondaggio.getIdUtente());
                                
                                for(int i = 0; i < partecipants.size(); i++){
                                    try {
                                        String to = partecipants.get(i).getEmail();
                                        
                                        String password = partecipants.get(i).getPassword();
                                        
                                        String contextPath = getServletContext().getRealPath("/");
                                        File f = new File(contextPath.substring(0,contextPath.length()-28)+"src\\main\\webapp\\emails\\emailSurvey"+(request.getParameter("changeVisibility"))+".txt"); //daniele -> joker; Davide-> Cronio
                                        if (!f.createNewFile()) { System.out.println("File already exists"); }
                                        PrintStream standard = System.out;
                                        PrintStream fileStream = new PrintStream(new FileOutputStream(contextPath.substring(0,contextPath.length()-28)+"src\\main\\webapp\\emails\\emailSurvey"+(request.getParameter("changeVisibility"))+".txt", true));
                                        System.setOut(fileStream);
                                        
                                        String title = "Invito Sondaggio privato Quack, Duck, Poll";
                                        

                                        String res = 
                                                "Salve\n" + "sei stato invitato a partecipare ad un sondaggio privato su Quack, Duck, Poll dall\'utente " + u.getUsername() + "\n" +
                                                "Le tue credenziali di accesso al sondaggio sono:\n" + "Email: " + partecipants.get(i).getEmail() + "\n" + "Password: " + password + "\n" +
                                                "Puoi effettuare il login al seguente link: http://localhost:8080/PollWeb/loginForPartecipants \n" + "\n" +
                                                "Questa mail viene inviata automaticamente dal sito Quack, Duck, Poll tramite la richiesta d\'invito da parte dell\'utente " + u.getUsername() + ", se pensi che la tua privacy sia stata lesa in un qualche modo contattaci all\'indirizzo: Quack@Duck.poll\n";

                                        String output =
                                           "Mail inviata da: "+ from + "\n" +
                                           "Mail ricevuta da: "+ to + "\n" +
                                           "Oggetto: " + title + "\n" +
                                           "Testo:\n" + res + "\n" + 
                                           "---------------------";
                                        
                                        System.out.println(output);
                                        
                                        System.setOut(standard); 

                                        MimeMessage message = new MimeMessage(session);

                                        message.setFrom(new InternetAddress(from));

                                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                                        message.setSubject(title);

                                        message.setText(res);

                                        Transport transport = session.getTransport("smtp");
                                        transport.connect(host, from, pass);
                                        transport.sendMessage(message, message.getAllRecipients());
                                        transport.close();
                                    } catch (MessagingException mex) {
                                        mex.printStackTrace();
                                    }
                                }
                                dl.getUtenteDAO().ListaPartecipantiSetMailSend(Integer.parseInt(request.getParameter("changeVisibility")));
                            }
                        } else {
                            sondaggio.setVisibilita(false);
                            dl.getSondaggioDAO().storeSondaggio(sondaggio);
                        }
                        response.sendRedirect("dashboard");
                        return;
                    } else if(request.getParameter("modSurvey")!=null){
                        s.setAttribute("sondaggio-in-creazione", Integer.parseInt(request.getParameter("modSurvey")));
                        s.setAttribute("continue", "yes");
                        s.setAttribute("modVersion", "yes");
                        response.sendRedirect("./makerPoll/firstSection");
                    } else if(request.getParameter("changePartecipants")!=null){
                        System.out.println("changePartecipants Cliccato");
                    } else if(request.getParameter("removeSurvey")!=null){
                        PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
                        dl.getSondaggioDAO().deleteSondaggio(Integer.parseInt(request.getParameter("removeSurvey")));
                        response.sendRedirect("dashboard");
                    } else if(request.getParameter("downloadAnswer")!=null){
                        PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
                        ArrayList<Domanda> domande = (ArrayList<Domanda>) dl.getDomandaDAO().getDomandaByIdSondaggio(Integer.parseInt(request.getParameter("downloadAnswer")));
                        if(!domande.isEmpty()){
                            ArrayList<Risposta> risposte = new ArrayList<Risposta>();
                            ArrayList<RispostaDomanda> risposteDomande = (ArrayList<RispostaDomanda>) dl.getRispostaDomandaDAO().getRispostaDomandaByDomandaId(domande.get(0).getKey());
                            Collections.sort(domande);
                            if(risposteDomande.isEmpty()){
                                String contextPath = getServletContext().getRealPath("/");
                                File f = new File(contextPath.substring(0,contextPath.length()-28)+"src\\main\\webapp\\download\\risposte"+request.getParameter("downloadAnswer")+".csv");
                                FileWriter outputFile = new FileWriter(f);
                                CSVWriter writer = new CSVWriter(outputFile); 
                                ArrayList<String> headerList = new ArrayList<String>();
                                headerList.add("email");
                                headerList.add("data");
                                for (int i = 0; i<domande.size(); i++){
                                    Domanda d = domande.get(i);
                                    headerList.add(d.getTitolo());
                                }
                                String[] header = new String[headerList.size()];
                                for(int i = 0; i<headerList.size(); i++){
                                    header[i] = headerList.get(i);
                                }
                                writer.writeNext(header);
                                writer.close();
                                StreamResult result = new StreamResult(getServletContext());
                                result.setResource(f);
                                result.activate(request, response);
                            } else {
                                for(int i = 0; i<risposteDomande.size();i++){
                                    Risposta r = dl.getRispostaDAO().getRisposta(risposteDomande.get(i).getIdRisposta());
                                    if(!risposte.contains(r)){
                                        risposte.add(r);;
                                    }
                                }
                                String contextPath = getServletContext().getRealPath("/");
                                File f = new File(contextPath.substring(0,contextPath.length()-28)+"src\\main\\webapp\\download\\risposte"+request.getParameter("downloadAnswer")+".csv");
                                FileWriter outputFile = new FileWriter(f);
                                CSVWriter writer = new CSVWriter(outputFile); 
                                ArrayList<String> headerList = new ArrayList<String>();
                                headerList.add("email");
                                headerList.add("data");
                                for (int i = 0; i<domande.size(); i++){
                                    Domanda d = domande.get(i);
                                    headerList.add(d.getTitolo());
                                }
                                String[] header = new String[headerList.size()];
                                for(int i = 0; i<headerList.size(); i++){
                                    header[i] = headerList.get(i);
                                }
                                writer.writeNext(header);
                                for (int i = 0; i<risposte.size(); i++){
                                    ArrayList<String> data = new ArrayList<String>();
                                    data.add(risposte.get(i).getUsernameUtenteRisposta());
                                    Date dataRisposta = risposte.get(i).getData();
                                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    String strDate = dateFormat.format(dataRisposta);
                                    data.add(strDate);
                                    for (int j = 0; j<domande.size(); j++){
                                        RispostaDomanda r = dl.getRispostaDomandaDAO().getRispostaDomanda(risposte.get(i).getKey(), domande.get(j).getKey());
                                        if(r!=null){
                                            JSONArray jsonRisposta = r.getRisposta().getJSONArray("risposta");
                                            String rispostaFinale = "";
                                            for(int k = 0; k <jsonRisposta.length(); k++){
                                                if(k == jsonRisposta.length()-1){
                                                    rispostaFinale += SecurityLayer.stripSlashes(jsonRisposta.getString(k));
                                                } else {
                                                    rispostaFinale += SecurityLayer.stripSlashes(jsonRisposta.getString(k)+ ";");
                                                }
                                            }
                                            data.add(rispostaFinale);
                                        } else {
                                            data.add("N/A");
                                        }
                                    }
                                    String[] dataArray = new String[data.size()];
                                    for(int j = 0; j < data.size(); j++){
                                        dataArray[j] = data.get(j);
                                    }
                                    writer.writeNext(dataArray); 
                                }
                                writer.close();
                                StreamResult result = new StreamResult(getServletContext());
                                result.setResource(f);
                                result.activate(request, response);
                            }
                        } else {
                            
                            response.sendRedirect("dashboard");
                        }
                    }
                    action_default(request, response);
                    return;
                }
            } else {
                action_redirect_login(request, response);
            }
            

        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        } catch (java.text.ParseException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        try {
            if(!(SecurityLayer.checkSession(request) != null)){ //controllo in più per essere sicuri
                action_redirect_login(request,response);
            }
            else{
                PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
                TemplateResult res = new TemplateResult(getServletContext());
                HttpSession s = request.getSession(false);
                GruppoImpl g = new GruppoImpl();
                request.setAttribute("username", (String)s.getAttribute("username"));
                request.setAttribute("email", (String)s.getAttribute("email"));
                request.setAttribute("nome", (String)s.getAttribute("nome"));
                request.setAttribute("cognome", (String)s.getAttribute("cognome"));
                request.setAttribute("eta", (Integer)s.getAttribute("eta"));
                request.setAttribute("gruppo", g.getNomeGruppoByID((Integer)s.getAttribute("groupid")));

                ArrayList<Sondaggio> sondaggi = (ArrayList<Sondaggio>) dl.getSondaggioDAO().getSondaggiByIdUtente((Integer)s.getAttribute("userid")); //Lista di tutti i sondaggi
                request.setAttribute("sondaggi", sondaggi);
                if( sondaggi.isEmpty() ){
                    request.setAttribute("noTuoiSondaggi", "yes");
                }
                
                ArrayList<Sondaggio> sondaggiPriv = (ArrayList<Sondaggio>) dl.getSondaggioDAO().getSondaggiPrivati((Integer)s.getAttribute("userid")); //Lista dei sondaggi privati
                request.setAttribute("sondaggiPriv", sondaggiPriv);
                if( sondaggiPriv.isEmpty() ){
                    request.setAttribute("noSondaggiPriv", "yes");
                }
                
                ArrayList<Sondaggio> sondaggiComp = (ArrayList<Sondaggio>) dl.getSondaggioDAO().getSondaggiCompilati((Integer)s.getAttribute("userid")); //Lista dei sondaggi compilati
                request.setAttribute("sondaggiComp", sondaggiComp);
                if( sondaggiComp.isEmpty() ){
                    request.setAttribute("noSondaggiComp", "yes");
                }

                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                res.activate("dashboard.ftl", request, response);
            }
        } catch (TemplateManagerException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void action_tuoi_sondaggi_search(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        try {
            if(!(SecurityLayer.checkSession(request) != null)){ //controllo in più per essere sicuri
                action_redirect_login(request,response);
            }else{
                PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
                TemplateResult res = new TemplateResult(getServletContext());
                HttpSession s = request.getSession(false);
                GruppoImpl g = new GruppoImpl();
                request.setAttribute("username", (String)s.getAttribute("username"));
                request.setAttribute("email", (String)s.getAttribute("email"));
                request.setAttribute("nome", (String)s.getAttribute("nome"));
                request.setAttribute("cognome", (String)s.getAttribute("cognome"));
                request.setAttribute("eta", (Integer)s.getAttribute("eta"));
                request.setAttribute("gruppo", g.getNomeGruppoByID((Integer)s.getAttribute("groupid")));
                
                ArrayList<Sondaggio> sondaggi = (ArrayList<Sondaggio>) dl.getSondaggioDAO().getSondaggiByIdUtente((Integer)s.getAttribute("userid")); //Lista di tutti i sondaggi
                sondaggi = (ArrayList<Sondaggio>) dl.getSondaggioDAO().searchSondaggi(sondaggi, (String)request.getParameter("header-search-tuoi-sondaggi"));
                request.setAttribute("sondaggi", sondaggi);
                
                if(!request.getParameter("header-search-tuoi-sondaggi").isEmpty()){
                    request.setAttribute("ricercaTuoiSondaggi", "yes");
                }
                else{
                    request.setAttribute("ricercaTuoiSondaggi", "");
                }
                
                if(sondaggi.isEmpty()){ 
                    request.setAttribute("listaTuoiSondaggiVuota", "yes");  
                    System.out.println("LISTA SONDAGGI VUOTA: " + request.getAttribute("listaTuoiSondaggiVuota"));
                }
                else{
                    request.setAttribute("listaTuoiSondaggiVuota", "");
                    System.out.println("LISTA SONDAGGI VUOTA: " + request.getAttribute("listaTuoiSondaggiVuota"));
                }
                
                ArrayList<Sondaggio> sondaggiPriv = (ArrayList<Sondaggio>) dl.getSondaggioDAO().getSondaggiPrivati((Integer)s.getAttribute("userid")); //Lista dei sondaggi privati
                request.setAttribute("sondaggiPriv", sondaggiPriv);
                if( sondaggiPriv.isEmpty() ){
                    request.setAttribute("noSondaggiPriv", "yes");
                }
                
                ArrayList<Sondaggio> sondaggiComp = (ArrayList<Sondaggio>) dl.getSondaggioDAO().getSondaggiCompilati((Integer)s.getAttribute("userid")); //Lista dei sondaggi compilati
                request.setAttribute("sondaggiComp", sondaggiComp);
                if( sondaggiComp.isEmpty() ){
                    request.setAttribute("noSondaggiComp", "yes");
                }

                res.activate("dashboard.ftl", request, response);
                
                
            }
        } catch (TemplateManagerException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void action_sondaggi_privati_search(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        try {
            if(!(SecurityLayer.checkSession(request) != null)){ //controllo in più per essere sicuri
                action_redirect_login(request,response);
            }else{
                PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
                TemplateResult res = new TemplateResult(getServletContext());
                HttpSession s = request.getSession(false);
                GruppoImpl g = new GruppoImpl();
                request.setAttribute("username", (String)s.getAttribute("username"));
                request.setAttribute("email", (String)s.getAttribute("email"));
                request.setAttribute("nome", (String)s.getAttribute("nome"));
                request.setAttribute("cognome", (String)s.getAttribute("cognome"));
                request.setAttribute("eta", (Integer)s.getAttribute("eta"));
                request.setAttribute("gruppo", g.getNomeGruppoByID((Integer)s.getAttribute("groupid")));
                
                ArrayList<Sondaggio> sondaggi = (ArrayList<Sondaggio>) dl.getSondaggioDAO().getSondaggiByIdUtente((Integer)s.getAttribute("userid")); //Lista di tutti i sondaggi
                request.setAttribute("sondaggi", sondaggi);
                
                ArrayList<Sondaggio> sondaggiPriv = (ArrayList<Sondaggio>) dl.getSondaggioDAO().getSondaggiPrivati((Integer)s.getAttribute("userid")); //Lista dei sondaggi privati
                sondaggiPriv = (ArrayList<Sondaggio>) dl.getSondaggioDAO().searchSondaggi(sondaggi, (String)request.getParameter("header-search-sondaggi-privati"));
                request.setAttribute("sondaggiPriv", sondaggiPriv);
                
                if( sondaggiPriv.isEmpty() ){
                    request.setAttribute("noSondaggiPriv", "yes");
                }
                
                if(!request.getParameter("header-search-sondaggi-privati").isEmpty()){
                    request.setAttribute("ricercaSondaggiPrivati", "yes");
                }
                else{
                    request.setAttribute("ricercaSondaggiPrivati", "");
                }
                
                if(sondaggiPriv.isEmpty()){ 
                    request.setAttribute("listaSondaggiPrivatiVuota", "yes");  
                    System.out.println("LISTA SONDAGGI VUOTA (privati): " + request.getAttribute("listaSondaggiPrivatiVuota"));
                }
                else{
                    request.setAttribute("listaSondaggiPrivatiVuota", "");
                    System.out.println("LISTA SONDAGGI VUOTA (privati): " + request.getAttribute("listaSondaggiPrivatiVuota"));
                }
                
                ArrayList<Sondaggio> sondaggiComp = (ArrayList<Sondaggio>) dl.getSondaggioDAO().getSondaggiCompilati((Integer)s.getAttribute("userid")); //Lista dei sondaggi compilati
                request.setAttribute("sondaggiComp", sondaggiComp);
                if( sondaggiComp.isEmpty() ){
                    request.setAttribute("noSondaggiComp", "yes");
                }
                

                res.activate("dashboard.ftl", request, response);
                
                
            }
        } catch (TemplateManagerException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void action_sondaggi_compilati_search(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        try {
            if(!(SecurityLayer.checkSession(request) != null)){ //controllo in più per essere sicuri
                action_redirect_login(request,response);
            }else{
                PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
                TemplateResult res = new TemplateResult(getServletContext());
                HttpSession s = request.getSession(false);
                GruppoImpl g = new GruppoImpl();
                request.setAttribute("username", (String)s.getAttribute("username"));
                request.setAttribute("email", (String)s.getAttribute("email"));
                request.setAttribute("nome", (String)s.getAttribute("nome"));
                request.setAttribute("cognome", (String)s.getAttribute("cognome"));
                request.setAttribute("eta", (Integer)s.getAttribute("eta"));
                request.setAttribute("gruppo", g.getNomeGruppoByID((Integer)s.getAttribute("groupid")));
                
                ArrayList<Sondaggio> sondaggi = (ArrayList<Sondaggio>) dl.getSondaggioDAO().getSondaggiByIdUtente((Integer)s.getAttribute("userid")); //Lista di tutti i sondaggi
                request.setAttribute("sondaggi", sondaggi);
                
                ArrayList<Sondaggio> sondaggiPriv = (ArrayList<Sondaggio>) dl.getSondaggioDAO().getSondaggiPrivati((Integer)s.getAttribute("userid")); //Lista dei sondaggi privati
                request.setAttribute("sondaggiPriv", sondaggiPriv);
                
                ArrayList<Sondaggio> sondaggiComp= (ArrayList<Sondaggio>) dl.getSondaggioDAO().getSondaggiCompilati((Integer)s.getAttribute("userid")); //Lista dei sondaggi compilati
                sondaggiComp = (ArrayList<Sondaggio>) dl.getSondaggioDAO().searchSondaggi(sondaggiComp, (String)request.getParameter("header-search-sondaggi-compilati"));
                request.setAttribute("sondaggiComp", sondaggiComp);
                
                if( sondaggiPriv.isEmpty() ){
                    request.setAttribute("noSondaggiComp", "yes");
                }
                
                if(!request.getParameter("header-search-sondaggi-compilati").isEmpty()){
                    request.setAttribute("ricercaSondaggiCompilati", "yes");
                }
                else{
                    request.setAttribute("ricercaSondaggiCompilati", "");
                }
                
                if(sondaggiComp.isEmpty()){ 
                    request.setAttribute("listaSondaggiCompilatiVuota", "yes");  
                    System.out.println("LISTA SONDAGGI VUOTA (compilati): " + request.getAttribute("listaSondaggiCompilatiVuota"));
                }
                else{
                    request.setAttribute("listaSondaggiCompilatiVuota", "");
                    System.out.println("LISTA SONDAGGI VUOTA (compilati): " + request.getAttribute("listaSondaggiCompilatiVuota"));
                }
                
                

                res.activate("dashboard.ftl", request, response);
                
                
            }
        } catch (TemplateManagerException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    private void action_redirect_login(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        try {
            request.setAttribute("urlrequest", request.getRequestURL());
            RequestDispatcher rd = request.getRequestDispatcher("/login");
            rd.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
    
    private void action_ban(HttpServletRequest request, HttpServletResponse response) throws  IOException, TemplateManagerException {
        TemplateResult res = new TemplateResult(getServletContext());
        SecurityLayer.disposeSession(request);
        res.activate("ban.ftl", request, response);
    }
    
    private void action_redirect_adminDashboard(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        try {
            request.setAttribute("urlrequest", request.getRequestURL());
            RequestDispatcher rd = request.getRequestDispatcher("/adminDashboard");
            rd.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
