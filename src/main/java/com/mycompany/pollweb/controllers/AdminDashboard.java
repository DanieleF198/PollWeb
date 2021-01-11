/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.controllers;

import com.mycompany.pollweb.dao.PollWebDataLayer;
import com.mycompany.pollweb.data.DataException;
import com.mycompany.pollweb.impl.GruppoImpl;
import com.mycompany.pollweb.model.Sondaggio;
import com.mycompany.pollweb.result.FailureResult;
import com.mycompany.pollweb.result.TemplateManagerException;
import com.mycompany.pollweb.result.TemplateResult;
import com.mycompany.pollweb.security.SecurityLayer;
import static com.mycompany.pollweb.security.SecurityLayer.checkSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Cronio
 */

public class AdminDashboard extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, DataException{
         try {
            HttpSession s = checkSession(request); //in teoria il controllo avviene prima ma lo eseguiamo comunque
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
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        if(!(SecurityLayer.checkSession(request) != null)){ //controllo in più per essere sicuri
            action_redirect_login(request,response);
        }else{
            PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer")); //prima o poi lo useremo quindi per ora rimane qui
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession s = request.getSession(false);
            GruppoImpl g = new GruppoImpl();
            request.setAttribute("username", (String)s.getAttribute("username"));
            request.setAttribute("email", (String)s.getAttribute("email"));
            request.setAttribute("nome", (String)s.getAttribute("nome"));
            request.setAttribute("cognome", (String)s.getAttribute("cognome"));
            request.setAttribute("eta", (Integer)s.getAttribute("eta"));
            request.setAttribute("gruppo", g.getNomeGruppoByID((Integer)s.getAttribute("groupid")));
            
            res.activate("adminDashboard.ftl", request, response);
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
   