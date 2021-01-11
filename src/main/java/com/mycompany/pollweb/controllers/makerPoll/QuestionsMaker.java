/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.controllers.makerPoll;

import com.mycompany.pollweb.controllers.BaseController;
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
import static com.mycompany.pollweb.security.SecurityLayer.checkSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author joker
 */

public class QuestionsMaker extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, DataException{
        //caso 1:
        //l'utente arriva qui per la prima volta/creazione prima domanda
        //caso 2:
        //l'utente arriva qui non per la prima volta, ma dopo aver lasciato in sospeso la domanda x.
        //data la gestione fatta in "firstSection" e "sondaggio-in-creazione" nel sessionamento, farò
        //in modo che l'utente possa Arrivare a questa pagina solo passando prima in FirstSection.
        //In questo caso la domanda X si assume non salvata nel database, mentre le domande precedenti sì.
        //In generale, quando l'utente arriva da firstSection (cioè sempre), partirà dalla prima domanda.
        //caso 3:
        //l'utente arriva qui dopo aver cliccato "domanda successiva". Da notare le seguenti osservazioni:
        //1- quando viene cliccato domanda successiva viene cmq memorizzata la domanda nel DB, anche se inconpleta.
        //   Semplicemente, quando verrà cliccato "vai alla conferma", verrà fatto un controllo su tutte le domande
        //   del sondaggio. In caso di errori, l'utente verrà reindirizzato in una pagina che segnala tutti gli
        //   errori da correggere per poter andare nella conferma.
        //   l'utente semplicemente vuole passare alla domanda successiva dopo essere andato alla precedente, in questo
        //   caso si deve prevedere eventuali modifiche fatte alla domanda corrente prima di passare alla successiva
        //NOTA: per capire la domanda successiva (se esiste) basta controllare se esiste una domanda con posizione immediatamente successiva
        //caso 4:
        //l'utente arriva qui dopo aver cliccato "domanda precedente". Questo caso è una versione semplificata del caso 3
        //con in più il solo controllo che domanda precedente è disabilitato alla creazione della prima domanda.
        //NOTA: per capire se si tratta della prima domanda basti controllare o che non esistano domande associate al sondaggio (caso 1),
        //      che esista una sola domanda, oppure che se ne esistono più di una si trovi quella con posizione 1
        //caso 5: l'utente arriva da "confirmSection", in questo caso, verrà ricondotto al 2 caso. Per fare questo dovrò introdurre un ulteriore
        //        valore nel sessionamento per controllare se è passato per "confirmSection", in tal caso, rimando a warningPage, quindi da lì
        //        a firstSection con creazione di un nuovo sondaggio o revisione di tutte le pagine create relative al sondaggio
        //OSS.: in caso di rimozione di una domanda vanno messe a posto le posizioni, o in alternativa costruito un algoritmo a livello java
        //      che sappia gestire tutte le situazioni 
        //NOTA: DA PREVEDERE I CASI IN CUI TORNI "INDIETRO" O "AVANTI" TRAMITE BROWSER - ANCORA NON FATTO
        
         try {
            HttpSession s = checkSession(request);
            if (s!= null) {
                if((int)s.getAttribute("sondaggio-in-creazione") != 0 && (s.getAttribute("fromFirst") != null)){ //è arrivato da firstSection
                    System.out.println("caso 1");
                    s.removeAttribute("fromFirst");
                    action_first_case(request, response);
                    return;
                } else if((int)s.getAttribute("sondaggio-in-creazione") != 0 && (request.getParameter("prevQuestion") != null)) { //è arrivato dopo aver cliccato "domanda precedente"
                    System.out.println("caso 2");
                    action_prev_case(request, response);
                    return;
                } else if((int)s.getAttribute("sondaggio-in-creazione") != 0 && (request.getParameter("nextQuestion") != null)) { //è arrivato dopo aver cliccato "domanda successiva"
                    System.out.println("caso 3");
                    action_next_case(request, response);
                    return;
                } else if((int)s.getAttribute("sondaggio-in-creazione") != 0 && (request.getParameter("confirm") != null)) { //è arrivato dopo aver cliccato "conferma"
                    System.out.println("caso 4");
                    action_confirm_case(request, response);
                    return;
                } else if((int)s.getAttribute("sondaggio-in-creazione") != 0 && (request.getParameter("removeQuestion") != null)) { //è arrivato dopo aver cliccato "elimina Domanda"
                    System.out.println("caso 5");
                    action_remove_case(request, response);
                    return;
                } else if((int)s.getAttribute("sondaggio-in-creazione") != 0 && (s.getAttribute("sondaggio-in-conferma").equals("yes"))) { //è arrivato da confirmSection
                    System.out.println("caso 6");
                    action_warning(request, response);
                    return;
                } else {
                    System.out.println("caso 7");
                    response.sendRedirect("firstSection");
                    return;
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
    
    private void action_first_case(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
       try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("noError", "yes");
            request.setAttribute("noPrev", "yes");
            request.setAttribute("noConf", "yes");
            res.activate("MakerPoll/questionsMaker.ftl", request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(QuestionsMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void action_prev_case(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
       try {
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("MakerPoll/questionsMaker.ftl", request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(QuestionsMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void action_next_case(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
       try {
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("MakerPoll/questionsMaker.ftl", request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(QuestionsMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void action_confirm_case(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
       try {
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("MakerPoll/questionsMaker.ftl", request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(QuestionsMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void action_remove_case(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
       try {
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("MakerPoll/questionsMaker.ftl", request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(QuestionsMaker.class.getName()).log(Level.SEVERE, null, ex);
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
