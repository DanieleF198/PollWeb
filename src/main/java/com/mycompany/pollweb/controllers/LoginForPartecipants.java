/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.controllers;

import com.mycompany.pollweb.dao.PollWebDataLayer;
import com.mycompany.pollweb.data.DataException;
import com.mycompany.pollweb.model.Sondaggio;
import com.mycompany.pollweb.model.Utente;
import com.mycompany.pollweb.result.FailureResult;
import com.mycompany.pollweb.result.HTMLResult;
import com.mycompany.pollweb.result.TemplateManagerException;
import com.mycompany.pollweb.result.TemplateResult;
import com.mycompany.pollweb.security.SecurityLayer;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
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
public class LoginForPartecipants extends BaseController {

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
            else {  //l'utente è arrivato su questa pagina per effettuare il login
                TemplateResult res = new TemplateResult(getServletContext());
                res.activate("loginForPartecipants.ftl", request, response);
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
                return;
            }else{
                TemplateResult res = new TemplateResult(getServletContext());
                if(request.getParameter("buttonLogin") != null){
                    String email = SecurityLayer.addSlashes(request.getParameter("inputEmail"));
                    String password = SecurityLayer.addSlashes(request.getParameter("inputPassword"));

                    if (email != null && password != null && !email.isEmpty() && !password.isEmpty()){
                        //controllo se le credenziali siano per caso "doppiate", cioè se esiste l'utente registrato con quella email (ma è anche stato invitato)
                        //in tal caso allora lo riporto al login "normale"
                        if(((PollWebDataLayer)request.getAttribute("datalayer")).getUtenteDAO().checkWithMail(email)){
                            request.setAttribute("error", "partecipant");
                            request.setAttribute("fromPartecipant", "yes");
                            request.setAttribute("urlrequest", request.getRequestURL());
                            RequestDispatcher rd = request.getRequestDispatcher("/login");
                            rd.forward(request, response);
                            return;
                        }
                        //come key abbiamo l'idSondaggio al quale il partecipante è riferito, value il partecipante effettivo
                        //questa cosa perché uno stesso partecipante può essere invitato a più sondaggi privati da diversi responsabili
                        HashMap<Integer, Utente> partecipante = ((PollWebDataLayer)request.getAttribute("datalayer")).getUtenteDAO().getPartecipanteLogin(email, password);
                        for ( int key : partecipante.keySet() ) {
                            Sondaggio sond = ((PollWebDataLayer)request.getAttribute("datalayer")).getSondaggioDAO().getSondaggio(key);
                            if (!(sond.isVisibilita())){
                                partecipante.remove(key);
                            }
                        }
                        if(partecipante != null){
                            if(!partecipante.isEmpty()){
                                Utente u = ((PollWebDataLayer)request.getAttribute("datalayer")).getUtenteDAO().createUtente();
                                u.setEmail(email);
                                u.setPassword(password);
                                SecurityLayer.createSession(request,u);
                                response.sendRedirect("partecipantDashboard");
                            } else {
                                request.setAttribute("error", "Credenziali errate oppure Permesso scaduto");
                                res.activate("loginForPartecipants.ftl", request, response);
                            }
                        } else {
                            request.setAttribute("error", "Credenziali errate oppure Permesso scaduto");
                            res.activate("loginForPartecipants.ftl", request, response);
                        }
                    } else {
                        request.setAttribute("error", "Credenziali errate oppure Permesso scaduto");
                        res.activate("loginForPartecipants.ftl", request, response);
                    }
                } else {
                    res.activate("loginForPartecipants.ftl", request, response);
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
                Logger.getLogger(LoginForPartecipants.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
}
