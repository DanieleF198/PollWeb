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
import com.mycompany.pollweb.model.Utente;
import com.mycompany.pollweb.result.FailureResult;
import com.mycompany.pollweb.result.SplitSlashesFmkExt;
import com.mycompany.pollweb.result.TemplateManagerException;
import com.mycompany.pollweb.result.TemplateResult;
import com.mycompany.pollweb.security.SecurityLayer;
import static com.mycompany.pollweb.security.SecurityLayer.checkSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joker
 */

public class PartecipantDashboard extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, DataException{
         try {
            HttpSession s = checkSession(request);
            if (s!= null) {
                System.out.println(s.getAttribute("groupid"));
                if ((Integer)s.getAttribute("groupid") != 1){
                    action_redirect_login(request, response);
                }
                else{
                    if(request.getParameter("header-search-sondaggi-privati") != null){
                        System.out.println("REQUEST HEADER_SEARCH(privati): " + request.getParameter("header-search-sondaggi-privati"));
                        action_sondaggi_privati_search(request, response);
                        return;
                    }
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

        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        try {
            if(!(SecurityLayer.checkSession(request) != null)){ //controllo in più per essere sicuri
                action_redirect_login(request,response);
            }
            else{
                PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
                TemplateResult res = new TemplateResult(getServletContext());
                HttpSession s = checkSession(request);
                request.setAttribute("email", (String)s.getAttribute("email"));
                request.setAttribute("gruppo", 1);
                ArrayList<Sondaggio> sondaggiPriv = new ArrayList<Sondaggio>();
                HashMap<Integer, Utente> partecipante = ((PollWebDataLayer)request.getAttribute("datalayer")).getUtenteDAO().getPartecipanteLogin((String)s.getAttribute("email"), (String)s.getAttribute("password"));
                if(partecipante != null){
                    if(!partecipante.isEmpty()){
                        for(int key : partecipante.keySet()){
                            Sondaggio sond = dl.getSondaggioDAO().getSondaggio(key);
                            if(!sondaggiPriv.contains(sond)){
                                sondaggiPriv.add(sond);
                            }
                        }
                    }
                }
                
                request.setAttribute("sondaggiPriv", sondaggiPriv);
                if( sondaggiPriv.isEmpty() ){
                    request.setAttribute("noSondaggiPriv", "yes");
                }
                
                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                res.activate("partecipantDashboard.ftl", request, response);
            }
        } catch (TemplateManagerException ex) {
            Logger.getLogger(PartecipantDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void action_sondaggi_privati_search(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        try {
            if(!(SecurityLayer.checkSession(request) != null)){ //controllo in più per essere sicuri
                action_redirect_login(request,response);
            }else{
                PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
                TemplateResult res = new TemplateResult(getServletContext());
                HttpSession s = checkSession(request);
                request.setAttribute("email", (String)s.getAttribute("email"));
                request.setAttribute("gruppo", 1);
                ArrayList<Sondaggio> sondaggiPriv = new ArrayList<Sondaggio>();
                HashMap<Integer, Utente> partecipante = ((PollWebDataLayer)request.getAttribute("datalayer")).getUtenteDAO().getPartecipanteLogin((String)s.getAttribute("email"), (String)s.getAttribute("password"));
                if(partecipante != null){
                    if(!partecipante.isEmpty()){
                        for(int key : partecipante.keySet()){
                            Sondaggio sond = dl.getSondaggioDAO().getSondaggio(key);
                            if(!sondaggiPriv.contains(sond)){
                                sondaggiPriv.add(sond);
                            }
                        }
                    }
                }
                
                sondaggiPriv = (ArrayList<Sondaggio>) dl.getSondaggioDAO().searchSondaggi(sondaggiPriv, (String)request.getParameter("header-search-sondaggi-privati"));
                request.setAttribute("sondaggiPriv", sondaggiPriv);
                
                
                
                if(!request.getParameter("header-search-sondaggi-privati").isEmpty()){
                    request.setAttribute("ricercaSondaggiPrivati", "yes");
                }
                else{
                    request.setAttribute("ricercaSondaggiPrivati", "");
                }
                
                if(sondaggiPriv.isEmpty()){ 
                    request.setAttribute("listaSondaggiPrivatiVuota", "yes");  
                    System.out.println("LISTA SONDAGGI VUOTA (privati): " + request.getAttribute("listaSondaggiPrivatiVuota"));
                }
                else{
                    request.setAttribute("listaSondaggiPrivatiVuota", "");
                    System.out.println("LISTA SONDAGGI VUOTA (privati): " + request.getAttribute("listaSondaggiPrivatiVuota"));
                }

                res.activate("partecipantDashboard.ftl", request, response);
                
                
            }
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
