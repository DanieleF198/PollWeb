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
import com.mycompany.pollweb.result.SplitSlashesFmkExt;
import static com.mycompany.pollweb.security.SecurityLayer.checkSession;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author joker
 */

public class Homepage extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, DataException{
         try {
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            HttpSession s = checkSession(request);
            if (s!= null) {
                if ((Integer)s.getAttribute("groupid") == 1){
                    response.sendRedirect("partecipantDashboard");
                    return;
                }
                request.setAttribute("sessioned", "yes");
            } else {
                request.setAttribute("sessioned", "no");
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

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
       try {
        TemplateResult res = new TemplateResult(getServletContext());
        PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));

        ArrayList<Sondaggio> sondaggi = (ArrayList<Sondaggio>) dl.getSondaggioDAO().getSondaggiPopolari9(); //Lista di tutti i sondaggi
        request.setAttribute("sondaggi", sondaggi);
        
        res.activate("homepage.ftl", request, response);
        
        } catch (TemplateManagerException ex) {
            Logger.getLogger(Homepage.class.getName()).log(Level.SEVERE, null, ex);
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
