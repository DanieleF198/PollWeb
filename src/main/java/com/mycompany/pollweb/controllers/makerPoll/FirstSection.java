/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.controllers.makerPoll;

import com.mycompany.pollweb.controllers.BaseController;
import com.mycompany.pollweb.controllers.Homepage;
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
import com.mycompany.pollweb.model.Utente;
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

public class FirstSection extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException{
         try {
            HttpSession s = checkSession(request);
            if (s!= null) {
                if (request.getParameter("questionsMaker") != null && "POST".equals(request.getMethod())) {
                    action_questions(request, response);
                } else {
                    action_default(request, response);
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

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
       try {
            TemplateResult res = new TemplateResult(getServletContext());
            PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
            HttpSession s = checkSession(request);
            if (s!= null) {
                Utente user = dl.getUtenteDAO().createUtente();
                int idGruppo = (int) s.getAttribute("groupid");
                switch (idGruppo) {
                    case 1:
                        request.setAttribute("group", "base");
                        break;
                    case 2:
                        request.setAttribute("group", "responsabile");
                        break;
                    case 3:
                        request.setAttribute("group", "admin");
                        break;
                    default:
                        action_error(request, response);
                        break;
                }
            }
            res.activate("MakerPoll/firstSection.ftl", request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(FirstSection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void action_questions(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
        TemplateResult res = new TemplateResult(getServletContext());
        if(request.getParameter("buttonFirstSection") != null){
            String title = SecurityLayer.addSlashes(request.getParameter("title"));
            title = SecurityLayer.stripSlashes(title);
            String description = SecurityLayer.addSlashes(request.getParameter("description"));
            description = SecurityLayer.stripSlashes(description);
            String finalMessage = SecurityLayer.addSlashes(request.getParameter("finalMessage"));
            finalMessage = SecurityLayer.stripSlashes(finalMessage);
            String date = SecurityLayer.addSlashes(request.getParameter("expiration"));
            date = SecurityLayer.stripSlashes(date);
            if (title != null && description != null && finalMessage != null && date != null && !title.isEmpty() && !description.isEmpty() && !finalMessage.isEmpty() && !date.isEmpty()){
                // TO DO
            }
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
