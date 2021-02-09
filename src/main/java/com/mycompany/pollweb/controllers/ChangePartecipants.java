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
import static com.mycompany.pollweb.security.SecurityLayer.checkSession;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
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
        if(!(request.getParameter("withCSV") != null && request.getParameter("withCSV").equals("withCSV"))){
                if(request.getParameter("usersName[]")!=null || request.getParameter("usersMail[]")!=null || request.getParameter("usersPass[]")!=null){
                    String[] name = request.getParameterValues("usersName[]");
                    String[] mail = request.getParameterValues("usersMail[]");
                    String[] pass = request.getParameterValues("usersPass[]");
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
                            request.setAttribute("partecipants", partecipants);
                        }
                    }
                }
            }
            request.setAttribute("idSondaggio", Integer.parseInt(request.getParameter("changePartecipants")));
            res.activate("changePartecipants.ftl", request, response);
            return;
    }
    
    private void action_change_partecipants(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException, CsvException {
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
            } else if(request.getParameter("usersName[]")!=null || request.getParameter("usersMail[]")!=null || request.getParameter("usersPass[]")!=null){
                String[] name = request.getParameterValues("usersName[]");
                String[] mail = request.getParameterValues("usersMail[]");
                String[] pass = request.getParameterValues("usersPass[]");
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
            if(checkError){
                action_default2(request, response);
                return;
            }
            if(!partecipants.isEmpty()){
                dl.getUtenteDAO().updateUtenteListaPartecipanti(partecipants, Integer.parseInt(request.getParameter("changePartecipants")));
            }
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
