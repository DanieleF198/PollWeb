/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.controllers;

import com.mycompany.pollweb.dao.PollWebDataLayer;
import com.mycompany.pollweb.data.DataException;
import com.mycompany.pollweb.model.Utente;
import com.mycompany.pollweb.proxy.UtenteProxy;
import com.mycompany.pollweb.result.FailureResult;
import com.mycompany.pollweb.result.TemplateManagerException;
import com.mycompany.pollweb.result.TemplateResult;
import com.mycompany.pollweb.security.SecurityLayer;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author joker
 */
public class Register extends BaseController {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, DataException{
         try {
            if(SecurityLayer.checkSession(request) != null){ //l'utente è già in sessione  
                response.sendRedirect("dashboard");
            }
            else if("POST".equals(request.getMethod())) {   //l'utente ha mandato i dati per la registrazione
                action_register(request, response);
            }
            else { //caricamento pagina registrazione (in questo caso non reinderizza)
                action_default(request, response);
            }

        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        } catch (ParseException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
       try {
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("register.ftl", request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_register(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException, DataException, ParseException {
        try{
            if(SecurityLayer.checkSession(request) != null){ //controllo in più inutile ma per essere sicuri
                response.sendRedirect("dashboard");
            }else{
                PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
                TemplateResult res = new TemplateResult(getServletContext());
                if(request.getParameter("buttonRegister") != null){
                    String firstName = SecurityLayer.addSlashes(request.getParameter("firstName"));
                    String lastName = SecurityLayer.addSlashes(request.getParameter("lastName"));
                    String date = SecurityLayer.addSlashes(request.getParameter("date"));
                    String username = SecurityLayer.addSlashes(request.getParameter("username"));
                    String email = SecurityLayer.addSlashes(request.getParameter("email"));
                    String password = SecurityLayer.addSlashes(request.getParameter("password"));
                    String confirmPassword = SecurityLayer.addSlashes(request.getParameter("confirmPassword"));
                    if (firstName != null && lastName != null && date != null && username != null && email != null && password != null && confirmPassword != null && !firstName.isEmpty() && !lastName.isEmpty() && !date.isEmpty() && !username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {

                        Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
                        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
                        Pattern digitCasePatten = Pattern.compile("[0-9 ]");
                        Pattern emailPattern = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
                        
                        if (digitCasePatten.matcher(firstName).find()) {
                            request.setAttribute("error", "il nome non deve contenere cifre");
                            res.activate("register.ftl", request, response);
                            return;
                        }
                        
                        if (specailCharPatten.matcher(firstName).find()) {
                            request.setAttribute("error", "il nome non deve contenere caratteri speciali");
                            res.activate("register.ftl", request, response);
                            return;
                        }
                        
                        if (digitCasePatten.matcher(lastName).find()) {
                            request.setAttribute("error", "il cognome non deve contenere cifre");
                            res.activate("register.ftl", request, response);
                            return;
                        }
                        
                        if (specailCharPatten.matcher(lastName).find()) {
                            request.setAttribute("error", "il cognome non deve contenere caratteri speciali");
                            res.activate("register.ftl", request, response);
                            return;
                        }
                        
                        if(username.length()<3){
                            request.setAttribute("error", "lo username deve eavere almeno 3 caratteri");
                            res.activate("register.ftl", request, response);
                            return;
                        }
                        
                        if(username.length()>20){
                            request.setAttribute("error", "lo username deve eavere al massimo 20 caratteri");
                            res.activate("register.ftl", request, response);
                            return;
                        }
                        
                        if (specailCharPatten.matcher(username).find()) {
                            request.setAttribute("error", "lo username non deve contenere caratteri speciali");
                            res.activate("register.ftl", request, response);
                            return;
                        }
                        
                        Date birthDateTemp = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                        Date nowTemp = new Date();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate birthDate = LocalDate.parse(date, formatter);
                        LocalDate now = LocalDate.now();
                        Period period = Period.between(birthDate, now);
                           
                        if(birthDateTemp.after(nowTemp)){
                            request.setAttribute("error", "la data inserita non è valida");
                            res.activate("register.ftl", request, response);
                            return;
                        }
                        
                        if(period.getYears() < 18){
                            request.setAttribute("error", "devi essere maggiorenne per registrarti al sito");
                            res.activate("register.ftl", request, response);
                            return;
                        }
                        
                        if(period.getYears() > 100){
                            request.setAttribute("error", "la data inserita non è valida");
                            res.activate("register.ftl", request, response);
                            return;
                        }
                        
                        if (!emailPattern.matcher(email).find()) {
                            request.setAttribute("error", "la email inserita non è valida");
                            res.activate("register.ftl", request, response);
                            return;
                        }

                        if(password.length()<8){
                            request.setAttribute("error", "la password deve contenere almeno 8 caratteri");
                            res.activate("register.ftl", request, response);
                            return;
                        }
              
                        if (!UpperCasePatten.matcher(password).find()) {
                            request.setAttribute("error", "la password deve contenere almeno 1 carattere maiscolo");
                            res.activate("register.ftl", request, response);
                            return;
                        }
                        
                        if (!lowerCasePatten.matcher(password).find()) {
                            request.setAttribute("error", "la password deve contenere almeno 1 carattere minuscolo");
                            res.activate("register.ftl", request, response);
                            return;
                        }
                        
                        if (!digitCasePatten.matcher(password).find()) {
                            request.setAttribute("error", "la password deve contenere almeno 1 cifra");
                            res.activate("register.ftl", request, response);
                            return;
                        }
                        
                        if (!specailCharPatten.matcher(password).find()) {
                            request.setAttribute("error", "la password deve contenere almeno 1 carattere speciale");
                            res.activate("register.ftl", request, response);
                            return;
                        }
                        
                        if(!password.equals(confirmPassword)){
                            request.setAttribute("error", "le password non coincidono");
                            res.activate("register.ftl", request, response);
                            return;
                        }
                        
                        String checkExists = ((PollWebDataLayer)request.getAttribute("datalayer")).getUtenteDAO().checkIfExist(username, email);
                        if(checkExists.equals("username")){
                            request.setAttribute("error", "username esistente");
                            res.activate("register.ftl", request, response);
                            return;
                        } else if(checkExists.equals("email")) {
                            request.setAttribute("error", "email esistente");
                            res.activate("register.ftl", request, response);
                            return;
                        } else if(checkExists.equals("usernameemail")){
                            request.setAttribute("error", "username ed email esistenti");
                            res.activate("register.ftl", request, response);
                            return;
                        }
                        
                        Utente newUtente = dl.getUtenteDAO().createUtente();
                        
                        newUtente.setIdGruppo(2);
                        newUtente.setNome(firstName);
                        newUtente.setCognome(lastName);
                        newUtente.setDataNascita(birthDateTemp);
                        newUtente.setEmail(email);
                        newUtente.setUsername(username);
                        newUtente.setPassword(password);
                        dl.getUtenteDAO().storeUtente(newUtente);
                        SecurityLayer.createSession(request, newUtente, false);
                        response.sendRedirect("homepage");
                    }else{
                        request.setAttribute("error", "tutti i campi devono essere compilati");
                        res.activate("register.ftl", request, response); 
                    }
                } else {
                    res.activate("register.ftl", request, response);
                }
            }
        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", new Exception("Login failed"));
            action_error(request, response);
        }
    }
}
