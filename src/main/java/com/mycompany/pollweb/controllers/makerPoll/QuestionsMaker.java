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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                System.out.println(s.getAttribute("sondaggio-in-conferma"));
                System.out.println("nyah" + request.getParameter("returnQuestions"));
                if((int)s.getAttribute("sondaggio-in-creazione") != 0 && (s.getAttribute("fromFirst") != null) && (s.getAttribute("sondaggio-in-conferma").equals("no"))){ //è arrivato da firstSection cliccando su bottone
                    System.out.println("caso 1");
                    s.removeAttribute("fromFirst");
                    action_first_case(request, response);
                    return;
                } else if(request.getParameter("returnQuestions") != null){ //è arrivato da confirmSection cliccando su bottone
                    System.out.println("caso 2");
                    s.setAttribute("sondaggio-in-conferma", "no");
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
                    request.setAttribute("titleQuestion", domanda.getTitolo());
                }
                if(domanda.getDescrizione()!=null && !domanda.getDescrizione().isEmpty()){
                    request.setAttribute("description", domanda.getDescrizione());
                }
                if(domanda.isObbligatoria()){
                    request.setAttribute("obbligatory", "yes");
                } else {
                    request.setAttribute("obbligatory", "no");
                }
                if(domanda.getTipo().equals("openShort")){
                    request.setAttribute("checked", "openShort");
                } else if(domanda.getTipo().equals("openLong")){
                    request.setAttribute("checked", "openLong");
                } else if(domanda.getTipo().equals("openNumber")){
                    request.setAttribute("checked", "openNumber");
                } else if(domanda.getTipo().equals("openDate")){
                    request.setAttribute("checked", "openDate");
                } else if(domanda.getTipo().equals("closeSingle")){
                    request.setAttribute("checked", "closeSingle");
                    JSONArray opzioni = domanda.getOpzioni().getJSONArray("opzioni");
                    for(int i = 0; i < opzioni.length(); i++){
                        request.setAttribute("option"+ i, opzioni.get(i));
                    }
                } else if(domanda.getTipo().equals("closeMultiple")){
                    request.setAttribute("checked", "closeMultiple");
                    JSONArray opzioni = domanda.getOpzioni().getJSONArray("opzioni");
                    for(int i = 0; i < opzioni.length(); i++){
                        request.setAttribute("option"+ i, opzioni.get(i));
                    }
                }
                request.setAttribute("noPrev", "yes");
                
            } else {
                
                request.setAttribute("noError", "yes");
                request.setAttribute("noPrev", "yes");
                request.setAttribute("noConf", "yes");
                
            }
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
            PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
            int position = (int)s.getAttribute("domanda-in-creazione") - 1;
            Domanda domanda = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), position);
            
            //prima di occuparci di prenderci i dati di prevDomanda carichiamo eventuali dati di currentDomanda
            //ovviamente dobbiamo riconoscere il caso in cui current domanda è effettivamente esistente(cioè se è la prima volta che stiamo cliccando "prev" o meno)
            
            if(s.getAttribute("updateDomanda")!=null){
            
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
                currentDomanda.setTitolo(title);
                currentDomanda.setDescrizione(description);
                currentDomanda.setObbligatoria(obbligatory);


                if(tipo.equals("openShort")){
                    currentDomanda.setTipo("openShort");
                } else if (tipo.equals("openLong")) {
                    currentDomanda.setTipo("openLong");
                } else if (tipo.equals("openNumber")) {
                    currentDomanda.setTipo("openNumber");
                } else if (tipo.equals("openDate")) {
                    currentDomanda.setTipo("openDate");
                } else if (tipo.equals("closeSingle")) {
                    currentDomanda.setTipo("closeSingle");
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
                    currentDomanda.setOpzioni(opzioni);
                } else if (tipo.equals("closeMultiple")) {
                    currentDomanda.setTipo("closeMultiple");
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
                    currentDomanda.setOpzioni(opzioni);
                }

                currentDomanda.setPosizione((int)s.getAttribute("domanda-in-creazione"));
                if(s.getAttribute("updateDomanda")!=null){
                    currentDomanda.setKey((int)s.getAttribute("updateDomanda"));
                }
                dl.getDomandaDAO().storeDomanda(currentDomanda);
            }
            
            //ancora prima di prev domanda dobbiamo assicurarci che non sia il caso in cui a)sta cliccando per la prima volta "prevDomanda", b)ha scritto qualcosa nel prev
            //cioè una sorta di next nel caso base al contrario
            
            if(s.getAttribute("updateDomanda")==null && (request.getParameter("questionTitle") != null || request.getParameter("questionDescription") != null || request.getParameter("questionDescription") != null)){
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
                newDomanda.setTitolo(title);
                newDomanda.setDescrizione(description);
                newDomanda.setObbligatoria(obbligatory);


                if(tipo.equals("openShort")){
                    newDomanda.setTipo("openShort");
                } else if (tipo.equals("openLong")) {
                    newDomanda.setTipo("openLong");
                } else if (tipo.equals("openNumber")) {
                    newDomanda.setTipo("openNumber");
                } else if (tipo.equals("openDate")) {
                    newDomanda.setTipo("openDate");
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

                newDomanda.setPosizione((int)s.getAttribute("domanda-in-creazione"));
                //non ci poniamo il problema dell'update perché in questo caso siamo sicuri che la domanda ancora non esiste
                dl.getDomandaDAO().storeDomanda(newDomanda); 
            }
            
            //prevDomanda
            
            if(domanda.getPosizione() == 0){
                request.setAttribute("noPrev", "yes");
            }
            if(domanda.getTitolo()!=null && !domanda.getTitolo().isEmpty()){
                request.setAttribute("titleQuestion", domanda.getTitolo());
            }
            if(domanda.getDescrizione()!=null && !domanda.getDescrizione().isEmpty()){
                request.setAttribute("description", domanda.getDescrizione());
            }
            if(domanda.isObbligatoria()){
                request.setAttribute("obbligatory", "yes");
            } else {
                request.setAttribute("obbligatory", "no");
            }
            if(domanda.getTipo().equals("openShort")){
                request.setAttribute("checked", "openShort");
            } else if(domanda.getTipo().equals("openLong")){
                request.setAttribute("checked", "openLong");
            } else if(domanda.getTipo().equals("openNumber")){
                request.setAttribute("checked", "openNumber");
            } else if(domanda.getTipo().equals("openDate")){
                request.setAttribute("checked", "openDate");
            } else if(domanda.getTipo().equals("closeSingle")){
                request.setAttribute("checked", "closeSingle");
                JSONArray opzioni = domanda.getOpzioni().getJSONArray("opzioni");
                for(int i = 0; i < opzioni.length(); i++){
                    request.setAttribute("option"+ i, opzioni.get(i));
                }
            } else if(domanda.getTipo().equals("closeMultiple")){
                request.setAttribute("checked", "closeMultiple");
                JSONArray opzioni = domanda.getOpzioni().getJSONArray("opzioni");
                for(int i = 0; i < opzioni.length(); i++){
                    request.setAttribute("option"+ i, opzioni.get(i));
                }
            }
            s.setAttribute("domanda-in-creazione", domanda.getPosizione());
            System.out.println("domanda id bello: " + domanda.getKey());
            s.setAttribute("updateDomanda", domanda.getKey());
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
            System.out.println("Domanda in creazione:" + s.getAttribute("domanda-in-creazione") );
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
                oldDomanda.setTitolo(title);
                oldDomanda.setDescrizione(description);
                oldDomanda.setObbligatoria(obbligatory);


                if(tipo.equals("openShort")){
                    oldDomanda.setTipo("openShort");
                } else if (tipo.equals("openLong")) {
                    oldDomanda.setTipo("openLong");
                } else if (tipo.equals("openNumber")) {
                    oldDomanda.setTipo("openNumber");
                } else if (tipo.equals("openDate")) {
                    oldDomanda.setTipo("openDate");
                } else if (tipo.equals("closeSingle")) {
                    oldDomanda.setTipo("closeSingle");
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
                    oldDomanda.setOpzioni(opzioni);
                } else if (tipo.equals("closeMultiple")) {
                    oldDomanda.setTipo("closeMultiple");
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
                    oldDomanda.setOpzioni(opzioni);
                }
                
                Domanda domandaE = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), (int)s.getAttribute("domanda-in-creazione"));
                
                oldDomanda.setPosizione((int)s.getAttribute("domanda-in-creazione"));
                if(s.getAttribute("updateDOmanda")!=null){
                    oldDomanda.setKey((int)s.getAttribute("updateDomanda"));
                    if(dl.getDomandaDAO().checkDomanda((int)s.getAttribute("updateDomanda") + 1)){ //esiste ancora una next (abbiamo fatto n prev ed n - x next (con x < n)
                        s.setAttribute("updateDomanda", oldDomanda.getKey() + 1);
                    } else {
                        s.removeAttribute("updateDomanda"); //non esiste ancora una next, cioè questa è l'ultima domanda creata (in teoria qui non dovrei mai finire, se ne occupa il caso "newDomanda", ma un controllo in più non fa mai male)
                    }
                }
                
                if(domandaE!=null){
                    oldDomanda.setKey(domandaE.getKey());
                }
                        
                dl.getDomandaDAO().storeDomanda(oldDomanda);
                
                //nextDomanda
                
                if(domanda.getTitolo()!=null && !domanda.getTitolo().isEmpty()){
                    request.setAttribute("titleQuestion", domanda.getTitolo());
                }
                if(domanda.getDescrizione()!=null && !domanda.getDescrizione().isEmpty()){
                    request.setAttribute("description", domanda.getDescrizione());
                }
                if(domanda.isObbligatoria()){
                    request.setAttribute("obbligatory", "yes");
                } else {
                    request.setAttribute("obbligatory", "no");
                }
                if(domanda.getTipo().equals("openShort")){
                    request.setAttribute("checked", "openShort");
                } else if(domanda.getTipo().equals("openLong")){
                    request.setAttribute("checked", "openLong");
                } else if(domanda.getTipo().equals("openNumber")){
                    request.setAttribute("checked", "openNumber");
                } else if(domanda.getTipo().equals("openDate")){
                    request.setAttribute("checked", "openDate");
                } else if(domanda.getTipo().equals("closeSingle")){
                    request.setAttribute("checked", "closeSingle");
                    JSONArray opzioni = domanda.getOpzioni().getJSONArray("opzioni");
                    for(int i = 0; i < opzioni.length(); i++){
                        request.setAttribute("option"+ i, opzioni.get(i));
                    }
                } else if(domanda.getTipo().equals("closeMultiple")){
                    request.setAttribute("checked", "closeMultiple");
                    JSONArray opzioni = domanda.getOpzioni().getJSONArray("opzioni");
                    for(int i = 0; i < opzioni.length(); i++){
                        request.setAttribute("option"+ i +"m", opzioni.get(i));
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
                newDomanda.setTitolo(title);
                newDomanda.setDescrizione(description);
                newDomanda.setObbligatoria(obbligatory);


                if(tipo.equals("openShort")){
                    newDomanda.setTipo("openShort");
                } else if (tipo.equals("openLong")) {
                    newDomanda.setTipo("openLong");
                } else if (tipo.equals("openNumber")) {
                    newDomanda.setTipo("openNumber");
                } else if (tipo.equals("openDate")) {
                    newDomanda.setTipo("openDate");
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
                s.setAttribute("domanda-in-creazione", (int)s.getAttribute("domanda-in-creazione") + 1);
            }
            System.out.println("domanda in creazione:" + s.getAttribute("domanda-in-creazione"));
            res.activate("MakerPoll/questionsMaker.ftl", request, response);
            return;
        } catch (TemplateManagerException ex) {
            Logger.getLogger(QuestionsMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
    private void action_remove_case(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
       try {
            TemplateResult res = new TemplateResult(getServletContext());
//            PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
//            HttpSession s = checkSession(request);
//            Domanda domandaToRemove = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), (int)s.getAttribute("domanda-in-creazione"));
//            TODO
            res.activate("MakerPoll/questionsMaker.ftl", request, response);
            return;
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
