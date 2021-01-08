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
import com.mycompany.pollweb.model.Sondaggio;
import com.mycompany.pollweb.model.Utente;
import com.mycompany.pollweb.result.FailureResult;
import com.mycompany.pollweb.security.SecurityLayer;
import static com.mycompany.pollweb.security.SecurityLayer.checkSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, DataException{
         try {
            HttpSession s = checkSession(request);
            if (s!= null) {
                if ("POST".equals(request.getMethod())) {
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

        } catch (ParseException ex) {
            Logger.getLogger(FirstSection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
       try {
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession s = checkSession(request);
            if (s!= null) {
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
    
    private void action_questions(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, ParseException, DataException{
        PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
        TemplateResult res = new TemplateResult(getServletContext());
        HttpSession s = checkSession(request);
        if(request.getParameter("buttonFirstSection") != null){
            String title = "";
            String description = "";
            String finalMessage = "";
            String date = "";
            boolean privateSurvey = false;
            boolean modificableSurvey = false;
            
                   
            
            if(request.getParameter("title") != null){
                title = SecurityLayer.addSlashes(request.getParameter("title"));
                title = SecurityLayer.stripSlashes(title);
            }
            
            if(request.getParameter("description") != null){
                description = SecurityLayer.addSlashes(request.getParameter("description"));
                description = SecurityLayer.stripSlashes(description);
            }
            
            if(request.getParameter("finalMessage") != null){
                finalMessage = SecurityLayer.addSlashes(request.getParameter("finalMessage"));
                finalMessage = SecurityLayer.stripSlashes(finalMessage);
            }
            
            if(request.getParameter("date") != null){
                date = SecurityLayer.addSlashes(request.getParameter("expiration"));
                date = SecurityLayer.stripSlashes(date);
            }
            
            
            System.out.println(title);
            if (title.length() < 3 || title.length() > 128){
                request.setAttribute("error", "il titolo deve contenere almeno 3 caratteri o al massimo 128");
                action_default(request, response);
                return;
            }
            
            if (description.length() > 2048){
                request.setAttribute("error", "la descrizione deve contenere al massimo 2048 caratteri");
                action_default(request, response);
                return; 
            }
            
            if (finalMessage.length() > 2048){
                request.setAttribute("error", "il messaggio di completamento deve contenere al massimo 1024 caratteri");
                action_default(request, response);
                return; 
            }
            
            
            Date nowTemp = new Date();
            Date expirationDate = null;
            
            if(!date.isEmpty()){
                expirationDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);

                if(nowTemp.after(expirationDate)){
                    request.setAttribute("error", "la data inserita non è valida");
                    action_default(request, response);
                    return;
                }
            }
            
            if(request.getParameter("private") != null){
                int idGruppo = (int) s.getAttribute("groupid");
                if (idGruppo == 1){
                    request.setAttribute("error", "non hai i permessi per fare un sondaggio privato");
                    action_default(request, response);
                    return;
                }
                privateSurvey = true;
            }
            
            if(request.getParameter("modificable") != null){
                modificableSurvey = true;
            }
            
            Sondaggio newSondaggio = dl.getSondaggioDAO().createSondaggio();
            
            newSondaggio.setIdUtente((int)s.getAttribute("userid"));
            newSondaggio.setTitolo(title);
            newSondaggio.setTestoApertura(description);
            newSondaggio.setTestoChiusura(finalMessage);
            newSondaggio.setCreazione(nowTemp);
            if(expirationDate != null){
                newSondaggio.setScadenza(expirationDate);
            }
            if(privateSurvey != false){
                newSondaggio.setPrivato(true);
            }
            if(modificableSurvey != false){
                newSondaggio.setModificabile(true);
            }
            dl.getSondaggioDAO().storeSondaggio(newSondaggio);
            s.setAttribute("sondaggio-in-creazione", 1);
            response.sendRedirect("questionsMaker");
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
