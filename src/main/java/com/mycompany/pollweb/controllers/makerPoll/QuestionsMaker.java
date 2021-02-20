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
import com.mycompany.pollweb.result.SplitSlashesFmkExt;
import com.mycompany.pollweb.security.SecurityLayer;
import static com.mycompany.pollweb.security.SecurityLayer.checkSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import static jdk.internal.org.jline.utils.Colors.s;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author joker
 */

public class QuestionsMaker extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, DataException{        
         try {
            HttpSession s = checkSession(request);
            if (s!= null) {
                if ((Integer)s.getAttribute("groupid") == 1){
                    response.sendRedirect("../partecipantDashboard");
                    return;
                }
                if((int)s.getAttribute("sondaggio-in-creazione") != 0 && (s.getAttribute("fromFirst") != null) && (s.getAttribute("sondaggio-in-conferma").equals("no"))){ //è arrivato da firstSection cliccando su bottone
                    System.out.println("caso 1");
                    s.removeAttribute("fromFirst");
                    action_first_case(request, response);
                    return;
                } else if(request.getParameter("returnQuestions") != null){ //è arrivato da confirmSection cliccando su bottone
                    System.out.println("caso 2");
                    s.setAttribute("sondaggio-in-conferma", "no");
                    PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
                    Domanda controlDomanda = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), 0);
                    if(controlDomanda == null){
                        if(s.getAttribute("updateDomanda")!= null){
                            s.removeAttribute("updateDomanda");
                        }
                    } else {
                        s.setAttribute("updateDomanda", 0);
                    }
                    action_first_case(request, response);
                    return;
                } else if((int)s.getAttribute("sondaggio-in-creazione") != 0 && (s.getAttribute("sondaggio-in-conferma").equals("yes"))) { //è arrivato da confirmSection senza cliccare su "torna alla sezione domande"
                    System.out.println("caso 3");
                    action_warning(request, response);
                    return;
                } else if((int)s.getAttribute("sondaggio-in-creazione") != 0 && "POST".equals(request.getMethod())  && (request.getParameter("prevQuestion") != null) && (s.getAttribute("sondaggio-in-conferma").equals("no"))) { //è arrivato dopo aver cliccato "domanda precedente"
                    System.out.println("caso 4");
                    action_prev_case(request, response);
                    return;
                } else if((int)s.getAttribute("sondaggio-in-creazione") != 0 && "POST".equals(request.getMethod())  && (request.getParameter("nextQuestion") != null) && (s.getAttribute("sondaggio-in-conferma").equals("no"))) { //è arrivato dopo aver cliccato "domanda successiva"
                    System.out.println("caso 5");
                    action_next_case(request, response);
                    return;
                } else if((int)s.getAttribute("sondaggio-in-creazione") != 0 && "POST".equals(request.getMethod()) && (request.getParameter("removeQuestion") != null) && (s.getAttribute("sondaggio-in-conferma").equals("no"))) { //è arrivato dopo aver cliccato "elimina Domanda"
                    System.out.println("caso 6");
                    action_remove_case(request, response);
                    return;
                } else if (request.getParameter("confirm") != null && s.getAttribute("sondaggio-in-conferma").equals("no")){
                    action_confirm_redirect(request,response);
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
    
    private void action_first_case(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
       try {
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession s = checkSession(request);
            PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
            Domanda domanda = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), 0);
            if (domanda!=null){
                
                if(domanda.getPosizione() == 0){
                    request.setAttribute("noPrev", "yes");
                }
                if(domanda.getTitolo()!=null && !domanda.getTitolo().isEmpty()){
                    request.setAttribute("titleQuestion", SecurityLayer.stripSlashes((String)domanda.getTitolo()));
                }
                if(domanda.getDescrizione()!=null && !domanda.getDescrizione().isEmpty()){
                    request.setAttribute("description", SecurityLayer.stripSlashes((String)domanda.getDescrizione()));
                }
                if(domanda.isObbligatoria()){
                    request.setAttribute("obbligatory", "yes");
                } else {
                    request.setAttribute("obbligatory", "no");
                }
                if(domanda.getTipo().equals("openShort")){
                    if(domanda.getVincolo()!=null && !domanda.getVincolo().isEmpty()){
                        System.out.println(domanda.getVincolo());
                        Pattern p = Pattern.compile("\\d+");
                        Matcher m = p.matcher(domanda.getVincolo());
                        if(m.find(0)){
                            String constraint = m.group(0);
                            System.out.println(constraint);
                            request.setAttribute("openShortConstraint", constraint);
                        }
                    }
                    request.setAttribute("checked", "openShort");
                } else if(domanda.getTipo().equals("openLong")){
                    if(domanda.getVincolo()!=null && !domanda.getVincolo().isEmpty()){
                        Pattern p = Pattern.compile("\\d+");
                        Matcher m = p.matcher(domanda.getVincolo());
                        if(m.find(0)){
                            String constraint = m.group(0);
                            System.out.println(constraint);
                            request.setAttribute("openLongConstraint", constraint);
                        }
                    }
                    request.setAttribute("checked", "openLong");
                } else if(domanda.getTipo().equals("openNumber")){
                    if(domanda.getVincolo()!=null && !domanda.getVincolo().isEmpty()){
                        Pattern p = Pattern.compile("\\d+");
                        Matcher m = p.matcher(domanda.getVincolo());
                        if(domanda.getVincolo().contains("Null --")){
                            int indexOf = domanda.getVincolo().indexOf("--");
                            String secondPart = domanda.getVincolo().substring(indexOf);
                            System.out.println(secondPart);
                            m = p.matcher(secondPart);
                            if(m.find(0)){
                                String constraintMax = m.group(0);
                                request.setAttribute("openNumberConstraintMax", constraintMax);
                            }
                        } else if (domanda.getVincolo().contains("--")){
                            if(m.find(0)){ 
                                String constraintMin = m.group(0);
                                int indexOf = domanda.getVincolo().indexOf("--");
                                String secondPart = domanda.getVincolo().substring(indexOf);
                                System.out.println(secondPart);
                                m = p.matcher(secondPart);
                                if(m.find(0)){
                                    String constraintMax = m.group(0);
                                    request.setAttribute("openNumberConstraintMin", constraintMin);
                                    request.setAttribute("openNumberConstraintMax", constraintMax);
                                }
                            }
                        } else {
                            if(m.find(0)){ 
                                String constraintMin = m.group(0);
                                request.setAttribute("openNumberConstraintMin", constraintMin);
                            }
                        }
                    }
                    request.setAttribute("checked", "openNumber"); 
                } else if(domanda.getTipo().equals("openDate")){
                    if(domanda.getVincolo()!=null && !domanda.getVincolo().isEmpty()){
                        Pattern p = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
                        Matcher m = p.matcher(domanda.getVincolo());
                        if(domanda.getVincolo().contains("Null --")){
                            int indexOf = domanda.getVincolo().indexOf("--");
                            String secondPart = domanda.getVincolo().substring(indexOf);
                            m = p.matcher(secondPart);
                            if(m.find(0)){
                                String constraintMax = m.group(0);
                                request.setAttribute("openDateConstraintMax", constraintMax);
                            }
                        } else if (domanda.getVincolo().contains("--")){
                            if(m.find(0)){ 
                                String constraintMin = m.group(0);
                                int indexOf = domanda.getVincolo().indexOf("--");
                                String secondPart = domanda.getVincolo().substring(indexOf);
                                m = p.matcher(secondPart);
                                if(m.find(0)){
                                    String constraintMax = m.group(0);
                                    request.setAttribute("openDateConstraintMin", constraintMin);
                                    request.setAttribute("openDateConstraintMax", constraintMax);
                                }
                            }
                        } else {
                            if(m.find(0)){ 
                                String constraintMin = m.group(0);
                                request.setAttribute("openDateConstraintMin", constraintMin);
                            }
                        }
                    }
                    request.setAttribute("checked", "openDate");
                } else if(domanda.getTipo().equals("closeSingle")){
                    request.setAttribute("checked", "closeSingle");
                    JSONArray opzioni = domanda.getOpzioni().getJSONArray("opzioni");
                    for(int i = 0; i < opzioni.length(); i++){
                        request.setAttribute("option"+ i, SecurityLayer.stripSlashes((String)opzioni.get(i)));
                    }
                } else if(domanda.getTipo().equals("closeMultiple")){
                    request.setAttribute("checked", "closeMultiple");
                    JSONArray opzioni = domanda.getOpzioni().getJSONArray("opzioni");
                    for(int i = 0; i < opzioni.length(); i++){
                        request.setAttribute("option"+ i + "m", SecurityLayer.stripSlashes((String)opzioni.get(i)));
                    }
                }
                request.setAttribute("noPrev", "yes");
                
            } else {
                
                request.setAttribute("noError", "yes");
                request.setAttribute("noPrev", "yes");
                
            }
            System.out.println("valore update:" + s.getAttribute("updateDomanda"));
            System.out.println("domanda corrente:" + s.getAttribute("domanda-in-creazione"));
            request.setAttribute("numeroDomanda", (int)s.getAttribute("domanda-in-creazione"));
            res.activate("MakerPoll/questionsMaker.ftl", request, response);
            return;
        } catch (TemplateManagerException ex) {
            Logger.getLogger(QuestionsMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void action_prev_case(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
       try {
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession s = checkSession(request);
            if((int)s.getAttribute("domanda-in-creazione")==0){
                action_warning(request, response);
                return;
            }
            PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
            int position = (int)s.getAttribute("domanda-in-creazione") - 1;
            Domanda domanda = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), position);
            
            //prima di occuparci di prenderci i dati di prevDomanda carichiamo eventuali dati di currentDomanda
            //ovviamente dobbiamo riconoscere il caso in cui current domanda è effettivamente esistente(cioè se è la prima volta che stiamo cliccando "prev" o meno)
            
            if(s.getAttribute("updateDomanda")!=null && request.getAttribute("fromRemove") == null){
            
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

                Domanda currentDomanda = dl.getDomandaDAO().createDomanda();
                currentDomanda.setIdSondaggio((int)s.getAttribute("sondaggio-in-creazione"));
                currentDomanda.setTitolo(SecurityLayer.addSlashes(title));
                currentDomanda.setDescrizione(SecurityLayer.addSlashes(description));
                currentDomanda.setObbligatoria(obbligatory);


                if(tipo.equals("openShort")){
                    currentDomanda.setTipo("openShort");
                    if(request.getParameter("openShortConstraint")!=null && !request.getParameter("openShortConstraint").isEmpty()){
                        String vincolo = "Constraint: " + request.getParameter("openShortConstraint");
                        currentDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                    }
                } else if (tipo.equals("openLong")) {
                    currentDomanda.setTipo("openLong");
                    if(request.getParameter("openLongConstraint")!=null && !request.getParameter("openLongConstraint").isEmpty()){
                        String vincolo = "Constraint: " + request.getParameter("openLongConstraint");
                        currentDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                    }
                } else if (tipo.equals("openNumber")) {
                    currentDomanda.setTipo("openNumber");
                    if(request.getParameter("openNumberConstraintMin")!=null && !request.getParameter("openNumberConstraintMin").isEmpty()){
                        if(request.getParameter("openNumberConstraintMax") != null && !request.getParameter("openNumberConstraintMax").isEmpty()){
                            String vincolo = "Constraint: " + request.getParameter("openNumberConstraintMin") + " -- " + request.getParameter("openNumberConstraintMax");
                            currentDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                        } else {
                            String vincolo = "Constraint: " + request.getParameter("openNumberConstraintMin");
                            currentDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                        }
                    } else if (request.getParameter("openNumberConstraintMax") != null && !request.getParameter("openNumberConstraintMax").isEmpty()){
                        String vincolo = "Constraint: Null -- " + request.getParameter("openNumberConstraintMax");
                        currentDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                    }
                } else if (tipo.equals("openDate")) {
                    currentDomanda.setTipo("openDate");
                    if(request.getParameter("openDateConstraintMin")!=null && !request.getParameter("openDateConstraintMin").isEmpty()){
                        if(request.getParameter("openDateConstraintMax") != null && !request.getParameter("openDateConstraintMax").isEmpty()){
                            String strMinDate = request.getParameter("openDateConstraintMin");
                            String strMaxDate = request.getParameter("openDateConstraintMax");
                            String vincolo = "Constraint: " +strMinDate + " -- " + strMaxDate;
                            currentDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                        } else { 
                            String strMinDate = request.getParameter("openDateConstraintMin");
                            String vincolo = "Constraint: " +strMinDate;
                            currentDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                        }
                    } else if (request.getParameter("openDateConstraintMax") != null && !request.getParameter("openDateConstraintMax").isEmpty()){
                        String strMaxDate = request.getParameter("openDateConstraintMax");
                        String vincolo = "Constraint: Null -- " +strMaxDate;
                        currentDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                    }
                } else if (tipo.equals("closeSingle")) {
                    currentDomanda.setTipo("closeSingle");
                    JSONObject opzioni = new JSONObject();
                    ArrayList<String> listOpzioni = new ArrayList<String>();
                    if(request.getParameter("option1")!=null && !request.getParameter("option1").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option1")));
                    }
                    if(request.getParameter("option2")!=null && !request.getParameter("option2").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option2")));
                    }
                    if(request.getParameter("option3")!=null && !request.getParameter("option3").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option3")));
                    }
                    if(request.getParameter("option4")!=null && !request.getParameter("option4").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option4")));
                    }
                    if(request.getParameter("option5")!=null && !request.getParameter("option5").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option5")));
                    }
                    if(request.getParameter("option6")!=null && !request.getParameter("option6").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option6")));
                    }
                    if(request.getParameter("option7")!=null && !request.getParameter("option7").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option7")));
                    }
                    if(request.getParameter("option8")!=null && !request.getParameter("option8").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option8")));
                    }
                    if(request.getParameter("option9")!=null && !request.getParameter("option9").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option9")));
                    }
                    if(request.getParameter("option10")!=null && !request.getParameter("option10").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option10")));
                    }
                    if(request.getParameter("option11")!=null && !request.getParameter("option11").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option11")));
                    }
                    if(request.getParameter("option12")!=null && !request.getParameter("option12").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option12")));
                    }
                    opzioni.put("opzioni", new JSONArray(listOpzioni));
                    currentDomanda.setOpzioni(opzioni);
                } else if (tipo.equals("closeMultiple")) {
                    currentDomanda.setTipo("closeMultiple");
                    JSONObject opzioni = new JSONObject();
                    ArrayList<String> listOpzioni = new ArrayList<String>();
                    if(request.getParameter("option1m")!=null && !request.getParameter("option1m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option1m")));
                    }
                    if(request.getParameter("option2m")!=null && !request.getParameter("option2m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option2m")));
                    }
                    if(request.getParameter("option3m")!=null && !request.getParameter("option3m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option3m")));
                    }
                    if(request.getParameter("option4m")!=null && !request.getParameter("option4m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option4m")));
                    }
                    if(request.getParameter("option5m")!=null && !request.getParameter("option5m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option5m")));
                    }
                    if(request.getParameter("option6m")!=null && !request.getParameter("option6m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option6m")));
                    }
                    if(request.getParameter("option7m")!=null && !request.getParameter("option7m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option7m")));
                    }
                    if(request.getParameter("option8m")!=null && !request.getParameter("option8m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option8m")));
                    }
                    if(request.getParameter("option9m")!=null && !request.getParameter("option9m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option9m")));
                    }
                    if(request.getParameter("option10m")!=null  && !request.getParameter("option10m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option10m")));
                    }
                    if(request.getParameter("option11m")!=null && !request.getParameter("option11m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option11m")));
                    }
                    if(request.getParameter("option12m")!=null && !request.getParameter("option12m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option12m")));
                    }
                    opzioni.put("opzioni", new JSONArray(listOpzioni));
                    currentDomanda.setOpzioni(opzioni);
                }

                currentDomanda.setPosizione((int)s.getAttribute("domanda-in-creazione"));
                if(s.getAttribute("updateDomanda")!=null){
                    Domanda domandaToUpdate = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), (int)s.getAttribute("updateDomanda"));
                    System.out.println("Ehy, la key è:" + domandaToUpdate.getKey());
                    currentDomanda.setKey(domandaToUpdate.getKey());
                    currentDomanda.setVersion(domandaToUpdate.getVersion());
                }
                dl.getDomandaDAO().storeDomanda(currentDomanda);
            }
            
            //ancora prima di prev domanda dobbiamo assicurarci che non sia il caso in cui a)sta cliccando per la prima volta "prevDomanda", b)ha scritto qualcosa nel prev
            //cioè una sorta di next nel caso base al contrario

            
            if(s.getAttribute("updateDomanda")==null && ((request.getParameter("questionTitle") != null && !request.getParameter("questionTitle").equals("")) || (request.getParameter("questionDescription") != null && !request.getParameter("questionDescription").equals("")) || (request.getParameter("questionObbligatory") != null && !request.getParameter("questionObbligatory").equals("")) || (request.getParameter("openShortConstraint") != null && !request.getParameter("openShortConstraint").equals("")) || (request.getParameter("openLongConstraint") != null && !request.getParameter("openLongConstraint").equals("")) || (request.getParameter("openNumberConstraintMin") != null && !request.getParameter("openNumberConstraintMin").equals("")) || (request.getParameter("openNumberConstraintMax") != null && !request.getParameter("openNumberConstraintMax").equals("")) || (request.getParameter("openDateConstraintMin") != null && !request.getParameter("openDateConstraintMin").equals("")) || (request.getParameter("openDateConstraintMax") != null && !request.getParameter("openDateConstraintMax").equals("")))){
                //controllo su quale sia checkato non lo facciamo perché qualcosa sarà sempre checkato, e se il tipo non ha avuto la voglia di scriversi quantomeno il titolo o la descrizione o chacckare se è obbligatorio
                //assumiamo che voglia tornare alla domanda precedente senza aver ancora iniziato a creare la corrente. Altrimenti glieli memorizziamo temporaneamente
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
                newDomanda.setTitolo(SecurityLayer.addSlashes(title));
                newDomanda.setDescrizione(SecurityLayer.addSlashes(description));
                newDomanda.setObbligatoria(obbligatory);


                if(tipo.equals("openShort")){
                    newDomanda.setTipo("openShort");
                    if(request.getParameter("openShortConstraint")!=null && !request.getParameter("openShortConstraint").isEmpty()){
                        String vincolo = "Constraint: " + request.getParameter("openShortConstraint");
                        newDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                    }
                } else if (tipo.equals("openLong")) {
                    newDomanda.setTipo("openLong");
                    if(request.getParameter("openLongConstraint")!=null && !request.getParameter("openLongConstraint").isEmpty()){
                        String vincolo = "Constraint: " + request.getParameter("openLongConstraint");
                        newDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                    }
                } else if (tipo.equals("openNumber")) {
                    newDomanda.setTipo("openNumber");
                    if(request.getParameter("openNumberConstraintMin")!=null && !request.getParameter("openNumberConstraintMin").isEmpty()){
                        if(request.getParameter("openNumberConstraintMax") != null && !request.getParameter("openNumberConstraintMax").isEmpty()){
                            String vincolo = "Constraint: " + request.getParameter("openNumberConstraintMin") + " -- " + request.getParameter("openNumberConstraintMax");
                            newDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                        } else {
                            String vincolo = "Constraint: " + request.getParameter("openNumberConstraintMin");
                            newDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                        }
                    } else if (request.getParameter("openNumberConstraintMax") != null && !request.getParameter("openNumberConstraintMax").isEmpty()){
                        String vincolo = "Constraint: Null -- " + request.getParameter("openNumberConstraintMax");
                        newDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                    }
                } else if (tipo.equals("openDate")) {
                    newDomanda.setTipo("openDate");
                    if(request.getParameter("openDateConstraintMin")!=null && !request.getParameter("openDateConstraintMin").isEmpty()){
                        if(request.getParameter("openDateConstraintMax") != null && !request.getParameter("openDateConstraintMax").isEmpty()){
                            String strMinDate = request.getParameter("openDateConstraintMin");
                            String strMaxDate = request.getParameter("openDateConstraintMax");
                            String vincolo = "Constraint: " +strMinDate + " -- " + strMaxDate;
                            newDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                        } else { 
                            String strMinDate = request.getParameter("openDateConstraintMin");
                            String vincolo = "Constraint: " +strMinDate;
                            newDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                        }
                    } else if (request.getParameter("openDateConstraintMax") != null && !request.getParameter("openDateConstraintMax").isEmpty()){
                        String strMaxDate = request.getParameter("openDateConstraintMax");
                        String vincolo = "Constraint: Null -- " +strMaxDate;
                        newDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                    }
                } else if (tipo.equals("closeSingle")) {
                    newDomanda.setTipo("closeSingle");
                    JSONObject opzioni = new JSONObject();
                    ArrayList<String> listOpzioni = new ArrayList<String>();
                    if(request.getParameter("option1")!=null && !request.getParameter("option1").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option1")));
                    }
                    if(request.getParameter("option2")!=null && !request.getParameter("option2").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option2")));
                    }
                    if(request.getParameter("option3")!=null && !request.getParameter("option3").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option3")));
                    }
                    if(request.getParameter("option4")!=null && !request.getParameter("option4").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option4")));
                    }
                    if(request.getParameter("option5")!=null && !request.getParameter("option5").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option5")));
                    }
                    if(request.getParameter("option6")!=null && !request.getParameter("option6").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option6")));
                    }
                    if(request.getParameter("option7")!=null && !request.getParameter("option7").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option7")));
                    }
                    if(request.getParameter("option8")!=null && !request.getParameter("option8").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option8")));
                    }
                    if(request.getParameter("option9")!=null && !request.getParameter("option9").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option9")));
                    }
                    if(request.getParameter("option10")!=null && !request.getParameter("option10").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option10")));
                    }
                    if(request.getParameter("option11")!=null && !request.getParameter("option11").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option11")));
                    }
                    if(request.getParameter("option12")!=null && !request.getParameter("option12").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option12")));
                    }
                    opzioni.put("opzioni", new JSONArray(listOpzioni));
                    newDomanda.setOpzioni(opzioni);
                } else if (tipo.equals("closeMultiple")) {
                    newDomanda.setTipo("closeMultiple");
                    JSONObject opzioni = new JSONObject();
                    ArrayList<String> listOpzioni = new ArrayList<String>();
                    if(request.getParameter("option1m")!=null && !request.getParameter("option1m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option1m")));
                    }
                    if(request.getParameter("option2m")!=null && !request.getParameter("option2m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option2m")));
                    }
                    if(request.getParameter("option3m")!=null && !request.getParameter("option3m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option3m")));
                    }
                    if(request.getParameter("option4m")!=null && !request.getParameter("option4m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option4m")));
                    }
                    if(request.getParameter("option5m")!=null && !request.getParameter("option5m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option5m")));
                    }
                    if(request.getParameter("option6m")!=null && !request.getParameter("option6m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option6m")));
                    }
                    if(request.getParameter("option7m")!=null && !request.getParameter("option7m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option7m")));
                    }
                    if(request.getParameter("option8m")!=null && !request.getParameter("option8m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option8m")));
                    }
                    if(request.getParameter("option9m")!=null && !request.getParameter("option9m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option9m")));
                    }
                    if(request.getParameter("option10m")!=null  && !request.getParameter("option10m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option10m")));
                    }
                    if(request.getParameter("option11m")!=null && !request.getParameter("option11m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option11m")));
                    }
                    if(request.getParameter("option12m")!=null && !request.getParameter("option12m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option12m")));
                    }
                    opzioni.put("opzioni", new JSONArray(listOpzioni));
                    newDomanda.setOpzioni(opzioni);
                }

                newDomanda.setPosizione((int)s.getAttribute("domanda-in-creazione"));
                //non ci poniamo il problema dell'update perché in questo caso siamo sicuri che la domanda ancora non esiste
                dl.getDomandaDAO().storeDomanda(newDomanda); 
            }
            
            //prevDomanda
            if(domanda.getPosizione() == 0){
                request.setAttribute("noPrev", "yes");
            }
            if(domanda.getTitolo()!=null && !domanda.getTitolo().isEmpty()){
                request.setAttribute("titleQuestion", SecurityLayer.stripSlashes((String)domanda.getTitolo()));
            }
            if(domanda.getDescrizione()!=null && !domanda.getDescrizione().isEmpty()){
                request.setAttribute("description", SecurityLayer.stripSlashes((String)domanda.getDescrizione()));
            }
            if(domanda.isObbligatoria()){
                request.setAttribute("obbligatory", "yes");
            } else {
                request.setAttribute("obbligatory", "no");
            }
            if(domanda.getTipo().equals("openShort")){
                if(domanda.getVincolo()!=null && !domanda.getVincolo().isEmpty()){
                    System.out.println(domanda.getVincolo());
                    Pattern p = Pattern.compile("\\d+");
                    Matcher m = p.matcher(domanda.getVincolo());
                    if(m.find(0)){
                        String constraint = m.group(0);
                        System.out.println(constraint);
                        request.setAttribute("openShortConstraint", constraint);
                    }
                }
                request.setAttribute("checked", "openShort");
            } else if(domanda.getTipo().equals("openLong")){
                if(domanda.getVincolo()!=null && !domanda.getVincolo().isEmpty()){
                    Pattern p = Pattern.compile("\\d+");
                    Matcher m = p.matcher(domanda.getVincolo());
                    if(m.find(0)){
                        String constraint = m.group(0);
                        System.out.println(constraint);
                        request.setAttribute("openLongConstraint", constraint);
                    }
                }
                request.setAttribute("checked", "openLong");
            } else if(domanda.getTipo().equals("openNumber")){
                if(domanda.getVincolo()!=null && !domanda.getVincolo().isEmpty()){
                    Pattern p = Pattern.compile("\\d+");
                    Matcher m = p.matcher(domanda.getVincolo());
                    if(domanda.getVincolo().contains("Null --")){
                        int indexOf = domanda.getVincolo().indexOf("--");
                        String secondPart = domanda.getVincolo().substring(indexOf);
                        System.out.println(secondPart);
                        m = p.matcher(secondPart);
                        if(m.find(0)){
                            String constraintMax = m.group(0);
                            request.setAttribute("openNumberConstraintMax", constraintMax);
                        }
                    } else if (domanda.getVincolo().contains("--")){
                        if(m.find(0)){ 
                            String constraintMin = m.group(0);
                            int indexOf = domanda.getVincolo().indexOf("--");
                            String secondPart = domanda.getVincolo().substring(indexOf);
                            System.out.println(secondPart);
                            m = p.matcher(secondPart);
                            if(m.find(0)){
                                String constraintMax = m.group(0);
                                request.setAttribute("openNumberConstraintMin", constraintMin);
                                request.setAttribute("openNumberConstraintMax", constraintMax);
                            }
                        }
                    } else {
                        if(m.find(0)){ 
                            String constraintMin = m.group(0);
                            request.setAttribute("openNumberConstraintMin", constraintMin);
                        }
                    }
                }
                request.setAttribute("checked", "openNumber"); 
            } else if(domanda.getTipo().equals("openDate")){
                if(domanda.getVincolo()!=null && !domanda.getVincolo().isEmpty()){
                    Pattern p = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
                    Matcher m = p.matcher(domanda.getVincolo());
                    if(domanda.getVincolo().contains("Null --")){
                        int indexOf = domanda.getVincolo().indexOf("--");
                        String secondPart = domanda.getVincolo().substring(indexOf);
                        m = p.matcher(secondPart);
                        if(m.find(0)){
                            String constraintMax = m.group(0);
                            request.setAttribute("openDateConstraintMax", constraintMax);
                        }
                    } else if (domanda.getVincolo().contains("--")){
                        if(m.find(0)){ 
                            String constraintMin = m.group(0);
                            int indexOf = domanda.getVincolo().indexOf("--");
                            String secondPart = domanda.getVincolo().substring(indexOf);
                            m = p.matcher(secondPart);
                            if(m.find(0)){
                                String constraintMax = m.group(0);
                                request.setAttribute("openDateConstraintMin", constraintMin);
                                request.setAttribute("openDateConstraintMax", constraintMax);
                            }
                        }
                    } else {
                        if(m.find(0)){ 
                            String constraintMin = m.group(0);
                            request.setAttribute("openDateConstraintMin", constraintMin);
                        }
                    }
                }
                request.setAttribute("checked", "openDate");
            } else if(domanda.getTipo().equals("closeSingle")){
                request.setAttribute("checked", "closeSingle");
                JSONArray opzioni = domanda.getOpzioni().getJSONArray("opzioni");
                for(int i = 0; i < opzioni.length(); i++){
                    request.setAttribute("option"+ i, SecurityLayer.stripSlashes((String)opzioni.get(i)));
                }
            } else if(domanda.getTipo().equals("closeMultiple")){
                request.setAttribute("checked", "closeMultiple");
                JSONArray opzioni = domanda.getOpzioni().getJSONArray("opzioni");
                for(int i = 0; i < opzioni.length(); i++){
                    request.setAttribute("option"+ i + "m", SecurityLayer.stripSlashes((String)opzioni.get(i)));
                }
            }
            s.setAttribute("domanda-in-creazione", domanda.getPosizione());
            s.setAttribute("updateDomanda", domanda.getPosizione());
            System.out.println("valore update:" + s.getAttribute("updateDomanda"));
            System.out.println("domanda corrente:" + s.getAttribute("domanda-in-creazione"));
            System.out.println(request.getAttribute("description"));
            request.setAttribute("numeroDomanda", (int)s.getAttribute("domanda-in-creazione"));
            res.activate("MakerPoll/questionsMaker.ftl", request, response);
            return;
        } catch (TemplateManagerException ex) {
            Logger.getLogger(QuestionsMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void action_next_case(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
       try {
            PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession s = checkSession(request);
            int position = (int)s.getAttribute("domanda-in-creazione") +1;
            Domanda domanda = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), position);
            if(domanda!=null){ //caso in cui la prossima domanda già esiste (vuol dire che abbiamo cliccato "domanda precedente" e poi "domanda successiva"
                
                //prima di occuparci di prenderci i dati di nextDomanda carichiamo eventuali dati di prevDomanda
                
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

                Domanda oldDomanda = dl.getDomandaDAO().createDomanda();
                oldDomanda.setIdSondaggio((int)s.getAttribute("sondaggio-in-creazione"));
                oldDomanda.setTitolo(SecurityLayer.addSlashes(title));
                oldDomanda.setDescrizione(SecurityLayer.addSlashes(description));
                oldDomanda.setObbligatoria(obbligatory);


                if(tipo.equals("openShort")){
                    oldDomanda.setTipo("openShort");
                    if(request.getParameter("openShortConstraint")!=null && !request.getParameter("openShortConstraint").isEmpty()){
                        String vincolo = "Constraint: " + request.getParameter("openShortConstraint");
                        oldDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                    }
                } else if (tipo.equals("openLong")) {
                    oldDomanda.setTipo("openLong");
                    if(request.getParameter("openLongConstraint")!=null && !request.getParameter("openLongConstraint").isEmpty()){
                        String vincolo = "Constraint: " + request.getParameter("openLongConstraint");
                        oldDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                    }
                } else if (tipo.equals("openNumber")) {
                    oldDomanda.setTipo("openNumber");
                    if(request.getParameter("openNumberConstraintMin")!=null && !request.getParameter("openNumberConstraintMin").isEmpty()){
                        if(request.getParameter("openNumberConstraintMax") != null && !request.getParameter("openNumberConstraintMax").isEmpty()){
                            String vincolo = "Constraint: " + request.getParameter("openNumberConstraintMin") + " -- " + request.getParameter("openNumberConstraintMax");
                            oldDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                        } else {
                            String vincolo = "Constraint: " + request.getParameter("openNumberConstraintMin");
                            oldDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                        }
                    } else if (request.getParameter("openNumberConstraintMax") != null && !request.getParameter("openNumberConstraintMax").isEmpty()){
                        String vincolo = "Constraint: Null -- " + request.getParameter("openNumberConstraintMax");
                        oldDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                    }
                } else if (tipo.equals("openDate")) {
                    oldDomanda.setTipo("openDate");
                    if(request.getParameter("openDateConstraintMin")!=null && !request.getParameter("openDateConstraintMin").isEmpty()){
                        if(request.getParameter("openDateConstraintMax") != null && !request.getParameter("openDateConstraintMax").isEmpty()){
                            String strMinDate = request.getParameter("openDateConstraintMin");
                            String strMaxDate = request.getParameter("openDateConstraintMax");
                            String vincolo = "Constraint: " +strMinDate + " -- " + strMaxDate;
                            oldDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                        } else { 
                            String strMinDate = request.getParameter("openDateConstraintMin");
                            String vincolo = "Constraint: " +strMinDate;
                            oldDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                        }
                    } else if (request.getParameter("openDateConstraintMax") != null && !request.getParameter("openDateConstraintMax").isEmpty()){
                        String strMaxDate = request.getParameter("openDateConstraintMax");
                        String vincolo = "Constraint: Null -- " +strMaxDate;
                        oldDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                    }
                } else if (tipo.equals("closeSingle")) {
                    oldDomanda.setTipo("closeSingle");
                    JSONObject opzioni = new JSONObject();
                    ArrayList<String> listOpzioni = new ArrayList<String>();
                    if(request.getParameter("option1")!=null && !request.getParameter("option1").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option1")));
                    }
                    if(request.getParameter("option2")!=null && !request.getParameter("option2").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option2")));
                    }
                    if(request.getParameter("option3")!=null && !request.getParameter("option3").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option3")));
                    }
                    if(request.getParameter("option4")!=null && !request.getParameter("option4").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option4")));
                    }
                    if(request.getParameter("option5")!=null && !request.getParameter("option5").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option5")));
                    }
                    if(request.getParameter("option6")!=null && !request.getParameter("option6").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option6")));
                    }
                    if(request.getParameter("option7")!=null && !request.getParameter("option7").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option7")));
                    }
                    if(request.getParameter("option8")!=null && !request.getParameter("option8").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option8")));
                    }
                    if(request.getParameter("option9")!=null && !request.getParameter("option9").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option9")));
                    }
                    if(request.getParameter("option10")!=null && !request.getParameter("option10").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option10")));
                    }
                    if(request.getParameter("option11")!=null && !request.getParameter("option11").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option11")));
                    }
                    if(request.getParameter("option12")!=null && !request.getParameter("option12").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option12")));
                    }
                    opzioni.put("opzioni", new JSONArray(listOpzioni));
                    oldDomanda.setOpzioni(opzioni);
                } else if (tipo.equals("closeMultiple")) {
                    oldDomanda.setTipo("closeMultiple");
                    JSONObject opzioni = new JSONObject();
                    ArrayList<String> listOpzioni = new ArrayList<String>();
                    if(request.getParameter("option1m")!=null && !request.getParameter("option1m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option1m")));
                    }
                    if(request.getParameter("option2m")!=null && !request.getParameter("option2m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option2m")));
                    }
                    if(request.getParameter("option3m")!=null && !request.getParameter("option3m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option3m")));
                    }
                    if(request.getParameter("option4m")!=null && !request.getParameter("option4m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option4m")));
                    }
                    if(request.getParameter("option5m")!=null && !request.getParameter("option5m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option5m")));
                    }
                    if(request.getParameter("option6m")!=null && !request.getParameter("option6m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option6m")));
                    }
                    if(request.getParameter("option7m")!=null && !request.getParameter("option7m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option7m")));
                    }
                    if(request.getParameter("option8m")!=null && !request.getParameter("option8m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option8m")));
                    }
                    if(request.getParameter("option9m")!=null && !request.getParameter("option9m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option9m")));
                    }
                    if(request.getParameter("option10m")!=null  && !request.getParameter("option10m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option10m")));
                    }
                    if(request.getParameter("option11m")!=null && !request.getParameter("option11m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option11m")));
                    }
                    if(request.getParameter("option12m")!=null && !request.getParameter("option12m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option12m")));
                    }
                    opzioni.put("opzioni", new JSONArray(listOpzioni));
                    oldDomanda.setOpzioni(opzioni);
                }
                
                Domanda domandaE = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), (int)s.getAttribute("domanda-in-creazione"));
                
                oldDomanda.setPosizione((int)s.getAttribute("domanda-in-creazione"));
                if(s.getAttribute("updateDomanda")!=null){
                    Domanda domandaToUpdate = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), (int)s.getAttribute("updateDomanda"));
                    oldDomanda.setKey(domandaToUpdate.getKey());
                    oldDomanda.setVersion(domandaToUpdate.getVersion());
                    //esiste ancora una next (abbiamo fatto n prev ed n - x next (con x < n)
                    s.setAttribute("updateDomanda", domanda.getPosizione());
                }
                
                if(domandaE!=null){
                    oldDomanda.setKey(domandaE.getKey());
                    oldDomanda.setVersion(domandaE.getVersion());
                }
                        
                dl.getDomandaDAO().storeDomanda(oldDomanda);
                
                
                //nextDomanda
                
                if(domanda.getTitolo()!=null && !domanda.getTitolo().isEmpty()){
                    request.setAttribute("titleQuestion", SecurityLayer.stripSlashes((String)domanda.getTitolo()));
                }
                if(domanda.getDescrizione()!=null && !domanda.getDescrizione().isEmpty()){
                    request.setAttribute("description", SecurityLayer.stripSlashes((String)domanda.getDescrizione()));
                }
                if(domanda.isObbligatoria()){
                    request.setAttribute("obbligatory", "yes");
                } else {
                    request.setAttribute("obbligatory", "no");
                }
                if(domanda.getTipo().equals("openShort")){
                    if(domanda.getVincolo()!=null && !domanda.getVincolo().isEmpty()){
                        System.out.println(domanda.getVincolo());
                        Pattern p = Pattern.compile("\\d+");
                        Matcher m = p.matcher(domanda.getVincolo());
                        if(m.find(0)){
                            String constraint = m.group(0);
                            System.out.println(constraint);
                            request.setAttribute("openShortConstraint", constraint);
                        }
                    }
                    request.setAttribute("checked", "openShort");
                } else if(domanda.getTipo().equals("openLong")){
                    if(domanda.getVincolo()!=null && !domanda.getVincolo().isEmpty()){
                        Pattern p = Pattern.compile("\\d+");
                        Matcher m = p.matcher(domanda.getVincolo());
                        if(m.find(0)){
                            String constraint = m.group(0);
                            System.out.println(constraint);
                            request.setAttribute("openLongConstraint", constraint);
                        }
                    }
                    request.setAttribute("checked", "openLong");
                } else if(domanda.getTipo().equals("openNumber")){
                    if(domanda.getVincolo()!=null && !domanda.getVincolo().isEmpty()){
                        Pattern p = Pattern.compile("\\d+");
                        Matcher m = p.matcher(domanda.getVincolo());
                        if(domanda.getVincolo().contains("Null --")){
                            int indexOf = domanda.getVincolo().indexOf("--");
                            String secondPart = domanda.getVincolo().substring(indexOf);
                            System.out.println(secondPart);
                            m = p.matcher(secondPart);
                            if(m.find(0)){
                                String constraintMax = m.group(0);
                                request.setAttribute("openNumberConstraintMax", constraintMax);
                            }
                        } else if (domanda.getVincolo().contains("--")){
                            if(m.find(0)){ 
                                String constraintMin = m.group(0);
                                int indexOf = domanda.getVincolo().indexOf("--");
                                String secondPart = domanda.getVincolo().substring(indexOf);
                                System.out.println(secondPart);
                                m = p.matcher(secondPart);
                                if(m.find(0)){
                                    String constraintMax = m.group(0);
                                    request.setAttribute("openNumberConstraintMin", constraintMin);
                                    request.setAttribute("openNumberConstraintMax", constraintMax);
                                }
                            }
                        } else {
                            if(m.find(0)){ 
                                String constraintMin = m.group(0);
                                request.setAttribute("openNumberConstraintMin", constraintMin);
                            }
                        }
                    }
                    request.setAttribute("checked", "openNumber"); 
                } else if(domanda.getTipo().equals("openDate")){
                    if(domanda.getVincolo()!=null && !domanda.getVincolo().isEmpty()){
                        Pattern p = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
                        Matcher m = p.matcher(domanda.getVincolo());
                        if(domanda.getVincolo().contains("Null --")){
                            int indexOf = domanda.getVincolo().indexOf("--");
                            String secondPart = domanda.getVincolo().substring(indexOf);
                            m = p.matcher(secondPart);
                            if(m.find(0)){
                                String constraintMax = m.group(0);
                                request.setAttribute("openDateConstraintMax", constraintMax);
                            }
                        } else if (domanda.getVincolo().contains("--")){
                            if(m.find(0)){ 
                                String constraintMin = m.group(0);
                                int indexOf = domanda.getVincolo().indexOf("--");
                                String secondPart = domanda.getVincolo().substring(indexOf);
                                m = p.matcher(secondPart);
                                if(m.find(0)){
                                    String constraintMax = m.group(0);
                                    request.setAttribute("openDateConstraintMin", constraintMin);
                                    request.setAttribute("openDateConstraintMax", constraintMax);
                                }
                            }
                        } else {
                            if(m.find(0)){ 
                                String constraintMin = m.group(0);
                                request.setAttribute("openDateConstraintMin", constraintMin);
                            }
                        }
                    }
                    request.setAttribute("checked", "openDate");
                } else if(domanda.getTipo().equals("closeSingle")){
                    request.setAttribute("checked", "closeSingle");
                    JSONArray opzioni = domanda.getOpzioni().getJSONArray("opzioni");
                    for(int i = 0; i < opzioni.length(); i++){
                        request.setAttribute("option"+ i, SecurityLayer.stripSlashes((String)opzioni.get(i)));
                    }
                } else if(domanda.getTipo().equals("closeMultiple")){
                    request.setAttribute("checked", "closeMultiple");
                    JSONArray opzioni = domanda.getOpzioni().getJSONArray("opzioni");
                    for(int i = 0; i < opzioni.length(); i++){
                        request.setAttribute("option"+ i +"m", SecurityLayer.stripSlashes((String)opzioni.get(i)));
                    }
                }
                
                s.setAttribute("domanda-in-creazione", domanda.getPosizione());
                
            } else { //caso in cui la prossima domanda non esiste, cioè ha cliccato "domanda successiva" per creare effettivamente la domanda successiva
                                
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
                newDomanda.setTitolo(SecurityLayer.addSlashes(title));
                newDomanda.setDescrizione(SecurityLayer.addSlashes(description));
                newDomanda.setObbligatoria(obbligatory);


                if(tipo.equals("openShort")){
                    newDomanda.setTipo("openShort");
                    if(request.getParameter("openShortConstraint")!=null && !request.getParameter("openShortConstraint").isEmpty()){
                        String vincolo = "Constraint: " + request.getParameter("openShortConstraint");
                        newDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                    }
                } else if (tipo.equals("openLong")) {
                    newDomanda.setTipo("openLong");
                    if(request.getParameter("openLongConstraint")!=null && !request.getParameter("openLongConstraint").isEmpty()){
                        String vincolo = "Constraint: " + request.getParameter("openLongConstraint");
                        newDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                    }
                } else if (tipo.equals("openNumber")) {
                    newDomanda.setTipo("openNumber");
                    if(request.getParameter("openNumberConstraintMin")!=null && !request.getParameter("openNumberConstraintMin").isEmpty()){
                        if(request.getParameter("openNumberConstraintMax") != null && !request.getParameter("openNumberConstraintMax").isEmpty()){
                            String vincolo = "Constraint: " + request.getParameter("openNumberConstraintMin") + " -- " + request.getParameter("openNumberConstraintMax");
                            newDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                        } else {
                            String vincolo = "Constraint: " + request.getParameter("openNumberConstraintMin");
                            newDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                        }
                    } else if (request.getParameter("openNumberConstraintMax") != null && !request.getParameter("openNumberConstraintMax").isEmpty()){
                        String vincolo = "Constraint: Null -- " + request.getParameter("openNumberConstraintMax");
                        newDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                    }
                } else if (tipo.equals("openDate")) {
                    newDomanda.setTipo("openDate");
                    if(request.getParameter("openDateConstraintMin")!=null && !request.getParameter("openDateConstraintMin").isEmpty()){
                        if(request.getParameter("openDateConstraintMax") != null && !request.getParameter("openDateConstraintMax").isEmpty()){
                            String strMinDate = request.getParameter("openDateConstraintMin");
                            String strMaxDate = request.getParameter("openDateConstraintMax");
                            String vincolo = "Constraint: " +strMinDate + " -- " + strMaxDate;
                            newDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                        } else { 
                            String strMinDate = request.getParameter("openDateConstraintMin");
                            String vincolo = "Constraint: " +strMinDate;
                            newDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                        }
                    } else if (request.getParameter("openDateConstraintMax") != null && !request.getParameter("openDateConstraintMax").isEmpty()){
                        String strMaxDate = request.getParameter("openDateConstraintMax");
                        String vincolo = "Constraint: Null -- " +strMaxDate;
                        newDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                    }
                } else if (tipo.equals("closeSingle")) {
                    newDomanda.setTipo("closeSingle");
                    JSONObject opzioni = new JSONObject();
                    ArrayList<String> listOpzioni = new ArrayList<String>();
                    if(request.getParameter("option1")!=null && !request.getParameter("option1").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option1")));
                    }
                    if(request.getParameter("option2")!=null && !request.getParameter("option2").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option2")));
                    }
                    if(request.getParameter("option3")!=null && !request.getParameter("option3").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option3")));
                    }
                    if(request.getParameter("option4")!=null && !request.getParameter("option4").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option4")));
                    }
                    if(request.getParameter("option5")!=null && !request.getParameter("option5").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option5")));
                    }
                    if(request.getParameter("option6")!=null && !request.getParameter("option6").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option6")));
                    }
                    if(request.getParameter("option7")!=null && !request.getParameter("option7").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option7")));
                    }
                    if(request.getParameter("option8")!=null && !request.getParameter("option8").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option8")));
                    }
                    if(request.getParameter("option9")!=null && !request.getParameter("option9").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option9")));
                    }
                    if(request.getParameter("option10")!=null && !request.getParameter("option10").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option10")));
                    }
                    if(request.getParameter("option11")!=null && !request.getParameter("option11").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option11")));
                    }
                    if(request.getParameter("option12")!=null && !request.getParameter("option12").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option12")));
                    }
                    opzioni.put("opzioni", new JSONArray(listOpzioni));
                    newDomanda.setOpzioni(opzioni);
                } else if (tipo.equals("closeMultiple")) {
                    newDomanda.setTipo("closeMultiple");
                    JSONObject opzioni = new JSONObject();
                    ArrayList<String> listOpzioni = new ArrayList<String>();
                    if(request.getParameter("option1m")!=null && !request.getParameter("option1m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option1m")));
                    }
                    if(request.getParameter("option2m")!=null && !request.getParameter("option2m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option2m")));
                    }
                    if(request.getParameter("option3m")!=null && !request.getParameter("option3m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option3m")));
                    }
                    if(request.getParameter("option4m")!=null && !request.getParameter("option4m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option4m")));
                    }
                    if(request.getParameter("option5m")!=null && !request.getParameter("option5m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option5m")));
                    }
                    if(request.getParameter("option6m")!=null && !request.getParameter("option6m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option6m")));
                    }
                    if(request.getParameter("option7m")!=null && !request.getParameter("option7m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option7m")));
                    }
                    if(request.getParameter("option8m")!=null && !request.getParameter("option8m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option8m")));
                    }
                    if(request.getParameter("option9m")!=null && !request.getParameter("option9m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option9m")));
                    }
                    if(request.getParameter("option10m")!=null  && !request.getParameter("option10m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option10m")));
                    }
                    if(request.getParameter("option11m")!=null && !request.getParameter("option11m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option11m")));
                    }
                    if(request.getParameter("option12m")!=null && !request.getParameter("option12m").isEmpty()){
                        listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option12m")));
                    }
                    opzioni.put("opzioni", new JSONArray(listOpzioni));
                    newDomanda.setOpzioni(opzioni);
                }
                          
                //caso in cui l'utente ha creato il sondaggio (non confermato o quel che vuoi, ma creato si) e ritorna qui da firstSection per modificare le domande
                //in questo caso, quando passerà per l'ultima domanda effettivamente creata, vedra che non esiste la prossima domanda, ma questo non vuol dire che che 
                //la domanda corrente và "creata", ma va solo aggiornato. Data la gestione fatta fin'ora con "update-domanda" nel sessionamento me la gestiro con un if
                
                Domanda domandaE = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), (int)s.getAttribute("domanda-in-creazione"));
                
                newDomanda.setPosizione((int)s.getAttribute("domanda-in-creazione"));
                if(s.getAttribute("updateDomanda")!=null){
                    Domanda domandaToUpdate = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), (int)s.getAttribute("updateDomanda"));
                    newDomanda.setKey(domandaToUpdate.getKey());
                    newDomanda.setVersion(domandaToUpdate.getVersion());
                    s.removeAttribute("updateDomanda");
                }
                if(domandaE!=null){
                    newDomanda.setKey(domandaE.getKey());
                    newDomanda.setVersion(domandaE.getVersion());
                }
                
                dl.getDomandaDAO().storeDomanda(newDomanda);
                s.setAttribute("domanda-in-creazione", (int)s.getAttribute("domanda-in-creazione") + 1);
                
                request.setAttribute("noError", "yes");
            }
            System.out.println("valore update:" + s.getAttribute("updateDomanda"));
            System.out.println("domanda corrente:" + s.getAttribute("domanda-in-creazione"));
            request.setAttribute("numeroDomanda", (int)s.getAttribute("domanda-in-creazione"));
            res.activate("MakerPoll/questionsMaker.ftl", request, response);
            return;
        } catch (TemplateManagerException ex) {
            Logger.getLogger(QuestionsMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
    private void action_remove_case(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
       try {
            TemplateResult res = new TemplateResult(getServletContext());
            PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
            HttpSession s = checkSession(request);
            Domanda domandaToRemove = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), (int)s.getAttribute("domanda-in-creazione"));
            if(domandaToRemove!=null){
                dl.getDomandaDAO().deleteDomanda(domandaToRemove.getKey());
            }
            else {
                action_warning(request, response);
            }
            int position = (int)s.getAttribute("domanda-in-creazione");
            position++;
            Domanda domandaSuccessiva;
            System.out.println("posizione vale: " + position);
            //caso in cui stiamo rimuovendo l'ultima domanda esistente (cioè domanda in posizione 0 con nessuna domanda davanti)
            if(position == 1){
                domandaSuccessiva = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), position); 
                if(domandaSuccessiva== null){
                    if(s.getAttribute("updateDomanda") !=null){
                        s.removeAttribute("updateDomanda");
                    }
                    action_first_case(request, response);
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
            
            domandaSuccessiva = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), (int)s.getAttribute("domanda-in-creazione"));
            if(domandaSuccessiva != null){
                if(domandaSuccessiva.getTitolo()!=null && !domandaSuccessiva.getTitolo().isEmpty()){
                    request.setAttribute("titleQuestion", SecurityLayer.stripSlashes((String)domandaSuccessiva.getTitolo()));
                }
                if(domandaSuccessiva.getDescrizione()!=null && !domandaSuccessiva.getDescrizione().isEmpty()){
                    request.setAttribute("description", SecurityLayer.stripSlashes((String)domandaSuccessiva.getDescrizione()));
                }
                if(domandaSuccessiva.isObbligatoria()){
                    request.setAttribute("obbligatory", "yes");
                } else {
                    request.setAttribute("obbligatory", "no");
                }
                if(domandaSuccessiva.getTipo().equals("openShort")){
                    if(domandaSuccessiva.getVincolo()!=null && !domandaSuccessiva.getVincolo().isEmpty()){
                        System.out.println(domandaSuccessiva.getVincolo());
                        Pattern p = Pattern.compile("\\d+");
                        Matcher m = p.matcher(domandaSuccessiva.getVincolo());
                        if(m.find(0)){
                            String constraint = m.group(0);
                            System.out.println(constraint);
                            request.setAttribute("openShortConstraint", constraint);
                        }
                    }
                    request.setAttribute("checked", "openShort");
                } else if(domandaSuccessiva.getTipo().equals("openLong")){
                    if(domandaSuccessiva.getVincolo()!=null && !domandaSuccessiva.getVincolo().isEmpty()){
                        Pattern p = Pattern.compile("\\d+");
                        Matcher m = p.matcher(domandaSuccessiva.getVincolo());
                        if(m.find(0)){
                            String constraint = m.group(0);
                            System.out.println(constraint);
                            request.setAttribute("openLongConstraint", constraint);
                        }
                    }
                    request.setAttribute("checked", "openLong");
                } else if(domandaSuccessiva.getTipo().equals("openNumber")){
                    if(domandaSuccessiva.getVincolo()!=null && !domandaSuccessiva.getVincolo().isEmpty()){
                        Pattern p = Pattern.compile("\\d+");
                        Matcher m = p.matcher(domandaSuccessiva.getVincolo());
                        if(domandaSuccessiva.getVincolo().contains("Null --")){
                            int indexOf = domandaSuccessiva.getVincolo().indexOf("--");
                            String secondPart = domandaSuccessiva.getVincolo().substring(indexOf);
                            System.out.println(secondPart);
                            m = p.matcher(secondPart);
                            if(m.find(0)){
                                String constraintMax = m.group(0);
                                request.setAttribute("openNumberConstraintMax", constraintMax);
                            }
                        } else if (domandaSuccessiva.getVincolo().contains("--")){
                            if(m.find(0)){ 
                                String constraintMin = m.group(0);
                                int indexOf = domandaSuccessiva.getVincolo().indexOf("--");
                                String secondPart = domandaSuccessiva.getVincolo().substring(indexOf);
                                System.out.println(secondPart);
                                m = p.matcher(secondPart);
                                if(m.find(0)){
                                    String constraintMax = m.group(0);
                                    request.setAttribute("openNumberConstraintMin", constraintMin);
                                    request.setAttribute("openNumberConstraintMax", constraintMax);
                                }
                            }
                        } else {
                            if(m.find(0)){ 
                                String constraintMin = m.group(0);
                                request.setAttribute("openNumberConstraintMin", constraintMin);
                            }
                        }
                    }
                    request.setAttribute("checked", "openNumber"); 
                } else if(domandaSuccessiva.getTipo().equals("openDate")){
                    if(domandaSuccessiva.getVincolo()!=null && !domandaSuccessiva.getVincolo().isEmpty()){
                        Pattern p = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
                        Matcher m = p.matcher(domandaSuccessiva.getVincolo());
                        if(domandaSuccessiva.getVincolo().contains("Null --")){
                            int indexOf = domandaSuccessiva.getVincolo().indexOf("--");
                            String secondPart = domandaSuccessiva.getVincolo().substring(indexOf);
                            m = p.matcher(secondPart);
                            if(m.find(0)){
                                String constraintMax = m.group(0);
                                request.setAttribute("openDateConstraintMax", constraintMax);
                            }
                        } else if (domandaSuccessiva.getVincolo().contains("--")){
                            if(m.find(0)){ 
                                String constraintMin = m.group(0);
                                int indexOf = domandaSuccessiva.getVincolo().indexOf("--");
                                String secondPart = domandaSuccessiva.getVincolo().substring(indexOf);
                                m = p.matcher(secondPart);
                                if(m.find(0)){
                                    String constraintMax = m.group(0);
                                    request.setAttribute("openDateConstraintMin", constraintMin);
                                    request.setAttribute("openDateConstraintMax", constraintMax);
                                }
                            }
                        } else {
                            if(m.find(0)){ 
                                String constraintMin = m.group(0);
                                request.setAttribute("openDateConstraintMin", constraintMin);
                            }
                        }
                    }
                    request.setAttribute("checked", "openDate");
                } else if(domandaSuccessiva.getTipo().equals("closeSingle")){
                    request.setAttribute("checked", "closeSingle");
                    JSONArray opzioni = domandaSuccessiva.getOpzioni().getJSONArray("opzioni");
                    for(int i = 0; i < opzioni.length(); i++){
                        request.setAttribute("option"+ i, SecurityLayer.stripSlashes((String)opzioni.get(i)));
                    }
                } else if(domandaSuccessiva.getTipo().equals("closeMultiple")){
                    request.setAttribute("checked", "closeMultiple");
                    JSONArray opzioni = domandaSuccessiva.getOpzioni().getJSONArray("opzioni");
                    for(int i = 0; i < opzioni.length(); i++){
                        request.setAttribute("option"+ i +"m", SecurityLayer.stripSlashes((String)opzioni.get(i)));
                    }
                }
            } else {
                request.setAttribute("fromRemove", "yes");
                action_prev_case(request, response);
                return;
            }
            
            request.setAttribute("numeroDomanda", (int)s.getAttribute("domanda-in-creazione"));
            if((int)s.getAttribute("domanda-in-creazione") == 0){
                request.setAttribute("noPrev", "yes");
            }
            res.activate("MakerPoll/questionsMaker.ftl", request, response);
            return;
        } catch (TemplateManagerException ex) {
            Logger.getLogger(QuestionsMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void action_confirm_redirect(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException, TemplateManagerException, DataException{
        //prima di andare avanti salviamo l'ultima domanda (quella da cui ha cliccato "conferma").
        //nota, in questo caso, assumiamo che se la domanda è vuota allora non la vuole caricare, se invece anche un solo campo tra "titolo", "descrizione" e "obbligatoria" è riempito/checkato
        //allora assumiamo che la domanda vuole essere caricata
        PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
        HttpSession s = checkSession(request);
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
            newDomanda.setTitolo(SecurityLayer.addSlashes(title));
            newDomanda.setDescrizione(SecurityLayer.addSlashes(description));
            newDomanda.setObbligatoria(obbligatory);


            if(tipo.equals("openShort")){
                newDomanda.setTipo("openShort");
                if(request.getParameter("openShortConstraint")!=null && !request.getParameter("openShortConstraint").isEmpty()){
                    String vincolo = "Constraint: " + request.getParameter("openShortConstraint");
                    newDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                }
            } else if (tipo.equals("openLong")) {
                newDomanda.setTipo("openLong");
                if(request.getParameter("openLongConstraint")!=null && !request.getParameter("openLongConstraint").isEmpty()){
                    String vincolo = "Constraint: " + request.getParameter("openLongConstraint");
                    newDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                }
            } else if (tipo.equals("openNumber")) {
                newDomanda.setTipo("openNumber");
                if(request.getParameter("openNumberConstraintMin")!=null && !request.getParameter("openNumberConstraintMin").isEmpty()){
                    if(request.getParameter("openNumberConstraintMax") != null && !request.getParameter("openNumberConstraintMax").isEmpty()){
                        String vincolo = "Constraint: " + request.getParameter("openNumberConstraintMin") + " -- " + request.getParameter("openNumberConstraintMax");
                        newDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                    } else {
                        String vincolo = "Constraint: " + request.getParameter("openNumberConstraintMin");
                        newDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                    }
                } else if (request.getParameter("openNumberConstraintMax") != null && !request.getParameter("openNumberConstraintMax").isEmpty()){
                    String vincolo = "Constraint: Null -- " + request.getParameter("openNumberConstraintMax");
                    newDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                }
            } else if (tipo.equals("openDate")) {
                newDomanda.setTipo("openDate");
                if(request.getParameter("openDateConstraintMin")!=null && !request.getParameter("openDateConstraintMin").isEmpty()){
                    if(request.getParameter("openDateConstraintMax") != null && !request.getParameter("openDateConstraintMax").isEmpty()){
                        String strMinDate = request.getParameter("openDateConstraintMin");
                        String strMaxDate = request.getParameter("openDateConstraintMax");
                        String vincolo = "Constraint: " +strMinDate + " -- " + strMaxDate;
                        newDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                    } else { 
                        String strMinDate = request.getParameter("openDateConstraintMin");
                        String vincolo = "Constraint: " +strMinDate;
                        newDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                    }
                } else if (request.getParameter("openDateConstraintMax") != null && !request.getParameter("openDateConstraintMax").isEmpty()){
                    String strMaxDate = request.getParameter("openDateConstraintMax");
                    String vincolo = "Constraint: null -- " +strMaxDate;
                    newDomanda.setVincolo(SecurityLayer.addSlashes(vincolo));
                }
            } else if (tipo.equals("closeSingle")) {
                newDomanda.setTipo("closeSingle");
                JSONObject opzioni = new JSONObject();
                ArrayList<String> listOpzioni = new ArrayList<String>();
                if(request.getParameter("option1")!=null && !request.getParameter("option1").isEmpty()){
                    listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option1")));
                }
                if(request.getParameter("option2")!=null && !request.getParameter("option2").isEmpty()){
                    listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option2")));
                }
                if(request.getParameter("option3")!=null && !request.getParameter("option3").isEmpty()){
                    listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option3")));
                }
                if(request.getParameter("option4")!=null && !request.getParameter("option4").isEmpty()){
                    listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option4")));
                }
                if(request.getParameter("option5")!=null && !request.getParameter("option5").isEmpty()){
                    listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option5")));
                }
                if(request.getParameter("option6")!=null && !request.getParameter("option6").isEmpty()){
                    listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option6")));
                }
                if(request.getParameter("option7")!=null && !request.getParameter("option7").isEmpty()){
                    listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option7")));
                }
                if(request.getParameter("option8")!=null && !request.getParameter("option8").isEmpty()){
                    listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option8")));
                }
                if(request.getParameter("option9")!=null && !request.getParameter("option9").isEmpty()){
                    listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option9")));
                }
                if(request.getParameter("option10")!=null && !request.getParameter("option10").isEmpty()){
                    listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option10")));
                }
                if(request.getParameter("option11")!=null && !request.getParameter("option11").isEmpty()){
                    listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option11")));
                }
                if(request.getParameter("option12")!=null && !request.getParameter("option12").isEmpty()){
                    listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option12")));
                }
                opzioni.put("opzioni", new JSONArray(listOpzioni));
                newDomanda.setOpzioni(opzioni);
            } else if (tipo.equals("closeMultiple")) {
                newDomanda.setTipo("closeMultiple");
                JSONObject opzioni = new JSONObject();
                ArrayList<String> listOpzioni = new ArrayList<String>();
                if(request.getParameter("option1m")!=null && !request.getParameter("option1m").isEmpty()){
                    listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option1m")));
                }
                if(request.getParameter("option2m")!=null && !request.getParameter("option2m").isEmpty()){
                    listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option2m")));
                }
                if(request.getParameter("option3m")!=null && !request.getParameter("option3m").isEmpty()){
                    listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option3m")));
                }
                if(request.getParameter("option4m")!=null && !request.getParameter("option4m").isEmpty()){
                    listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option4m")));
                }
                if(request.getParameter("option5m")!=null && !request.getParameter("option5m").isEmpty()){
                    listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option5m")));
                }
                if(request.getParameter("option6m")!=null && !request.getParameter("option6m").isEmpty()){
                    listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option6m")));
                }
                if(request.getParameter("option7m")!=null && !request.getParameter("option7m").isEmpty()){
                    listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option7m")));
                }
                if(request.getParameter("option8m")!=null && !request.getParameter("option8m").isEmpty()){
                    listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option8m")));
                }
                if(request.getParameter("option9m")!=null && !request.getParameter("option9m").isEmpty()){
                    listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option9m")));
                }
                if(request.getParameter("option10m")!=null  && !request.getParameter("option10m").isEmpty()){
                    listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option10m")));
                }
                if(request.getParameter("option11m")!=null && !request.getParameter("option11m").isEmpty()){
                    listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option11m")));
                }
                if(request.getParameter("option12m")!=null && !request.getParameter("option12m").isEmpty()){
                    listOpzioni.add(SecurityLayer.addSlashes(request.getParameter("option12m")));
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
                Domanda domandaToUpdate = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), (int)s.getAttribute("updateDomanda"));
                newDomanda.setKey(domandaToUpdate.getKey());
                newDomanda.setVersion(domandaToUpdate.getVersion());
                s.removeAttribute("updateDomanda");
            }
            if(domandaE!=null){
                newDomanda.setKey(domandaE.getKey());
                newDomanda.setVersion(domandaE.getVersion());
            }
            
            dl.getDomandaDAO().storeDomanda(newDomanda);
        }
        response.sendRedirect("confirmSection");
        return;
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
            request.setAttribute("urlMakerPoll", "yes");
            RequestDispatcher rd = request.getRequestDispatcher("/login");
            rd.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

}