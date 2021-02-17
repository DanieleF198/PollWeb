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
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author joker
 */

public class ChangeMail extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, DataException{
         try {
            HttpSession s = checkSession(request);
            if (s!= null) {
                if(request.getParameter("btnChangeEmail") != null){

                    if(request.getParameter("currentMail") != null && request.getParameter("newMail") != null && request.getParameter("confirmNewMail") != null && !request.getParameter("currentMail").isEmpty() && !request.getParameter("newMail").isEmpty() && !request.getParameter("confirmNewMail").isEmpty()){

                        if(request.getParameter("currentMail").equals((String)s.getAttribute("email"))){

                            if(request.getParameter("newMail").equals(request.getParameter("confirmNewMail"))){
                                PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
                                Utente u = dl.getUtenteDAO().getUtente((Integer) s.getAttribute("userid"));
                                u.setEmail(request.getParameter("newMail"));
                                dl.getUtenteDAO().storeUtente(u);
                                request.setAttribute("success", "Operazione conclusa con successo");
                            }
                            else{
                               request.setAttribute("error", "Le nuove email non coincidono"); 
                            }
                        }
                        else{
                            request.setAttribute("error", "Inserire l'indirizzo email corretto");
                        }
                    }
                    else{
                        request.setAttribute("error", "Compilare ogni campo");
                    }
                }
                action_default(request, response);
            }
            else{
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

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
       try {
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("changeMail.ftl", request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(ChangeMail.class.getName()).log(Level.SEVERE, null, ex);
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
