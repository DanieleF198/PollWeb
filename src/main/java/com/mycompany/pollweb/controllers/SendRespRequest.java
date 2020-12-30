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
import com.mycompany.pollweb.proxy.UtenteProxy;
import com.mycompany.pollweb.result.FailureResult;
import com.mycompany.pollweb.security.SecurityLayer;
import static com.mycompany.pollweb.security.SecurityLayer.checkSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author joker
 */

public class SendRespRequest extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException{
         try {
            HttpSession s = checkSession(request);
            if (s!= null) {
                action_default(request, response);
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

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
       try {
           if(SecurityLayer.checkSession(request) != null){ //controllo in più inutile ma per essere sicuri
                response.sendRedirect("dashboard");
            }else{
                PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
                TemplateResult res = new TemplateResult(getServletContext());
                if(request.getParameter("buttonSendRespRequest") != null){
                    String CF = SecurityLayer.addSlashes(request.getParameter("CF"));
                    CF = SecurityLayer.stripSlashes(CF);
                    if (CF != null && !CF.isEmpty()){
                        Pattern CFCheck = Pattern.compile("/^[A-Za-z]{6}[0-9]{2}[A-Za-z]{1}[0-9]{2}[A-Za-z]{1}[0-9]{3}[A-Za-z]{1}$/;");

                        if (CFCheck.matcher(CF).find()) {
                            request.setAttribute("error", "il codice fiscale non è valido");
                            res.activate("sendRespRequest.ftl", request, response);
                            return;
                        }
                        //sezione in cui avviene la modifica effettiva per ora senza proxy
                        UtenteProxy u = new UtenteProxy(dl);
                        
                    }else{
                        request.setAttribute("error", "tutti i campi devono essere compilati");
                        res.activate("sendRespRequest.ftl", request, response); 
                    }
                }else{
                    res.activate("sendRespRequest.ftl", request, response);
                }
            }
        } catch (TemplateManagerException ex) {
            Logger.getLogger(SendRespRequest.class.getName()).log(Level.SEVERE, null, ex);
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
