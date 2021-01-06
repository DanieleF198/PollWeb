/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.controllers;

import com.mycompany.pollweb.dao.PollWebDataLayer;
import com.mycompany.pollweb.data.DataException;
import com.mycompany.pollweb.model.Utente;
import com.mycompany.pollweb.result.FailureResult;
import com.mycompany.pollweb.result.HTMLResult;
import com.mycompany.pollweb.result.TemplateManagerException;
import com.mycompany.pollweb.result.TemplateResult;
import com.mycompany.pollweb.security.SecurityLayer;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author joker
 */
public class Login extends BaseController {

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
            else if("POST".equals(request.getMethod())) {   //l'utente ha mandato i dati per il login
                action_login(request, response);
            }
            else {  //l'utente è stato reindirizzato in questa pagina per effettuare il login
                TemplateResult res = new TemplateResult(getServletContext());
                if(request.getAttribute("urlrequest") != null){
                    String referrer = request.getAttribute("urlrequest").toString();
                    if(referrer.contains("makerPoll")){
                        request.setAttribute("urlMakerPoll", "yes");
                    } else {
                        request.setAttribute("urlMakerPoll", "no");
                    }
                    request.setAttribute("referrer", referrer);
                }
                res.activate("login.ftl", request, response);
            }

        } catch (IOException | TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }

    private void action_login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        try{
            if(SecurityLayer.checkSession(request) != null){ //controllo in più inutile ma per essere sicuri
                response.sendRedirect("dashboard");
            }else{
                TemplateResult res = new TemplateResult(getServletContext());
                if(request.getParameter("buttonLogin") != null){
                    String username = SecurityLayer.addSlashes(request.getParameter("inputUsername"));
                    username = SecurityLayer.stripSlashes(username);
                    String password = SecurityLayer.addSlashes(request.getParameter("inputPassword"));
                    password = SecurityLayer.stripSlashes(password);

                    if (username != null && password != null && !username.isEmpty() && !password.isEmpty()) {
                        Utente utente = ((PollWebDataLayer)request.getAttribute("datalayer")).getUtenteDAO().getUtenteLogin(username, password);
                        if(utente != null){
                            if (username.equals(utente.getUsername())){
                                if(request.getParameter("remember") == null){
                                    SecurityLayer.createSession(request, utente, false);
                                }else{
                                    SecurityLayer.createSession(request, utente, true);
                                }
                                if(request.getParameter("referrer") != null){
                                    response.sendRedirect(request.getParameter("referrer"));
                                }
                                else{
                                    response.sendRedirect("homepage");
                                }
                            }
                        } else {
                            request.setAttribute("error", "Credenziali errate");
                            if(request.getParameter("referrer") != null){
                                String referrer = request.getParameter("referrer");
                                request.setAttribute("referrer", referrer);
                            }
                            //è vero che deriviamo da li, ma essendo che in caso di errori ritorniamo in pollweb/login (e non pollweb/pollMaker/login non serve più).
                            //non abbiamo optato per sendRedirect per non perderci il referrer.
                            request.setAttribute("urlMakerPoll", "no"); 
                            res.activate("login.ftl", request, response);
                        }
                    }
                } else {
                    res.activate("login.ftl", request, response);
                }
            }
        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", new Exception("Login failed"));
            action_error(request, response);
        }
    }

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        //assumiamo che l'eccezione sia passata tramite gli attributi della request
        //we assume that the exception has been passed using the request attributes
        Exception exception = (Exception) request.getAttribute("exception");
        String message;
        if (exception != null && exception.getMessage() != null) {
            message = exception.getMessage();
        } else {
            message = "Unknown error";
        }
        HTMLResult result = new HTMLResult(getServletContext());
        result.setTitle("ERROR");
        result.setBody("<p>" + message + "</p>");
        try {
            result.activate(request, response);
        } catch (IOException ex) {
            //if error page cannot be sent, try a standard HTTP error message
            //se non possiamo inviare la pagina di errore, proviamo un messaggio di errore HTTP standard
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
            } catch (IOException ex1) {
                //if ALSO this error status cannot be notified, write to the server log
                //se ANCHE questo stato di errore non pu� essere notificato, scriviamo sul log del server
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
}
