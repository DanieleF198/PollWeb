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
import com.mycompany.pollweb.model.Utente;
import com.mycompany.pollweb.result.FailureResult;
import static com.mycompany.pollweb.security.SecurityLayer.checkSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.servlet.http.HttpSession;

/**
 *
 * @author joker
 */

public class ChangePassword extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, DataException{
         try {
            HttpSession s = checkSession(request);
            if ((Integer)s.getAttribute("groupid") == 1){
               response.sendRedirect("partecipantDashboard");
               return;
            }
            request.setAttribute("error", ""); 
            if(request.getParameter("btnChangePassword") != null){

                if(request.getParameter("currentPassword") != null && request.getParameter("newPassword") != null && request.getParameter("confirmNewPassword") != null && !request.getParameter("currentPassword").isEmpty() && !request.getParameter("newPassword").isEmpty() && !request.getParameter("confirmNewPassword").isEmpty()){
                    
                    PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
                    Utente u = dl.getUtenteDAO().getUtente((Integer) s.getAttribute("userid"));
                    
                    if(request.getParameter("currentPassword").equals(u.getPassword())){
                        
                        if(request.getParameter("newPassword").equals(request.getParameter("confirmNewPassword"))){
                            
                            password_check(request, response);
                            
                            if(request.getAttribute("error") == ""){
                                u.setPassword(request.getParameter("newPassword"));
                                dl.getUtenteDAO().storeUtente(u);
                                s.setAttribute("password", u.getPassword());
                                request.setAttribute("success", "Operazione conclusa con successo");
                            }
                        }
                        else{
                           request.setAttribute("error", "Le nuove password non coincidono"); 
                        }
                    }
                    else{
                        request.setAttribute("error", "Inserire la password corretta");
                    }
                }
                else{
                    request.setAttribute("error", "Compilare ogni campo");
                }
            }
            action_default(request, response);

        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
       try {
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("changePassword.ftl", request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(ChangePassword.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void password_check(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        
        TemplateResult res = new TemplateResult(getServletContext());
        String password = request.getParameter("newPassword");
        System.out.println(password);
        
        Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");
        
        if(password.length()<8){
            request.setAttribute("error", "la password deve contenere almeno 8 caratteri");
            res.activate("changePassword.ftl", request, response);
            return;
        }

        if (!UpperCasePatten.matcher(password).find()) {
            request.setAttribute("error", "la password deve contenere almeno 1 carattere maiscolo");
            res.activate("changePassword.ftl", request, response);
            return;
        }

        if (!lowerCasePatten.matcher(password).find()) {
            request.setAttribute("error", "la password deve contenere almeno 1 carattere minuscolo");
            res.activate("changePassword.ftl", request, response);
            return;
        }

        if (!digitCasePatten.matcher(password).find()) {
            request.setAttribute("error", "la password deve contenere almeno 1 cifra");
            res.activate("changePassword.ftl", request, response);
            return;
        }

        if (!specailCharPatten.matcher(password).find()) {
            request.setAttribute("error", "la password deve contenere almeno 1 carattere speciale");
            res.activate("changePassword.ftl", request, response);
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

}
