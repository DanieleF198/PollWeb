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
import com.mycompany.pollweb.model.Sondaggio;
import com.mycompany.pollweb.result.FailureResult;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joker
 */

public class PublicPolls extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, DataException{
         try {
            
            action_default(request, response);

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
           
            TemplateResult res = new TemplateResult(getServletContext());
            
            PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
                    
            if(request.getParameter("search-sondaggio") != null){
                System.out.println("search-sondaggi cliccato");
                ArrayList<Sondaggio> sondaggi = (ArrayList<Sondaggio>) dl.getSondaggioDAO().getSondaggiPopolari();
                sondaggi = (ArrayList<Sondaggio>) dl.getSondaggioDAO().searchSondaggi(sondaggi, (String)request.getParameter("search-sondaggio"));
                request.setAttribute("sondaggi", sondaggi);
            }
            
            else if(request.getParameter("btnPopolari") != null){
                System.out.println("btnPopolari cliccato");
                ArrayList<Sondaggio> sondaggi = (ArrayList<Sondaggio>) dl.getSondaggioDAO().getSondaggiPopolari();
                request.setAttribute("sondaggi", sondaggi);
            }
            else if(request.getParameter("btnRecenti") != null){
                System.out.println("btnRecenti cliccato");
                ArrayList<Sondaggio> sondaggi = (ArrayList<Sondaggio>) dl.getSondaggioDAO().getSondaggiRecenti();
                request.setAttribute("sondaggi", sondaggi);
            }
            else if(request.getParameter("btnMenoRecenti") != null){
                System.out.println("btnMenoRecenti cliccato");
                ArrayList<Sondaggio> sondaggi = (ArrayList<Sondaggio>) dl.getSondaggioDAO().getSondaggiMenoRecenti();
                request.setAttribute("sondaggi", sondaggi);
            }

            else{
                ArrayList<Sondaggio> sondaggi = (ArrayList<Sondaggio>) dl.getSondaggioDAO().getSondaggi();//Lista di tutti i sondaggi
                request.setAttribute("sondaggi", sondaggi);
            }
            
            res.activate("publicPolls.ftl", request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(PublicPolls.class.getName()).log(Level.SEVERE, null, ex);
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
