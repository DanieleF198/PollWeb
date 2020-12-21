/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.controllers.makerPoll;

import com.mycompany.pollweb.controllers.BaseController;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joker
 */

public class ConfirmSection extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException{
         try {
            if (request.getParameter("confirm") != null) {
                action_confirm(request, response);
            } else if(request.getParameter("nextQuestion") != null){
                action_next_question(request, response);
            } else if(request.getParameter("prevQuestion") != null){
                action_prev_question(request, response);
            } else {
                action_default(request, response);
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
            res.activate("MakerPoll/confirmSection.ftl", request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(ConfirmSection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void action_confirm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        response.sendRedirect("confirmSection"); //ATTENZIONE, per ora fa solo un redirect, ma quando implementeremo effettivamente vanno considerati i casi di errore 
    }
    
    private void action_next_question(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        response.sendRedirect("questionsMaker"); //ATTENZIONE, per ora fa solo un redirect, ma quando implementeremo effettivamente vanno considerati i casi di errore 
    }
    
    private void action_prev_question(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        response.sendRedirect("questionsMaker"); //ATTENZIONE, per ora fa solo un redirect, ma quando implementeremo effettivamente vanno considerati i casi di errore 
    }


    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

}
