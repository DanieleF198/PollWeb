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
import com.mycompany.pollweb.impl.UtenteImpl;
import com.mycompany.pollweb.model.Domanda;
import com.mycompany.pollweb.model.Sondaggio;
import com.mycompany.pollweb.model.Utente;
import com.mycompany.pollweb.result.FailureResult;
import com.mycompany.pollweb.security.SecurityLayer;
import static com.mycompany.pollweb.security.SecurityLayer.checkSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
            if(s!= null){
                if(s.getAttribute("sondaggio-in-conferma").equals("no")){
                    s.setAttribute("domanda-in-creazione", 0); //resetto domanda in creazione, in caso torni indietro lo porterò alla warning
                    s.setAttribute("sondaggio-in-conferma", "yes");
                    action_default(request, response);
                }
                else if("POST".equals(request.getMethod()) && request.getParameter("removeQuestion") != null){
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
            } else{
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
            Sondaggio sondaggio = dl.getSondaggioDAO().getSondaggio((int)s.getAttribute("sondaggio-in-creazione"));
            Collections.sort(domande);
            ArrayList<String> errors = new ArrayList<String>();
            String err1 = "non esistono domande";
            String err2 = "non tutte le domande hanno il titolo";
            String err3 = "Una tua domanda chiusa ha meno di due opzioni";
            String err4 = "Un qualche vincolo impostato non è valido (controlla domande aperte con risposta breve o lunga)";
            if(domande.isEmpty()){
                errors.add(err1);
            } else {
                for (int i = 0; i < domande.size(); i++){
                    Domanda d = domande.get(i);
                    if(d.getTitolo().isBlank()){
                        if(!errors.contains(err2)){
                            errors.add(err2);
                        }
                    }
                    if (d.getTipo().equals("closeSingle") || d.getTipo().equals("closeMultiple")){
                        JSONArray opzioni = d.getOpzioni().getJSONArray("opzioni");
                        if(opzioni.length()<2){
                            if(!errors.contains(err3)){
                                errors.add(err3);
                            }
                        }
                    }
                    if (d.getTipo().equals("openShort")){
                        if(d.getVincolo()!=null && !d.getVincolo().isBlank()){
                            Pattern p = Pattern.compile("\\d+");
                            Matcher m = p.matcher(d.getVincolo());
                            if(m.find(0)){
                                String constraint = m.group(0);
                                int value = Integer.parseInt(constraint);
                                if(value > 64){
                                    if(!errors.contains(err4)){
                                        errors.add(err4);
                                    }
                                }
                            }
                        }
                    }
                    if (d.getTipo().equals("openLong")){
                        if(d.getVincolo()!=null && !d.getVincolo().isBlank()){
                            Pattern p = Pattern.compile("\\d+");
                            Matcher m = p.matcher(d.getVincolo());
                            if(m.find(0)){
                                String constraint = m.group(0);
                                int value = Integer.parseInt(constraint);
                                if(value > 256){
                                    if(!errors.contains(err4)){
                                        errors.add(err4);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(!errors.isEmpty()){
                request.setAttribute("errors", errors);
                if(sondaggio.isVisibilita()){
                    sondaggio.setVisibilita(false);
                }
                dl.getSondaggioDAO().storeSondaggio(sondaggio);
            }
            if(sondaggio.isPrivato()){
                request.setAttribute("private", "yes");
            }
            request.setAttribute("domande", domande);
            if(request.getParameter("withCSV") != null && request.getParameter("withCSV").equals("withCSV")){
                System.out.println("TODO");
            } else {
                if(request.getParameter("usersName[]")!=null || request.getParameter("usersMail[]")!=null || request.getParameter("usersPass[]")!=null){
                    String[] name = request.getParameterValues("usersName[]");
                    String[] mail = request.getParameterValues("usersMail[]");
                    String[] pass = request.getParameterValues("usersPass[]");
                    int maxIndex; 
                    maxIndex = Integer.max(name.length, mail.length);
                    maxIndex = Integer.max(maxIndex, pass.length);
                    ArrayList<Utente> partecipants = new ArrayList<Utente>();
                    for(int i = 0; i < maxIndex; i++){
                        System.out.println("primo for, ciclo " + i);
                        Utente u = new UtenteImpl();
                        if(name.length > i){
                            u.setNome(name[i]);
                        } else {
                            u.setNome("");
                        }
                        if(mail.length > i){
                            u.setEmail(mail[i]);
                        } else {
                            u.setEmail("");
                        }
                        if(pass.length > i){
                            u.setPassword(pass[i]);
                        } else {
                            u.setPassword("");
                        }
                        partecipants.add(u);
                    }
                    if(!partecipants.isEmpty()){
                        ArrayList<Utente> tempPartecipants = new ArrayList<Utente>();
                        System.out.println("numero inserimenti" + partecipants.size());
                        for(int k = partecipants.size()-1; k >= 0 ; k--){
                            System.out.println("K = " + k);
                            System.out.println("Nome utente = " + partecipants.get(k).getNome());
                            if (partecipants.get(k).getNome().isBlank() && partecipants.get(k).getEmail().isBlank() && partecipants.get(k).getPassword().isBlank()){
                                System.out.println("sono passato di qui");
                                partecipants.remove(k); 
                            }
                        }
                        for(int k = 0; k < partecipants.size(); k++){
                            tempPartecipants.add(partecipants.get(k));
                        }
                        for(int j = 0; j < partecipants.size(); j++){
                            System.out.println("secondo for, ciclo " + j);
                            Utente checkU = partecipants.get(j);
                            if(checkU.getNome().isBlank() || checkU.getEmail().isBlank() || checkU.getPassword().isBlank()){
                                System.out.println("sono passato di qui");
                                request.setAttribute("partecipantsError", "yes");
                            }
                            String passwordOfU = checkU.getPassword();
                            Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                            Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
                            Pattern lowerCasePatten = Pattern.compile("[a-z ]");
                            Pattern digitCasePatten = Pattern.compile("[0-9 ]");
                            Pattern emailPattern = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
                            if(passwordOfU.length()<8 || !UpperCasePatten.matcher(passwordOfU).find() || !lowerCasePatten.matcher(passwordOfU).find() || !digitCasePatten.matcher(passwordOfU).find() || !specailCharPatten.matcher(passwordOfU).find()){
                                System.out.println("errore password");
                                request.setAttribute("partecipantsError", "yes");
                            }
                            if (!emailPattern.matcher(checkU.getEmail()).find()) {
                                System.out.println("errore mail");
                                request.setAttribute("partecipantsError", "yes");
                            }
                            tempPartecipants.remove(0);
                            for(int k = 0; k < tempPartecipants.size(); k++){
                                System.out.println("terzo ciclo for, ciclo " + k);
                                Utente tempUtente = tempPartecipants.get(k);
                                if(tempUtente.getEmail().equals(checkU.getEmail()) || tempUtente.getPassword().equals(checkU.getPassword())){
                                    request.setAttribute("partecipantsError", "yes");
                                }
                            }
                        }
                        request.setAttribute("partecipants", partecipants);
                    }
                }
            }
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