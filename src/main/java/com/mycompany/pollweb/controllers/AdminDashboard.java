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
import com.mycompany.pollweb.result.TemplateManagerException;
import com.mycompany.pollweb.result.TemplateResult;
import com.mycompany.pollweb.security.SecurityLayer;
import static com.mycompany.pollweb.security.SecurityLayer.checkSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
                if(request.getParameter("header-search-sondaggi") != null){
                    action_sondaggi_search(request, response);
                    
                }
                if (request.getParameter("btnDeleteUser") != null) {
                    System.out.println("Delete utente cliccato");
                    action_delete_user(request);
                    
                }
                if ((request.getParameter("btnBanUser") != null)) {  
                    System.out.println("Ban cliccato");
                    action_ban_user(request);
                    
                }
                if ((request.getParameter("btnSbanUser") != null)) { 
                    System.out.println("Sban cliccato");
                    action_sban_user(request);
                    
                }
                if ((request.getParameter("btnDeleteSondaggio") != null)) {  
                    System.out.println("Delete sondaggio cliccato");
                    action_delete_sondaggio(request);
                    
                }
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
            
            ArrayList<Sondaggio> sondaggi = (ArrayList<Sondaggio>) dl.getSondaggioDAO().getSondaggi();
            request.setAttribute("sondaggi", sondaggi);
            
            List<Utente> utenti = dl.getUtenteDAO().getUtenti();
            request.setAttribute("utenti", utenti);
            
            if(sondaggi.isEmpty()){ 
                request.setAttribute("listaTuoiSondaggiVuota", "yes");  
                System.out.println("LISTA SONDAGGI VUOTA: " + request.getAttribute("listaTuoiSondaggiVuota"));
            }
            else{
                request.setAttribute("listaTuoiSondaggiVuota", "");
                System.out.println("LISTA SONDAGGI VUOTA: " + request.getAttribute("listaTuoiSondaggiVuota"));
            }
            
            if(utenti.isEmpty()){ 
                request.setAttribute("listaUtentiVuota", "yes");  
                System.out.println("LISTA UTENTI VUOTA: " + request.getAttribute("listaUtentiVuota"));
            }
            else{
                request.setAttribute("listaUtentiVuota", "");
                System.out.println("LISTA UTENTI VUOTA: " + request.getAttribute("listaUtentiVuota"));
            }
            
            res.activate("adminDashboard.ftl", request, response);
        }
    }
    
    private void action_sondaggi_search(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
        if(!(SecurityLayer.checkSession(request) != null)){
            action_redirect_login(request,response);
        }else{
            PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession s = request.getSession(false);
            GruppoImpl g = new GruppoImpl();
            request.setAttribute("username", (String)s.getAttribute("username"));
            request.setAttribute("email", (String)s.getAttribute("email"));
            request.setAttribute("nome", (String)s.getAttribute("nome"));
            request.setAttribute("cognome", (String)s.getAttribute("cognome"));
            request.setAttribute("eta", (Integer)s.getAttribute("eta"));
            request.setAttribute("gruppo", g.getNomeGruppoByID((Integer)s.getAttribute("groupid")));
            
            ArrayList<Sondaggio> sondaggi = (ArrayList<Sondaggio>) dl.getSondaggioDAO().getSondaggi();
            sondaggi = (ArrayList<Sondaggio>) dl.getSondaggioDAO().searchSondaggi(sondaggi, (String)request.getParameter("header-search-sondaggi"));
            request.setAttribute("sondaggi", sondaggi);
            
            if(!request.getParameter("header-search-sondaggi").isEmpty()){
                request.setAttribute("ricercaTuoiSondaggi", "yes");
            }
            else{
                request.setAttribute("ricercaTuoiSondaggi", "");
            }
            
            if(sondaggi.isEmpty()){
                request.setAttribute("listaTuoiSondaggiVuota", "yes");
                System.out.println("LISTA SONDAGGI VUOTA: " + request.getAttribute("listaTuoiSondaggiVuota"));
            }
            else{
                request.setAttribute("listaTuoiSondaggiVuota", "");
                System.out.println("LISTA SONDAGGI VUOTA: " + request.getAttribute("listaTuoiSondaggiVuota"));
            }
            
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
        
    private void action_delete_user(HttpServletRequest request) throws  IOException, DataException {
        PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
        int userId = 0;
        request.getParameter("btnDeleteUser");
        System.out.println("Utente da eliminare: " + request.getParameter("btnDeleteUser"));
        try{
        userId = Integer.parseInt(request.getParameter("btnDeleteUser"));
        }
        catch (NumberFormatException e){
        }
        dl.getUtenteDAO().deleteUtente(userId);
    }
    
    private void action_ban_user(HttpServletRequest request) throws  IOException, DataException {
        PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
        int userId = 0;
        request.getParameter("btnBanUser");
        try{
        userId = Integer.parseInt(request.getParameter("btnBanUser"));
        }
        catch (NumberFormatException e){
        }
        Utente userBan = dl.getUtenteDAO().getUtente(userId);
        userBan.setBloccato(true);
        dl.getUtenteDAO().storeUtente(userBan);
    }
    
    private void action_sban_user(HttpServletRequest request) throws  IOException, DataException {
        PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
        int userId = 0;
        request.getParameter("btnSbanUser");
        try{
        userId = Integer.parseInt(request.getParameter("btnSbanUser"));
        }
        catch (NumberFormatException e){
        }
        Utente userSban = dl.getUtenteDAO().getUtente(userId);
        userSban.setBloccato(false);
        dl.getUtenteDAO().storeUtente(userSban);
    }
    
    private void action_delete_sondaggio(HttpServletRequest request) throws  IOException, DataException {
        PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
        int sondaggioId = 0;
        request.getParameter("btnDeleteSondaggio");
        System.out.println("Sondaggio da eliminare: " + request.getParameter("btnDeleteSondaggio"));
        try{
        sondaggioId = Integer.parseInt(request.getParameter("btnDeleteSondaggio"));
        }
        catch (NumberFormatException e){
        }
        dl.getSondaggioDAO().deleteSondaggio(sondaggioId);
    }
    
}
   