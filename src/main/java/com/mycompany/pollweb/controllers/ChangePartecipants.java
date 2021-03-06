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
import com.mycompany.pollweb.impl.UtenteImpl;
import com.mycompany.pollweb.model.Sondaggio;
import com.mycompany.pollweb.model.Utente;
import com.mycompany.pollweb.result.FailureResult;
import com.mycompany.pollweb.security.SecurityLayer;
import static com.mycompany.pollweb.security.SecurityLayer.checkSession;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author joker
 */

public class ChangePartecipants extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, DataException{
        try {
            HttpSession s = checkSession(request);
            if (s!= null) {
                if ((Integer)s.getAttribute("groupid") == 1){
                    response.sendRedirect("partecipantDashboard");
                    return;
                }
                if("POST".equals(request.getMethod()) && request.getParameter("changePartecipants")!=null){
                    action_change_partecipants(request, response);
                    return;
                } else {
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
        } catch (CsvException ex) {
            Logger.getLogger(ChangePartecipants.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ChangePartecipants.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
       try {
            PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
            Sondaggio s = dl.getSondaggioDAO().getSondaggio(Integer.parseInt(request.getParameter("changePartecipantsForm")));
            if(s.isPrivato()){
                ArrayList<Utente> listaPartecipanti = (ArrayList<Utente>) dl.getUtenteDAO().getListaPartecipantiBySondaggioId(Integer.parseInt(request.getParameter("changePartecipantsForm")), false);
                System.out.println("sondaggioId " + request.getParameter("changePartecipantsForm"));
                if(!(listaPartecipanti.isEmpty())){
                    for(int i = 0; i < listaPartecipanti.size(); i++){
                        listaPartecipanti.get(i).setNome(SecurityLayer.stripSlashes(listaPartecipanti.get(i).getNome()));
                        listaPartecipanti.get(i).setPassword(SecurityLayer.stripSlashes(listaPartecipanti.get(i).getPassword()));
                        System.out.println("Partecipante " + i + ", nome: " + listaPartecipanti.get(i).getNome());
                        System.out.println("Partecipante " + i + ", eMail: " + listaPartecipanti.get(i).getEmail());
                        System.out.println("Partecipante " + i + ", password: " + listaPartecipanti.get(i).getPassword());
                    }
                    request.setAttribute("partecipants", listaPartecipanti);
                }
                request.setAttribute("idSondaggio", Integer.parseInt(request.getParameter("changePartecipantsForm")));
                TemplateResult res = new TemplateResult(getServletContext());
                res.activate("changePartecipants.ftl", request, response);
            } else {
                response.sendRedirect("dashboard");
            }
        } catch (TemplateManagerException ex) {
            Logger.getLogger(ChangePartecipants.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void action_default2(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        TemplateResult res = new TemplateResult(getServletContext());
        PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
        if(!(request.getParameter("withCSV") != null && request.getParameter("withCSV").equals("withCSV"))){
                if(request.getParameter("usersNameNew[]")!=null || request.getParameter("usersMailNew[]")!=null || request.getParameter("usersPassNew[]")!=null){
                    String[] name = request.getParameterValues("usersNameNew[]");
                    String[] mail = request.getParameterValues("usersMailNew[]");
                    String[] pass = request.getParameterValues("usersPassNew[]");
                    int maxIndex; 
                    maxIndex = Integer.max(name.length, mail.length);
                    maxIndex = Integer.max(maxIndex, pass.length);
                    ArrayList<Utente> partecipants = new ArrayList<Utente>();
                    System.out.println("MAXINDEX = " + maxIndex);
                    boolean allEmpty = true;
                    for(int i = 0; i < maxIndex; i++){
                        if((!(name[i].isBlank())) || (!(mail[i].isBlank())) || (!(pass[i].isBlank()))){
                            allEmpty = false;
                        }
                    }
                    if(!(allEmpty)){
                        for(int i = 0; i < maxIndex; i++){
                            System.out.println("primo for, ciclo " + i);
                            Utente u = new UtenteImpl();
                            if(name.length > i){
                                u.setNome(name[i]);
                            } else {
                                u.setNome("");
                            }
                            if(mail.length > i){
                                u.setEmail(mail[i]);
                            } else {
                                u.setEmail("");
                            }
                            if(pass.length > i){
                                u.setPassword(pass[i]);
                            } else {
                                u.setPassword("");
                            }
                            partecipants.add(u);
                        }
                        //mi prendo gli "oldPartecipants"
                        ArrayList<Utente> oldPartecipants = (ArrayList<Utente>) dl.getUtenteDAO().getListaPartecipantiBySondaggioId(Integer.parseInt(request.getParameter("changePartecipants")), false);
                        if(!oldPartecipants.isEmpty()){
                            for(int j = 0; j<oldPartecipants.size();j++){
                                partecipants.add(0, oldPartecipants.get(j));
                            }
                        }
                        if(!partecipants.isEmpty()){
                            ArrayList<Utente> tempPartecipants = new ArrayList<Utente>();
                            System.out.println("numero inserimenti" + partecipants.size());
                            for(int k = partecipants.size()-1; k >= 0 ; k--){
                                System.out.println("K = " + k);
                                System.out.println("Nome utente = " + partecipants.get(k).getNome());
                                if (partecipants.get(k).getNome().isBlank() && partecipants.get(k).getEmail().isBlank() && partecipants.get(k).getPassword().isBlank()){
                                    System.out.println("sono passato di qui");
                                    partecipants.remove(k); 
                                }
                            }
                            for(int k = 0; k < partecipants.size(); k++){
                                tempPartecipants.add(partecipants.get(k));
                            }
                            for(int j = 0; j < partecipants.size(); j++){
                                System.out.println("secondo for, ciclo " + j);
                                Utente checkU = partecipants.get(j);
                                if(checkU.getNome().isBlank() || checkU.getEmail().isBlank() || checkU.getPassword().isBlank()){
                                    System.out.println("sono passato di qui");
                                    request.setAttribute("partecipantsError", "yes");
                                }
                                String passwordOfU = checkU.getPassword();
                                Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                                Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
                                Pattern lowerCasePatten = Pattern.compile("[a-z ]");
                                Pattern digitCasePatten = Pattern.compile("[0-9 ]");
                                Pattern emailPattern = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
                                if(passwordOfU.length()<8 || !UpperCasePatten.matcher(passwordOfU).find() || !lowerCasePatten.matcher(passwordOfU).find() || !digitCasePatten.matcher(passwordOfU).find() || !specailCharPatten.matcher(passwordOfU).find()){
                                    System.out.println("errore password");
                                    request.setAttribute("partecipantsError", "yes");
                                }
                                if (!emailPattern.matcher(checkU.getEmail()).find()) {
                                    System.out.println("errore mail");
                                    request.setAttribute("partecipantsError", "yes");
                                }
                                tempPartecipants.remove(0);
                                for(int k = 0; k < tempPartecipants.size(); k++){
                                    System.out.println("terzo ciclo for, ciclo " + k);
                                    Utente tempUtente = tempPartecipants.get(k);
                                    if(tempUtente.getEmail().equals(checkU.getEmail()) || tempUtente.getPassword().equals(checkU.getPassword())){
                                        request.setAttribute("partecipantsError", "yes");
                                    }
                                }
                            }
                            request.setAttribute("partecipants", oldPartecipants);
                        }
                    }
                }
            }
            request.setAttribute("idSondaggio", Integer.parseInt(request.getParameter("changePartecipants")));
            
            res.activate("changePartecipants.ftl", request, response);
            return;
    }
    
    private void action_change_partecipants(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException, CsvException, AddressException, MessagingException {
        ArrayList<Utente> partecipants = new ArrayList<Utente>();   
        HttpSession s = checkSession(request);
        PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
        if (request.getContentType() != null && request.getContentType().toLowerCase().contains("multipart/form-data")) {
            System.out.println("passato enctype");
            boolean checkError = false;
            if(request.getParameter("withCSV") != null){    
                Part filePart = request.getPart("file");
                File uploaded_file = File.createTempFile("upload_", "", new File(getServletContext().getInitParameter("uploads.directory")));
                try (InputStream is = filePart.getInputStream();
                        OutputStream os = new FileOutputStream(uploaded_file)) {
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = is.read(buffer)) > 0) {
                        os.write(buffer, 0, read);
                    }
                }
                List<String[]> r = new ArrayList<String[]>();
                
                List<String[]> rH = new ArrayList<String[]>();
                                
                try (CSVReader readerHeader = new CSVReaderBuilder(new FileReader(uploaded_file)).withSkipLines(0).build()) {
                    rH = readerHeader.readAll();
                    System.out.println("lettura riuscita - header");
                }

                if(!(rH.isEmpty())){
                    String[] header = rH.get(0);
                    if(header.length == 3){
                        if((!(header[0].toLowerCase().equals("nome"))) || (!(header[1].toLowerCase().equals("mail"))) || (!(header[2].toLowerCase().equals("password")))){
                            request.setAttribute("notCSVError", "yes");
                            action_default(request, response);
                            return;
                        }
                    } else {
                        request.setAttribute("notCSVError", "yes");
                        action_default(request, response);
                        return;
                    }
                } else{
                    request.setAttribute("notCSVError", "yes");
                    action_default(request, response);
                    return;
                }

                try (CSVReader reader = new CSVReaderBuilder(new FileReader(uploaded_file)).withSkipLines(1).build()) {
                    r = reader.readAll();
                    System.out.println("lettura riuscita - 2");
                }

                boolean allEmpty = true;
                for(int i = 0; i < r.size(); i++){
                    String[] x = r.get(i);
                    if((!(x[0].isBlank())) || (!(x[1].isBlank())) || (!(x[2].isBlank()))){
                        allEmpty = false;
                    }
                }
                if(!(allEmpty)){
                    for(int i = 0; i < r.size(); i++){
                        String[] x = r.get(i);
                        Utente u = new UtenteImpl();
                        if(x.length == 3){
                            u.setNome(x[0]);
                            u.setEmail(x[1]);
                            u.setPassword(x[2]);
                        } else if (x.length == 2){
                            u.setNome(x[0]);
                            u.setEmail(x[1]);
                            u.setPassword("");
                        } else if (x.length == 1){
                            u.setNome(x[0]);
                            u.setEmail("");
                            u.setPassword("");
                        } else {
                            u.setNome("");
                            u.setEmail("");
                            u.setPassword("");
                        }
                        partecipants.add(u);
                        if(!partecipants.isEmpty()){
                            ArrayList<Utente> tempPartecipants = new ArrayList<Utente>();
                            for(int k = partecipants.size()-1; k >= 0 ; k--){
                                if (partecipants.get(k).getNome().isBlank() && partecipants.get(k).getEmail().isBlank() && partecipants.get(k).getPassword().isBlank()){
                                    partecipants.remove(k);
                                }
                            }
                            for(int k = 0; k < partecipants.size(); k++){
                                tempPartecipants.add(partecipants.get(k));
                            }
                            for(int j = 0; j < partecipants.size(); j++){
                                Utente checkU = partecipants.get(j);
                                if(checkU.getNome().isBlank() || checkU.getEmail().isBlank() || checkU.getPassword().isBlank()){
                                    partecipants.remove(checkU);
                                    checkError = true;
                                    request.setAttribute("CSVerror", "yes");
                                }
                                String passwordOfU = checkU.getPassword();
                                Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                                Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
                                Pattern lowerCasePatten = Pattern.compile("[a-z ]");
                                Pattern digitCasePatten = Pattern.compile("[0-9 ]");
                                Pattern emailPattern = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
                                if(passwordOfU.length()<8 || !UpperCasePatten.matcher(passwordOfU).find() || !lowerCasePatten.matcher(passwordOfU).find() || !digitCasePatten.matcher(passwordOfU).find() || !specailCharPatten.matcher(passwordOfU).find()){
                                    partecipants.remove(checkU);
                                    checkError = true;
                                    request.setAttribute("CSVerror", "yes");
                                }
                                if (!emailPattern.matcher(checkU.getEmail()).find()) {
                                    partecipants.remove(checkU);
                                    checkError = true;
                                    request.setAttribute("CSVerror", "yes");
                                }
                                tempPartecipants.remove(0);
                                for(int k = 0; k < tempPartecipants.size(); k++){
                                    Utente tempUtente = tempPartecipants.get(k);
                                    if(tempUtente.getEmail().equals(checkU.getEmail()) || tempUtente.getPassword().equals(checkU.getPassword())){
                                        partecipants.remove(checkU);
                                        partecipants.remove(tempUtente);
                                        checkError = true;
                                        request.setAttribute("CSVerror", "yes");
                                    }
                                }
                            }
                        }
                    }
                }
                uploaded_file.delete();
            } else if(request.getParameter("usersNameNew[]")!=null || request.getParameter("usersMailNew[]")!=null || request.getParameter("usersPassNew[]")!=null){
                String[] name = request.getParameterValues("usersNameNew[]");
                String[] mail = request.getParameterValues("usersMailNew[]");
                String[] pass = request.getParameterValues("usersPassNew[]");
                int maxIndex; 
                maxIndex = Integer.max(name.length, mail.length);
                maxIndex = Integer.max(maxIndex, pass.length);
                System.out.println("MAXINDEX = " + maxIndex);
                boolean allEmpty = true;
                for(int i = 0; i < maxIndex; i++){
                    if((!(name[i].isBlank())) || (!(mail[i].isBlank())) || (!(pass[i].isBlank()))){
                        allEmpty = false;
                    }
                }
                if(!(allEmpty)){
                    for(int i = 0; i < maxIndex; i++){
                        Utente u = new UtenteImpl();
                        if(name.length > i){
                            u.setNome(name[i]);
                        } else {
                            u.setNome("");
                        }
                        if(mail.length > i){
                            u.setEmail(mail[i]);
                        } else {
                            u.setEmail("");
                        }
                        if(pass.length > i){
                            u.setPassword(pass[i]);
                        } else {
                            u.setPassword("");
                        }
                        partecipants.add(u);
                    }
                    if(!partecipants.isEmpty()){
                        ArrayList<Utente> tempPartecipants = new ArrayList<Utente>();
                        for(int k = partecipants.size()-1; k >= 0 ; k--){
                            if (partecipants.get(k).getNome().isBlank() && partecipants.get(k).getEmail().isBlank() && partecipants.get(k).getPassword().isBlank()){
                                partecipants.remove(k);
                            }
                        }
                        for(int k = 0; k < partecipants.size(); k++){
                            tempPartecipants.add(partecipants.get(k));
                        }
                        for(int j = 0; j < partecipants.size(); j++){
                            Utente checkU = partecipants.get(j);
                            if(checkU.getNome().isBlank() || checkU.getEmail().isBlank() || checkU.getPassword().isBlank()){
                                checkError = true;
                            }
                            String passwordOfU = checkU.getPassword();
                            Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                            Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
                            Pattern lowerCasePatten = Pattern.compile("[a-z ]");
                            Pattern digitCasePatten = Pattern.compile("[0-9 ]");
                            Pattern emailPattern = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
                            if(passwordOfU.length()<8 || !UpperCasePatten.matcher(passwordOfU).find() || !lowerCasePatten.matcher(passwordOfU).find() || !digitCasePatten.matcher(passwordOfU).find() || !specailCharPatten.matcher(passwordOfU).find()){
                                checkError = true;
                            }
                            if (!emailPattern.matcher(checkU.getEmail()).find()) {
                                checkError = true;
                            }
                            tempPartecipants.remove(0);
                            for(int k = 0; k < tempPartecipants.size(); k++){
                                System.out.println("terzo ciclo for, ciclo " + k);
                                Utente tempUtente = tempPartecipants.get(k);
                                if(tempUtente.getEmail().equals(checkU.getEmail()) || tempUtente.getPassword().equals(checkU.getPassword())){
                                    checkError = true;
                                }
                            }
                        }
                    }
                }    
            }
            //get dei vecchi partecipanti (oldEmails per un controllo)
            ArrayList<Utente> oldPartecipants = new ArrayList<Utente>();
            ArrayList<String> oldEmails = new ArrayList<String>();
            if(request.getParameter("usersName[]")!=null || request.getParameter("usersMail[]")!=null){
                String[] nameOld = request.getParameterValues("usersName[]");
                String[] mailOld = request.getParameterValues("usersMail[]");
                //non serve fare controlli perché se c'erano prima erano già corretti, al più sono solo stati rimossi
                for(int i = 0; i < nameOld.length; i++){
                    Utente u = new UtenteImpl();
                    u.setNome(nameOld[i]);
                    u.setEmail(mailOld[i]);
                    oldPartecipants.add(u);
                    oldEmails.add(u.getEmail());
                }  
            }
            
            //ultimo controllo per partecipants
            if(!oldEmails.isEmpty()){
                for(int i = 0; i<partecipants.size(); i++){
                    if(oldEmails.contains(partecipants.get(i).getEmail())){
                        checkError = true;
                    }
                }
            }
            
            if(checkError){
                action_default2(request, response);
                return;
            }
            
            for(int i = 0; i<oldPartecipants.size();i++){
                    oldPartecipants.get(i).setNome(SecurityLayer.addSlashes(oldPartecipants.get(i).getNome()));
                    oldPartecipants.get(i).setPassword(SecurityLayer.addSlashes(oldPartecipants.get(i).getPassword()));
                }
            dl.getUtenteDAO().updateUtenteListaPartecipanti(oldPartecipants, Integer.parseInt(request.getParameter("changePartecipants")));

            if(!partecipants.isEmpty()){
                for(int i = 0; i<partecipants.size();i++){
                    partecipants.get(i).setNome(SecurityLayer.addSlashes(partecipants.get(i).getNome()));
                    partecipants.get(i).setPassword(SecurityLayer.addSlashes(partecipants.get(i).getPassword()));
                    dl.getUtenteDAO().insertUtenteListaPartecipanti(partecipants.get(i), Integer.parseInt(request.getParameter("changePartecipants")));
                }
                
            }
            ArrayList<Utente> partecipants2 = (ArrayList<Utente>) dl.getUtenteDAO().getListaPartecipantiWithMailToSendBySondaggioId(Integer.parseInt(request.getParameter("changePartecipants")));
            Sondaggio sondaggio = dl.getSondaggioDAO().getSondaggio(Integer.parseInt(request.getParameter("changePartecipants")));
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

            for(int i = 0; i < partecipants2.size(); i++){
                try {
                    String to = partecipants2.get(i).getEmail();

                    String password = partecipants2.get(i).getPassword();

                    String contextPath = getServletContext().getRealPath("/");
                    File f = new File(contextPath.substring(0,contextPath.length()-28)+"src\\main\\webapp\\emails\\emailSurvey"+Integer.parseInt(request.getParameter("changePartecipants"))+".txt"); //daniele -> joker; Davide-> Cronio
                    if (!f.createNewFile()) { System.out.println("File already exists"); }
                    PrintStream standard = System.out;
                    PrintStream fileStream = new PrintStream(new FileOutputStream(contextPath.substring(0,contextPath.length()-28)+"src\\main\\webapp\\emails\\emailSurvey"+Integer.parseInt(request.getParameter("changePartecipants"))+".txt", true));
                    System.setOut(fileStream);

                    String title = "Invito Sondaggio privato Quack, Duck, Poll";


                    String res = 
                            "Salve\n" + "sei stato invitato a partecipare ad un sondaggio privato su Quack, Duck, Poll dall\'utente " + u.getUsername() + "\n" +
                            "Le tue credenziali di accesso al sondaggio sono:\n" + "Email: " + partecipants2.get(i).getEmail() + "\n" + "Password: " + password + "\n" +
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
            dl.getUtenteDAO().ListaPartecipantiSetMailSend(Integer.parseInt(request.getParameter("changePartecipants")));

            response.sendRedirect("dashboard");
            return;
        } else {
            action_error(request, response);
            return;
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

}
