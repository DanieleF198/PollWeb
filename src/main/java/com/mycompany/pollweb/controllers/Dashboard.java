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
import com.mycompany.pollweb.model.Gruppo;
import com.mycompany.pollweb.model.Sondaggio;
import com.mycompany.pollweb.model.Utente;
import com.mycompany.pollweb.result.FailureResult;
import com.mycompany.pollweb.security.SecurityLayer;
import static com.mycompany.pollweb.security.SecurityLayer.checkSession;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import javax.mail.*;
import javax.mail.internet.*;
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
                            sondaggio.setVisibilita(true);
                            dl.getSondaggioDAO().storeSondaggio(sondaggio);
                            if(sondaggio.isPrivato()){
                                //simulazione invio della email (il codice è questo, solo non vi è un server locale per il SMTP, quindi stampiamo su file esterno il risultato finale
                                ArrayList<Utente> partecipants = (ArrayList<Utente>) dl.getUtenteDAO().getListaPartecipantiWithMailToSendBySondaggioId(Integer.parseInt(request.getParameter("changeVisibility")));
                                String from = "daniele.fossemo@outlook.it";
                                String host = "localhost";
                                Properties properties = System.getProperties();
                                properties.setProperty("mail.smtp.host", host);
                                Session session = Session.getDefaultInstance(properties);
                                response.setContentType("text/html");
                                PrintWriter out = response.getWriter();
                                Utente u = dl.getUtenteDAO().getUtente(sondaggio.getIdUtente());
                                
                                for(int i = 0; i < partecipants.size(); i++){
                                    try {
                                        String to = partecipants.get(i).getEmail();
                                        
                                        String password = partecipants.get(i).getPassword();
                                        
                                        MimeMessage message = new MimeMessage(session);

                                        message.setFrom(new InternetAddress(from));

                                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                                        message.setSubject("This is the Subject Line!");

                                        message.setText("This is actual message");

                                        //Transport.send(message);
                                        File f = new File("C:\\Users\\joker\\Documents\\NetBeansProjects\\PollWeb\\src\\main\\webapp\\emails\\emailSurvey"+Integer.parseInt(request.getParameter("changeVisibility"))+".txt"); 
                                        if (!f.createNewFile()) { System.out.println("File already exists"); }
                                        PrintStream standard = System.out;
                                        PrintStream fileStream = new PrintStream(new FileOutputStream("C:\\Users\\joker\\Documents\\NetBeansProjects\\PollWeb\\src\\main\\webapp\\emails\\emailSurvey"+Integer.parseInt(request.getParameter("changeVisibility"))+".txt", true));
                                        System.setOut(fileStream);
                                        
                                        String title = "Invito Sondaggio privato Quack, Duck, Poll";
                                        String res = 
                                                "Salve\n" + "sei stato invitato a partecipare ad un sondaggio privato su Quack, Duck, Poll dall\'utente " + u.getUsername() + "\n" +
                                                "Le tue credenziali di accesso al sondaggio sono:\n" + "Email: " + to + "\n" + "Password: " + password + "\n" +
                                                "Puoi effettuare il login al seguente link: http://localhost:8080/PollWeb/loginForPartecipants \n" + "\n" +
                                                "Questa mail viene inviata automaticamente dal sito Quack, Duck, Poll tramite la richiesta d\'invito da parte dell\'utente " + u.getUsername() + ", se pensi che la tua privacy sia stata lesa in un qualche modo contattaci all\'indirizzo: Quack@Duck.poll\n";
//                                        Come sarebbe l'invio (esempio) vero
//                                        out.println(docType +
//                                           "<html>\n" +
//                                              "<head><title>" + title + "</title></head>\n" +
//                                                "<body bgcolor = \"#f0f0f0\">\n" +
//                                                    "<h1 align = \"center\">" + title + "</h1>\n" +
//                                                    "<p align = \"center\">" + res + "</p>\n" +
//                                                "</body>\n" +
//                                            "</html>\n" +
//                                            "---------------------"
//                                        );
                                        System.out.println(
                                           "Mail inviata da: "+ from + "\n" +
                                           "Mail ricevuta da: "+ to + "\n" +
                                           "Oggetto: " + title + "\n" +
                                           "Testo:\n" + res + "\n" + 
                                           "---------------------"
                                        );
                                        System.setOut(standard);  
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
