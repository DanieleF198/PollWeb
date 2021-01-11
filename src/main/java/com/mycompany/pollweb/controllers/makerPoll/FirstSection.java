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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
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
                if (("POST".equals(request.getMethod())) && (request.getParameter("buttonFirstSection") != null)) {
                    action_questions(request, response); //caso in cui clicca il bottone "crea sondaggio"
                    return;
                }
                else if (("POST".equals(request.getMethod())) && (request.getParameter("buttonContinue") != null)){
                    s.setAttribute("continue", "yes");
                    response.sendRedirect("firstSection"); //caso in cui arriva da firstSectionWarning e decide di continuare
                    return;
                }
                else if (("POST".equals(request.getMethod())) && (request.getParameter("buttonNew") != null)){
                    s.setAttribute("sondaggio-in-creazione", 0); //caso in cui arriva da firstSectionWarning e vuole nuovo
                    response.sendRedirect("firstSection");
                    return;
                }
                else{
                    define_action(request, response, s);
                }
            } else {
                action_redirect_login(request, response);
                return;
            }
            
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        } catch (ParseException ex) {
            Logger.getLogger(FirstSection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(FirstSection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void define_action(HttpServletRequest request, HttpServletResponse response, HttpSession s) throws IOException, ServletException, TemplateManagerException, DataException, InterruptedException {
        if((int)s.getAttribute("sondaggio-in-creazione") != 0){
            if (s.getAttribute("continue")=="no"){
                action_warning(request, response); //caso in cui carica la pagina mentre era in creazione un'altro sondaggio / non ha completato la creazione                            
                return;
            }
            else{
                action_default(request, response);
                return;
            }
        }
        else {
            action_default(request, response); //caso base
            return;
        }
    }

    
    private void action_warning(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
       try {
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession s = checkSession(request);
            PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
            Sondaggio sondaggio = dl.getSondaggioDAO().getSondaggio((int)s.getAttribute("sondaggio-in-creazione"));
            request.setAttribute("titoloSondaggio", sondaggio.getTitolo());
            res.activate("MakerPoll/firstSectionWarning.ftl", request, response);
            return;
        } catch (TemplateManagerException ex) {
            Logger.getLogger(FirstSection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException, InterruptedException {
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
            PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
            if((int)s.getAttribute("sondaggio-in-creazione")!= 0){ //caso in cui hai fatto continue
                Sondaggio sondaggio = dl.getSondaggioDAO().getSondaggio((int)s.getAttribute("sondaggio-in-creazione"));
                request.setAttribute("sondaggio", sondaggio);
                if(sondaggio.getScadenza() != null){
                    Date exp = new Date(sondaggio.getScadenza().getTime());
                    LocalDate expLocDate = exp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    String d = expLocDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    request.setAttribute("expirationDate", d);
                }
                System.out.println(sondaggio.isModificabile());
                if(sondaggio.isModificabile()){
                    request.setAttribute("modificable", "yes");
                }
                else{
                    request.setAttribute("modificable", "no");
                }
                if(sondaggio.isPrivato()){
                    request.setAttribute("private", "yes");
                }
                else{
                    request.setAttribute("private", "no");
                }
                if (s.getAttribute("continue")=="yes"){
                    s.setAttribute("continue", "no"); //ormai ha fatto il suo compito
                }
            }
            if(s.getAttribute("error") != null){
                request.setAttribute("error", s.getAttribute("error"));
                s.removeAttribute("error");
            }
            res.activate("MakerPoll/firstSection.ftl", request, response);
            return;
        } catch (TemplateManagerException ex) {
            Logger.getLogger(FirstSection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void action_questions(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, ParseException, DataException, InterruptedException{
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
            
            if(request.getParameter("expiration") != null){
                date = SecurityLayer.addSlashes(request.getParameter("expiration"));
                date = SecurityLayer.stripSlashes(date);
            }
            
            if (title.length() < 3 || title.length() > 128){
                s.setAttribute("error", "il titolo deve contenere almeno 3 caratteri o al massimo 128");
                response.sendRedirect("firstSection");
                return;
            }
            
            if (description.length() > 2048){
                s.setAttribute("error", "la descrizione deve contenere al massimo 2048 caratteri");
                response.sendRedirect("firstSection");
                return; 
            }
            
            if (finalMessage.length() > 1024){
                s.setAttribute("error", "il messaggio di completamento deve contenere al massimo 1024 caratteri");
                response.sendRedirect("firstSection");
                return; 
            }
            
            
            Date nowTemp = new Date();
            Date expirationDate = null;
            
            if(!date.isEmpty()){
                expirationDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);

                if(nowTemp.after(expirationDate)){
                    s.setAttribute("error", "la data inserita non è valida");
                    response.sendRedirect("firstSection");
                    return;
                }
            }
            
            if(request.getParameter("private") != null){
                if(request.getParameter("private").equals("private")){
                    int idGruppo = (int) s.getAttribute("groupid");
                    if (idGruppo == 1){
                        s.setAttribute("error", "non hai i permessi per fare un sondaggio privato");
                        response.sendRedirect("firstSection");
                        return;
                    }
                    privateSurvey = true;
                }
            }
            System.out.println(request.getParameter("modificable"));
            if(request.getParameter("modificable") != null){
                if(request.getParameter("modificable").equals("modificable")){
                    modificableSurvey = true;
                }
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
            if((int)s.getAttribute("sondaggio-in-creazione") == 0){
                dl.getSondaggioDAO().storeSondaggio(newSondaggio);
                s.setAttribute("sondaggio-in-creazione", newSondaggio.getKey());
            } else {
                newSondaggio.setKey((int)s.getAttribute("sondaggio-in-creazione"));
                dl.getSondaggioDAO().storeSondaggio(newSondaggio);
            }
            s.setAttribute("fromFirst", "firstSection");
            response.sendRedirect("questionsMaker");
            return;
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