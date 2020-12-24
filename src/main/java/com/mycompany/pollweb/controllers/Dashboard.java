/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.controllers;

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
import com.mycompany.pollweb.result.FailureResult;
import com.mycompany.pollweb.security.SecurityLayer;
import static com.mycompany.pollweb.security.SecurityLayer.checkSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author joker
 */

public class Dashboard extends BaseController {

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
            TemplateResult res = new TemplateResult(getServletContext());
            if(SecurityLayer.checkSession(request) == null){
                request.setAttribute("username", "PuppetUser"); //questo set è solo di prova, dovrà essere sostituito col valore effettivo caricato dinamicamente tramite il DAO
            } else {
                HttpSession s = request.getSession(false);
                request.setAttribute("username", (String)s.getAttribute("username"));
            }
            res.activate("dashboard.ftl", request, response); 
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
}
