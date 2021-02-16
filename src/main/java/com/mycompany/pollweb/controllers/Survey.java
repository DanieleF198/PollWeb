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
import com.mycompany.pollweb.impl.RispostaImpl;
import com.mycompany.pollweb.model.Domanda;
import com.mycompany.pollweb.model.Risposta;
import com.mycompany.pollweb.model.RispostaDomanda;
import com.mycompany.pollweb.model.Sondaggio;
import com.mycompany.pollweb.result.FailureResult;
import com.mycompany.pollweb.security.SecurityLayer;
import static com.mycompany.pollweb.security.SecurityLayer.checkSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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

public class Survey extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, DataException{
         try {
            HttpSession s = checkSession(request);
            PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
            if(request.getParameter("btnCompile")!=null){
                Sondaggio sondaggio = dl.getSondaggioDAO().getSondaggio(Integer.parseInt(request.getParameter("btnCompile")));
                request.setAttribute("sondaggio", sondaggio);
                if(sondaggio.isPrivato()){
                    if (s!= null) {
                        //TODO
                    } else {
                        action_redirect_login(request, response);
                        return;
                    }
                } else {
                    action_sondaggio_pubblico(request, response);
                    return;
                }
                
                action_default(request, response);
            } else if(request.getParameter("returnSurvey")!=null){ //caso in cui torna per modificare le risposte
                action_load_survey(request,response);
            } else if(request.getParameter("sendAnswer")!=null || request.getParameter("ModAnswer")!=null){
                action_send_answer(request,response);
            } else {
                request.setAttribute("message", "sondaggio inesistente");
                action_error(request,response);
            }
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(Survey.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
       try {
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("surveyExample.ftl", request, response);
        
        } catch (TemplateManagerException ex) {
            Logger.getLogger(Survey.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void action_sondaggio_pubblico(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException {
       try {
        TemplateResult res = new TemplateResult(getServletContext());
        PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
        ArrayList<Domanda> domande = (ArrayList<Domanda>) dl.getDomandaDAO().getDomandaByIdSondaggio(Integer.parseInt(request.getParameter("btnCompile")));
        Sondaggio sondaggio = dl.getSondaggioDAO().getSondaggio(Integer.parseInt(request.getParameter("btnCompile")));
        request.setAttribute("domande", domande);
        Collections.sort(domande);
        //controllo se l'utente ha già risposto al sondaggio in passato
        ArrayList<Risposta> risposte;
        HttpSession s = checkSession(request);
        if(s!=null){
            risposte = (ArrayList<Risposta>) dl.getRispostaDAO().getRispostaByIdUtente((int) s.getAttribute("userid"));
        } else {
            risposte = (ArrayList<Risposta>) dl.getRispostaDAO().getRispostaByIPUtente(request.getRemoteHost());
        }
        Risposta risposta = new RispostaImpl();
        int idRispostaCorretta = -1;
        for(int i = 0; i<risposte.size();i++){
            risposta = risposte.get(i);
            for(int j = 0; j<domande.size(); j++){
                Domanda d = domande.get(i);
                JSONArray r = dl.getRispostaDomandaDAO().getRispostaDomanda(risposta.getKey(), d.getKey()).getRisposta().getJSONArray("risposta");
                if(r != null){
                    idRispostaCorretta = i;
                    break;
                }
            }
            if(idRispostaCorretta!=-1){
                break;
            }
        }
        if(idRispostaCorretta!=-1){
            risposta = risposte.get(idRispostaCorretta);
        } else {
            risposta = null;
        }
        HashMap<String, String> risposteNoMult = new HashMap<String, String>();
        HashMap<String, String> risposteRadios = new HashMap<String, String>();
        HashMap<String, HashMap<String, String>> risposteSiMult = new HashMap<String, HashMap<String, String>>();
        if(risposta != null){
            for(int i = 0; i<domande.size(); i++){
                Domanda d = domande.get(i);
                JSONArray r = dl.getRispostaDomandaDAO().getRispostaDomanda(risposta.getKey(), d.getKey()).getRisposta().getJSONArray("risposta");
                if(r != null){
                    if (d.getTipo().equals("closeMultiple")){
                        HashMap<String, String> temp = new HashMap<String, String>();
                        for(int j = 0; j<r.length(); j++){
                            temp.put(String.valueOf(j), r.get(j).toString());
                        }
                        risposteSiMult.put(String.valueOf(i), temp);
                    } else if(d.getTipo().equals("closeSingle")) {
                        risposteRadios.put(String.valueOf(i), r.get(0).toString());
                    } else {
                        risposteNoMult.put(String.valueOf(i), r.get(0).toString());
                    }
                } else {
                    if(d.isObbligatoria()){
                        request.setAttribute("message", "errore nel caricamento delle risposte");
                        action_error(request, response);
                    }
                }
            }
        }
        boolean atLeastOne = false;
        if(!risposteNoMult.isEmpty()){
            request.setAttribute("risposteNoMult", risposteNoMult);
            atLeastOne = true;
        }
        if(!risposteRadios.isEmpty()){
            request.setAttribute("risposteRadios", risposteRadios);
            atLeastOne = true;
        }
        if(!risposteSiMult.isEmpty()){
            request.setAttribute("risposteSiMult", risposteSiMult);
            atLeastOne = true;
        }
        request.setAttribute("sondaggio", sondaggio);
        
        if(!sondaggio.isModificabile() && atLeastOne){ //caso in cui il sondaggio non è modificabile e l'utente ha già risposto in passato
            request.setAttribute("AlreadyCompiled", "yes");
            RequestDispatcher rd = request.getRequestDispatcher("/surveyAnswered");
            rd.forward(request, response);
            return;
        }
        res.activate("survey.ftl", request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(Survey.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void action_send_answer(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException, ParseException {
       try {
        TemplateResult res = new TemplateResult(getServletContext());
        PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
        int idSondaggio = -1;
        if(request.getParameter("ModAnswer")!=null){
            idSondaggio = Integer.parseInt(request.getParameter("ModAnswer").substring(9));
        } else {
            idSondaggio = Integer.parseInt(request.getParameter("sendAnswer").substring(10));
        }
        Sondaggio sondaggio = dl.getSondaggioDAO().getSondaggio(idSondaggio);
        ArrayList<Domanda> domande = (ArrayList<Domanda>) dl.getDomandaDAO().getDomandaByIdSondaggio(idSondaggio);
        Collections.sort(domande);
        
        //hashmap dove key = posizione domanda e value= valore risposta.
        HashMap<Integer, String> openShortMap = new HashMap<Integer, String>();
        HashMap<Integer, String> openLongMap = new HashMap<Integer, String>();
        HashMap<Integer, String> openNumberMap = new HashMap<Integer, String>();
        HashMap<Integer, String> openDateMap = new HashMap<Integer, String>();
        HashMap<Integer, String> closeSingleMap = new HashMap<Integer, String>();
        //chiave = posizione domanda all'interno del sondaggio, valore = hashmap contenente i checkbox checkati con corrispettivo value
        HashMap<Integer, HashMap<Integer, String>> closeMultipleMap = new HashMap<Integer, HashMap<Integer, String>>(); 
        //array dove salvo gli eventuali errori da stampare
        ArrayList<String> errors = new ArrayList<String>();
        System.out.println("INIZIO VISITA DOMANDE");
        for(int i = 0; i<domande.size();i++){
            int p = i + 1;
            System.out.println("domanda " + i);
            Domanda d = domande.get(i);
            boolean hasRisposta = true; //se non è obbligatoria non ci interessa che abbia effettivamente la risposta ai fini dei controlli
            if(d.isObbligatoria()){
                hasRisposta = false;
                System.out.println("obbligatoria");
            }
            System.out.println("tipo: " + d.getTipo());
            System.out.println("ricerca di: " + d.getTipo()+i);
            if(d.getTipo().equals("openShort")){
                if(request.getParameter("openShort"+i)!=null && !request.getParameter("openShort"+i).isBlank()){
                    System.out.println("entrato nell'openShort");
                    openShortMap.put(i, request.getParameter("openShort"+i));
                    hasRisposta = true;
                    if(d.getVincolo()!=null && !d.getVincolo().isBlank()){
                        System.out.println("sezione vincoli openShort");
                        int vincoloShort = Integer.parseInt(d.getVincolo().substring(12));
                        System.out.println("vincoloShort: " + vincoloShort);
                        if(request.getParameter("openShort"+i).length()<vincoloShort){
                            System.out.println("errore trovato");
                            errors.add("la risposta in posizione " + p + " ha " + request.getParameter("openShort"+i).length() + " caratteri, mentre ne deve avere almeno " + vincoloShort);
                        }
                    }
                }
            } else if(d.getTipo().equals("openLong")){
                if(request.getParameter("openLong"+i)!=null && !request.getParameter("openLong"+i).isBlank()){
                    System.out.println("entrato nell'openLong");
                    openLongMap.put(i, request.getParameter("openLong"+i));
                    hasRisposta = true;
                    if(d.getVincolo()!=null && !d.getVincolo().isBlank()){
                        System.out.println("sezione vincoli openLong");
                        int vincoloLong = Integer.parseInt(d.getVincolo().substring(12));
                        System.out.println("vincoloLong: " + vincoloLong);
                        if(request.getParameter("openLong"+i).length()<vincoloLong){
                            System.out.println("errore trovato");
                            errors.add("la risposta in posizione " + p + " ha " + request.getParameter("openLong"+i).length() + " caratteri, mentre ne deve avere almeno " + vincoloLong);
                        }
                    }
                }
            } else if(d.getTipo().equals("openNumber")){
                if(request.getParameter("openNumber"+i)!=null && !request.getParameter("openNumber"+i).isBlank()){
                    System.out.println("entrato nell'openNumber");
                    openNumberMap.put(i, request.getParameter("openNumber"+i));
                    hasRisposta = true;
                    if(d.getVincolo()!=null && !d.getVincolo().isBlank()){
                        System.out.println("sezione vincoli openNumber");
                        int vincoloNumber1 = Integer.parseInt(d.getVincolo().substring(d.getVincolo().indexOf(":")+2, d.getVincolo().indexOf("-")-1));
                        int vincoloNumber2 = Integer.parseInt(d.getVincolo().substring(d.getVincolo().indexOf("-")+3));
                        System.out.println("vincoloNumber1: " + vincoloNumber1);
                        System.out.println("vincoloNumber2: " + vincoloNumber2);
                        if(Integer.parseInt(request.getParameter("openNumber"+i))<vincoloNumber1){
                            System.out.println("errore trovato");
                            errors.add("la risposta in posizione " + p + " dev'essere un numero maggiore di " + vincoloNumber1);
                        } else if(Integer.parseInt(request.getParameter("openNumber"+i))>vincoloNumber2){
                            System.out.println("errore trovato");
                            errors.add("la risposta in posizione " + p + " dev'essere un numero minore di " + vincoloNumber2);
                        }
                    }
                }
            } else if(d.getTipo().equals("openDate")){
                if(request.getParameter("openDate"+i)!=null && !request.getParameter("openDate"+i).isBlank()){
                    System.out.println("entrato nell'openDate");
                    openDateMap.put(i, request.getParameter("openDate"+i));
                    hasRisposta = true;
                    if(d.getVincolo()!=null && !d.getVincolo().isBlank()){
                        System.out.println("sezione vincoli openDate");
                        String vincoloDate1 = d.getVincolo().substring(12,22);
                        String vincoloDate2 = d.getVincolo().substring(26,36);
                        System.out.println("vincoloDate1: " + vincoloDate1);
                        System.out.println("vincoloDate2: " + vincoloDate2);
                        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(vincoloDate1);
                        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(vincoloDate2);
                        String dateAnswered = request.getParameter("openDate"+i);
                        Date dateA = new SimpleDateFormat("yyyy-MM-dd").parse(dateAnswered);
                        if(dateA.before(date1)){
                            System.out.println("errore trovato");
                            errors.add("la risposta in posizione " + p + " dev'essere una data successiva alla data " + d.getVincolo().substring(20,22)+"/"+ d.getVincolo().substring(17,19)+"/"+d.getVincolo().substring(12,16));
                        } else if(dateA.after(date2)){
                            System.out.println("errore trovato");
                            errors.add("la risposta in posizione " + p + " dev'essere una data precedente alla data " + d.getVincolo().substring(34,36)+"/"+ d.getVincolo().substring(31,33)+"/"+d.getVincolo().substring(26,30));
                        }
                    }
                }
            } else if(d.getTipo().equals("closeSingle")){
                if(d.getTipo().equals("closeSingle")){
                    if(request.getParameter("closeSingle"+i)!=null){
                        System.out.println("sezione openDate");
                        closeSingleMap.put(i, request.getParameter("closeSingle"+i));
                        hasRisposta = true;
                    }
                }
            } else if(d.getTipo().equals("closeMultiple")){
                System.out.println("sezione closeMultiple");
                JSONArray opzioni = d.getOpzioni().getJSONArray("opzioni");
                HashMap<Integer, String> closeMultipleIstance = new HashMap<Integer, String>();
                int k = 0;
                for(int j = 0; j < opzioni.length();j++){
                    if(request.getParameter("opzioneM"+i+"-"+j)!=null){
                        closeMultipleIstance.put(k, request.getParameter("opzioneM"+i+"-"+j));
                        hasRisposta = true;
                        k++;
                    }
                }
                closeMultipleMap.put(i, closeMultipleIstance);
            }
            

            if(!(hasRisposta)){
                errors.add("la domanda " + p + " è obbligatoria");
            }
        }
        
        for(int i = 0; i<domande.size();i++){
            Domanda d1 = domande.get(i);
            if(d1.getTipo().equals("closeSingle")){
                System.out.println("domanda in posizione " + i + " di tipo radio, ha risposta: " + closeSingleMap.get(i));
            }
            if(d1.getTipo().equals("closeMultiple")){
                HashMap<Integer, String> closeMultipleIstanceTemp = closeMultipleMap.get(i);
                for(int j = 0; j < closeMultipleIstanceTemp.size(); j++){
                    System.out.println("domanda in posizione " + i + " di tipo checkbox, ha risposta: ");
                    System.out.println(closeMultipleIstanceTemp.get(j));
                }
            }
        }
        
        if(!(errors.isEmpty())){
            request.setAttribute("errors", errors);
            request.setAttribute("domande", domande);
            request.setAttribute("sondaggio", sondaggio);
            res.activate("survey.ftl", request, response);
            return;
        }
        
        System.out.println("caso senza errori");
        
        HttpSession s = checkSession(request);
        Risposta risp = new RispostaImpl();
        if(request.getParameter("ModAnswer")!=null){
            ArrayList<Risposta> risposte;
            if(s!=null){
                risposte = (ArrayList<Risposta>) dl.getRispostaDAO().getRispostaByIdUtente((int) s.getAttribute("userid"));
            } else {
                risposte = (ArrayList<Risposta>) dl.getRispostaDAO().getRispostaByIPUtente(request.getRemoteHost());
            }
            int idRispostaCorretta = -1;
            for(int i = 0; i<risposte.size();i++){
                risp = risposte.get(i);
                for(int j = 0; j<domande.size(); j++){
                    Domanda d = domande.get(i);
                    JSONArray r = dl.getRispostaDomandaDAO().getRispostaDomanda(risp.getKey(), d.getKey()).getRisposta().getJSONArray("risposta");
                    if(r != null){
                        idRispostaCorretta = i;
                        break;
                    }
                }
                if(idRispostaCorretta!=-1){
                    break;
                }
            }
            risp = risposte.get(idRispostaCorretta);   
        } else {
            risp = dl.getRispostaDAO().createRisposta();
        } 
        
        int idRisposta = 0;
        Date nowTemp = new Date();
        if(s!=null){
            risp.setIdUtente((int)s.getAttribute("userid"));
            risp.setIpUtenteRisposta((String)s.getAttribute("ip"));
            risp.setUsernameUtenteRisposta((String)s.getAttribute("username"));
            risp.setData(nowTemp);
            idRisposta = dl.getRispostaDAO().storeRispostaUserReg(risp);   
        } else {
            risp.setIpUtenteRisposta(request.getRemoteHost());
            risp.setData(nowTemp);
            idRisposta = dl.getRispostaDAO().storeRispostaUserNotReg(risp);
        }
        
        if(request.getParameter("ModAnswer")==null){
            dl.getSondaggioDAO().updateCompilazioni(sondaggio.getKey());
        }
        
        for(int i = 0; i<domande.size(); i++){
            Domanda d = domande.get(i);
            JSONObject risposta = new JSONObject();
            ArrayList<String> r = new ArrayList<String>();
            if(d.getTipo().equals("openShort")){
                r.add(SecurityLayer.addSlashes(openShortMap.get(i)));
                risposta.put("risposta", new JSONArray(r)); 
            } else if(d.getTipo().equals("openLong")){
                r.add(SecurityLayer.addSlashes(openLongMap.get(i)));
                risposta.put("risposta", new JSONArray(r)); 
            } else if(d.getTipo().equals("openNumber")){
                r.add(SecurityLayer.addSlashes(openNumberMap.get(i)));
                risposta.put("risposta", new JSONArray(r)); 
            } else if(d.getTipo().equals("openDate")){
                r.add(SecurityLayer.addSlashes(openDateMap.get(i)));
                risposta.put("risposta", new JSONArray(r)); 
            } else if(d.getTipo().equals("closeSingle")){
                r.add(SecurityLayer.addSlashes(closeSingleMap.get(i)));
                risposta.put("risposta", new JSONArray(r)); 
            } else if(d.getTipo().equals("closeMultiple")){
                HashMap<Integer, String> closeMultipleIstanceTemp = closeMultipleMap.get(i);
                for(int j = 0; j < closeMultipleIstanceTemp.size(); j++){
                    r.add(SecurityLayer.addSlashes(closeMultipleIstanceTemp.get(j)));
                }
                risposta.put("risposta", new JSONArray(r)); 
            }
            System.out.println("IdRisposta: " + idRisposta + ", IdDomanda: " + d.getKey() + ", risposta: " + risposta.toString());
            RispostaDomanda rispDom = dl.getRispostaDomandaDAO().createRispostaDomanda();
            rispDom.setIdDomanda(d.getKey());
            rispDom.setIdRisposta(idRisposta);
            rispDom.setRisposta(risposta);
            if(request.getParameter("ModAnswer")!=null){
                dl.getRispostaDomandaDAO().updateRisposta(rispDom);
            } else {
                dl.getRispostaDomandaDAO().insertRisposta(rispDom);
            } 
        }
        
        request.setAttribute("sondaggio", sondaggio);
        RequestDispatcher rd = request.getRequestDispatcher("/surveyAnswered");
        rd.forward(request, response);
        
        return;
        } catch (TemplateManagerException ex) {
            Logger.getLogger(Survey.class.getName()).log(Level.SEVERE, null, ex);
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

    private void action_load_survey(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException, TemplateManagerException, DataException, ParseException {
        TemplateResult res = new TemplateResult(getServletContext());
        PollWebDataLayer dl = ((PollWebDataLayer)request.getAttribute("datalayer"));
        int idSondaggio = Integer.parseInt(request.getParameter("returnSurvey"));
        Sondaggio sondaggio = dl.getSondaggioDAO().getSondaggio(idSondaggio);
        ArrayList<Domanda> domande = (ArrayList<Domanda>) dl.getDomandaDAO().getDomandaByIdSondaggio(idSondaggio);
        Collections.sort(domande);
        request.setAttribute("domande", domande);
        request.setAttribute("sondaggio", sondaggio);
        ArrayList<Risposta> risposte;
        HttpSession s = checkSession(request);
        if(s!=null){
            risposte = (ArrayList<Risposta>) dl.getRispostaDAO().getRispostaByIdUtente((int) s.getAttribute("userid"));
        } else {
            risposte = (ArrayList<Risposta>) dl.getRispostaDAO().getRispostaByIPUtente(request.getRemoteHost());
        }
        Risposta risposta = new RispostaImpl();
        int idRispostaCorretta = -1;
        for(int i = 0; i<risposte.size();i++){
            risposta = risposte.get(i);
            for(int j = 0; j<domande.size(); j++){
                Domanda d = domande.get(i);
                JSONArray r = dl.getRispostaDomandaDAO().getRispostaDomanda(risposta.getKey(), d.getKey()).getRisposta().getJSONArray("risposta");
                if(r != null){
                    idRispostaCorretta = i;
                    break;
                }
            }
            if(idRispostaCorretta!=-1){
                break;
            }
        }
        risposta = risposte.get(idRispostaCorretta);
        HashMap<String, String> risposteNoMult = new HashMap<String, String>();
        HashMap<String, String> risposteRadios = new HashMap<String, String>();
        HashMap<String, HashMap<String, String>> risposteSiMult = new HashMap<String, HashMap<String, String>>();
        for(int i = 0; i<domande.size(); i++){
            Domanda d = domande.get(i);
            JSONArray r = dl.getRispostaDomandaDAO().getRispostaDomanda(risposta.getKey(), d.getKey()).getRisposta().getJSONArray("risposta");
            if(r != null){
                if (d.getTipo().equals("closeMultiple")){
                    HashMap<String, String> temp = new HashMap<String, String>();
                    for(int j = 0; j<r.length(); j++){
                        temp.put(String.valueOf(j), r.get(j).toString());
                    }
                    risposteSiMult.put(String.valueOf(i), temp);
                } else if(d.getTipo().equals("closeSingle")) {
                    risposteRadios.put(String.valueOf(i), r.get(0).toString());
                } else {
                    risposteNoMult.put(String.valueOf(i), r.get(0).toString());
                }
            } else {
                if(d.isObbligatoria()){
                    request.setAttribute("message", "errore nel caricamento delle risposte");
                    action_error(request, response);
                }
            }
        }
      
        if(!risposteNoMult.isEmpty()){
            request.setAttribute("risposteNoMult", risposteNoMult);
        }
        if(!risposteRadios.isEmpty()){
            request.setAttribute("risposteRadios", risposteRadios);
        }
        if(!risposteSiMult.isEmpty()){
            request.setAttribute("risposteSiMult", risposteSiMult);
        }
        request.setAttribute("modRisposta", "yes");
        res.activate("survey.ftl", request, response);
        return;
    }

}
