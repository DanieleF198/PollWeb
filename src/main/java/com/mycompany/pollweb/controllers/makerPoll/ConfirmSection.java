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
import com.mycompany.pollweb.model.Domanda;
import com.mycompany.pollweb.model.Sondaggio;
import com.mycompany.pollweb.result.FailureResult;
import com.mycompany.pollweb.security.SecurityLayer;
import static com.mycompany.pollweb.security.SecurityLayer.checkSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author joker
 */

public class ConfirmSection extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, DataException{
         try {
            HttpSession s = checkSession(request);
            PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
            if (request.getParameter("confirm") != null && s.getAttribute("sondaggio-in-conferma").equals("no")){
                //prima di andare avanti salviamo l'ultima domanda (quella da cui ha cliccato "conferma").
                //nota, in questo caso, assumiamo che se la domanda è vuota allora non la vuole caricare, se invece anche un solo campo tra "titolo", "descrizione" e "obbligatoria" è riempito/checkato
                //allora assumiamo che la domanda vuole essere caricata
                if((request.getParameter("questionTitle") != null && !request.getParameter("questionTitle").equals("")) || (request.getParameter("questionDescription") != null && !request.getParameter("questionDescription").equals("")) || (request.getParameter("questionObbligatory") != null && !request.getParameter("questionObbligatory").equals("")) || (request.getParameter("openShortConstraint") != null && !request.getParameter("openShortConstraint").equals("")) || (request.getParameter("openLongConstraint") != null && !request.getParameter("openLongConstraint").equals("")) || (request.getParameter("openNumberConstraintMin") != null && !request.getParameter("openNumberConstraintMin").equals("")) || (request.getParameter("openNumberConstraintMax") != null && !request.getParameter("openNumberConstraintMax").equals("")) || (request.getParameter("openDateConstraintMin") != null && !request.getParameter("openDateConstraintMin").equals("")) || (request.getParameter("openDateConstraintMax") != null && !request.getParameter("openDateConstraintMax").equals(""))){
                    String title = "";
                    String description = "";
                    String tipo = request.getParameter("tabGroup");
                    boolean obbligatory = false;

                    if(request.getParameter("questionTitle") != null){
                        title = SecurityLayer.addSlashes(request.getParameter("questionTitle"));
                        title = SecurityLayer.stripSlashes(title);
                    }

                    if(request.getParameter("questionDescription") != null){
                        description = SecurityLayer.addSlashes(request.getParameter("questionDescription"));
                        description = SecurityLayer.stripSlashes(description);
                    }

                    if(request.getParameter("questionObbligatory") != null){
                        if(request.getParameter("questionObbligatory").equals("obbligatory")){
                            obbligatory = true;
                        }
                    }

                    Domanda newDomanda = dl.getDomandaDAO().createDomanda();
                    newDomanda.setIdSondaggio((int)s.getAttribute("sondaggio-in-creazione"));
                    newDomanda.setTitolo(title);
                    newDomanda.setDescrizione(description);
                    newDomanda.setObbligatoria(obbligatory);


                    if(tipo.equals("openShort")){
                        newDomanda.setTipo("openShort");
                        if(request.getParameter("openShortConstraint")!=null && !request.getParameter("openShortConstraint").isEmpty()){
                            String vincolo = "Constraint: " + request.getParameter("openShortConstraint");
                            newDomanda.setVincolo(vincolo);
                        }
                    } else if (tipo.equals("openLong")) {
                        newDomanda.setTipo("openLong");
                        if(request.getParameter("openLongConstraint")!=null && !request.getParameter("openLongConstraint").isEmpty()){
                            String vincolo = "Constraint: " + request.getParameter("openLongConstraint");
                            newDomanda.setVincolo(vincolo);
                        }
                    } else if (tipo.equals("openNumber")) {
                        newDomanda.setTipo("openNumber");
                        if(request.getParameter("openNumberConstraintMin")!=null && !request.getParameter("openNumberConstraintMin").isEmpty()){
                            if(request.getParameter("openNumberConstraintMax") != null && !request.getParameter("openNumberConstraintMax").isEmpty()){
                                String vincolo = "Constraint: " + request.getParameter("openNumberConstraintMin") + " -- " + request.getParameter("openNumberConstraintMax");
                                newDomanda.setVincolo(vincolo);
                            } else {
                                String vincolo = "Constraint: " + request.getParameter("openNumberConstraintMin");
                                newDomanda.setVincolo(vincolo);
                            }
                        } else if (request.getParameter("openNumberConstraintMax") != null && !request.getParameter("openNumberConstraintMax").isEmpty()){
                            String vincolo = "Constraint: Null -- " + request.getParameter("openNumberConstraintMax");
                            newDomanda.setVincolo(vincolo);
                        }
                    } else if (tipo.equals("openDate")) {
                        newDomanda.setTipo("openDate");
                        if(request.getParameter("openDateConstraintMin")!=null && !request.getParameter("openDateConstraintMin").isEmpty()){
                            if(request.getParameter("openDateConstraintMax") != null && !request.getParameter("openDateConstraintMax").isEmpty()){
                                String strMinDate = request.getParameter("openDateConstraintMin");
                                String strMaxDate = request.getParameter("openDateConstraintMax");
                                String vincolo = "Constraint: " +strMinDate + " -- " + strMaxDate;
                                newDomanda.setVincolo(vincolo);
                            } else { 
                                String strMinDate = request.getParameter("openDateConstraintMin");
                                String vincolo = "Constraint: " +strMinDate;
                                newDomanda.setVincolo(vincolo);
                            }
                        } else if (request.getParameter("openDateConstraintMax") != null && !request.getParameter("openDateConstraintMax").isEmpty()){
                            String strMaxDate = request.getParameter("openDateConstraintMax");
                            String vincolo = "Constraint: null -- " +strMaxDate;
                            newDomanda.setVincolo(vincolo);
                        }
                    } else if (tipo.equals("closeSingle")) {
                        newDomanda.setTipo("closeSingle");
                        JSONObject opzioni = new JSONObject();
                        ArrayList<String> listOpzioni = new ArrayList<String>();
                        if(request.getParameter("option1")!=null && !request.getParameter("option1").isEmpty()){
                            listOpzioni.add(request.getParameter("option1"));
                        }
                        if(request.getParameter("option2")!=null && !request.getParameter("option2").isEmpty()){
                            listOpzioni.add(request.getParameter("option2"));
                        }
                        if(request.getParameter("option3")!=null && !request.getParameter("option3").isEmpty()){
                            listOpzioni.add(request.getParameter("option3"));
                        }
                        if(request.getParameter("option4")!=null && !request.getParameter("option4").isEmpty()){
                            listOpzioni.add(request.getParameter("option4"));
                        }
                        if(request.getParameter("option5")!=null && !request.getParameter("option5").isEmpty()){
                            listOpzioni.add(request.getParameter("option5"));
                        }
                        if(request.getParameter("option6")!=null && !request.getParameter("option6").isEmpty()){
                            listOpzioni.add(request.getParameter("option6"));
                        }
                        if(request.getParameter("option7")!=null && !request.getParameter("option7").isEmpty()){
                            listOpzioni.add(request.getParameter("option7"));
                        }
                        if(request.getParameter("option8")!=null && !request.getParameter("option8").isEmpty()){
                            listOpzioni.add(request.getParameter("option8"));
                        }
                        if(request.getParameter("option9")!=null && !request.getParameter("option9").isEmpty()){
                            listOpzioni.add(request.getParameter("option9"));
                        }
                        if(request.getParameter("option10")!=null && !request.getParameter("option10").isEmpty()){
                            listOpzioni.add(request.getParameter("option10"));
                        }
                        if(request.getParameter("option11")!=null && !request.getParameter("option11").isEmpty()){
                            listOpzioni.add(request.getParameter("option11"));
                        }
                        if(request.getParameter("option12")!=null && !request.getParameter("option12").isEmpty()){
                            listOpzioni.add(request.getParameter("option12"));
                        }
                        opzioni.put("opzioni", new JSONArray(listOpzioni));
                        newDomanda.setOpzioni(opzioni);
                    } else if (tipo.equals("closeMultiple")) {
                        newDomanda.setTipo("closeMultiple");
                        JSONObject opzioni = new JSONObject();
                        ArrayList<String> listOpzioni = new ArrayList<String>();
                        if(request.getParameter("option1m")!=null && !request.getParameter("option1m").isEmpty()){
                            listOpzioni.add(request.getParameter("option1m"));
                        }
                        if(request.getParameter("option2m")!=null && !request.getParameter("option2m").isEmpty()){
                            listOpzioni.add(request.getParameter("option2m"));
                        }
                        if(request.getParameter("option3m")!=null && !request.getParameter("option3m").isEmpty()){
                            listOpzioni.add(request.getParameter("option3m"));
                        }
                        if(request.getParameter("option4m")!=null && !request.getParameter("option4m").isEmpty()){
                            listOpzioni.add(request.getParameter("option4m"));
                        }
                        if(request.getParameter("option5m")!=null && !request.getParameter("option5m").isEmpty()){
                            listOpzioni.add(request.getParameter("option5m"));
                        }
                        if(request.getParameter("option6m")!=null && !request.getParameter("option6m").isEmpty()){
                            listOpzioni.add(request.getParameter("option6m"));
                        }
                        if(request.getParameter("option7m")!=null && !request.getParameter("option7m").isEmpty()){
                            listOpzioni.add(request.getParameter("option7m"));
                        }
                        if(request.getParameter("option8m")!=null && !request.getParameter("option8m").isEmpty()){
                            listOpzioni.add(request.getParameter("option8m"));
                        }
                        if(request.getParameter("option9m")!=null && !request.getParameter("option9m").isEmpty()){
                            listOpzioni.add(request.getParameter("option9m"));
                        }
                        if(request.getParameter("option10m")!=null  && !request.getParameter("option10m").isEmpty()){
                            listOpzioni.add(request.getParameter("option10m"));
                        }
                        if(request.getParameter("option11m")!=null && !request.getParameter("option11m").isEmpty()){
                            listOpzioni.add(request.getParameter("option11m"));
                        }
                        if(request.getParameter("option12m")!=null && !request.getParameter("option12m").isEmpty()){
                            listOpzioni.add(request.getParameter("option12m"));
                        }
                        opzioni.put("opzioni", new JSONArray(listOpzioni));
                        newDomanda.setOpzioni(opzioni);
                    }

                    //caso in cui l'utente ha creato il sondaggio (non confermato o quel che vuoi, ma creato si) e ritorna qui da firstSection per modificare le domande
                    //in questo caso, quando passerà per l'ultima domanda effettivamente creata, vedra che non esiste la prossima domanda, ma questo non vuol dire che che 
                    //la domanda corrente và "creata", ma va solo aggiornato. Data la gestione fatta fin'ora con "update-domanda" nel sessionamento me la gestiro con un if

                    Domanda domandaE = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), (int)s.getAttribute("domanda-in-creazione"));

                    newDomanda.setPosizione((int)s.getAttribute("domanda-in-creazione"));
                    System.out.println(s.getAttribute("updateDomanda"));
                    if(s.getAttribute("updateDomanda")!=null){
                        newDomanda.setKey((int)s.getAttribute("updateDomanda"));
                        s.removeAttribute("updateDomanda");
                    }
                    if(domandaE!=null){
                        newDomanda.setKey(domandaE.getKey());
                    }

                    dl.getDomandaDAO().storeDomanda(newDomanda);
                }
                s.setAttribute("domanda-in-creazione", 0); //resetto domanda in creazione, in caso torni indietro lo porterò alla warning
                s.setAttribute("sondaggio-in-conferma", "yes");
                action_default(request, response);
                return;
            }else if("POST".equals(request.getMethod()) && request.getParameter("removeQuestion") != null){
                int position = Integer.parseInt(request.getParameter("removeQuestion").substring(20));
                Domanda domandaToRemove = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), position);
                if(domandaToRemove!=null){
                    dl.getDomandaDAO().deleteDomanda(domandaToRemove.getKey());
                }
                else {
                    action_warning(request, response);
                }
                position++;
                Domanda domandaSuccessiva;
                if(position == 1){
                    domandaSuccessiva = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), position); 
                    if(domandaSuccessiva== null){
                        action_default(request, response);
                        return;
                    }
                }
                for(int i = 0; i < 1; i--){
                    domandaSuccessiva = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), position);
                    if(domandaSuccessiva != null){
                        domandaSuccessiva.setPosizione(position - 1);
                        dl.getDomandaDAO().storeDomanda(domandaSuccessiva);
                        position++;
                    }
                    else{
                        break;
                    }
                }
                action_default(request, response);
                return;
            }else if("POST".equals(request.getMethod()) && request.getParameter("goUpQuestion") != null){ //goUp Vuol dire diminuire la posizione (so che suona strano)
                int position = Integer.parseInt(request.getParameter("goUpQuestion").substring(18));
                Domanda domandaToGoUp = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), position);
                Domanda domandaToGoDown = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), position-1);
                domandaToGoUp.setPosizione(position-1);
                domandaToGoDown.setPosizione(position);
                dl.getDomandaDAO().storeDomanda(domandaToGoUp);
                dl.getDomandaDAO().storeDomanda(domandaToGoDown);
                action_default(request, response);
                return;
            }else if("POST".equals(request.getMethod()) && request.getParameter("goDownQuestion") != null){ //goDown Vuol dire aumentare la posizione (so che suona strano)
                int position = Integer.parseInt(request.getParameter("goDownQuestion").substring(20));
                Domanda domandaToGoDown = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), position);
                Domanda domandaToGoUp = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), position+1);
                domandaToGoDown.setPosizione(position+1);
                domandaToGoUp.setPosizione(position);
                dl.getDomandaDAO().storeDomanda(domandaToGoDown);
                dl.getDomandaDAO().storeDomanda(domandaToGoUp);
                action_default(request, response);
                return;
            } else {
                action_warning(request, response);
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

    private void action_redirect(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        response.sendRedirect("confirmSection");
        return;
    }
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
       try {
            TemplateResult res = new TemplateResult(getServletContext());  
            HttpSession s = checkSession(request);
            PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
            ArrayList<Domanda> domande = (ArrayList<Domanda>) dl.getDomandaDAO().getDomandaByIdSondaggio((Integer)s.getAttribute("sondaggio-in-creazione"));
            Collections.sort(domande);
            request.setAttribute("domande", domande);
            res.activate("MakerPoll/confirmSection.ftl", request, response);
            return;
        } catch (TemplateManagerException ex) {
            Logger.getLogger(ConfirmSection.class.getName()).log(Level.SEVERE, null, ex);
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

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

}
