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
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author joker
 */
@MultipartConfig
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
                }else if("POST".equals(request.getMethod())){
                    if(request.getParameter("createOnly") != null || request.getParameter("createAndPublic") != null){
                        System.out.println("sono passato per createOnly/CreateAndPublic");
                        ArrayList<Utente> partecipants = new ArrayList<Utente>();   
                        if (request.getContentType() != null && request.getContentType().toLowerCase().contains("multipart/form-data")) {
                            System.out.println("passato enctype");
                            if(request.getParameter("withCSV") != null){    
                                Part filePart = request.getPart("file");
                                File uploaded_file = File.createTempFile("upload_", "", new File(getServletContext().getInitParameter("uploads.directory")));
                                try (InputStream is = filePart.getInputStream();
                                        OutputStream os = new FileOutputStream(uploaded_file)) {
                                    byte[] buffer = new byte[1024];
                                    int read;
                                    while ((read = is.read(buffer)) > 0) {
                                        os.write(buffer, 0, read);
                                    }
                                }
                                List<String[]> r = new ArrayList<String[]>();
                                
                                try (CSVReader reader = new CSVReader(new FileReader(uploaded_file))) {
                                    r = reader.readAll();
                                    System.out.println("lettura riuscita - 2");
                                }
                                
                                boolean allEmpty = true;
                                for(int i = 0; i < r.size(); i++){
                                    String[] x = r.get(i);
                                    if((!(x[0].isBlank())) || (!(x[1].isBlank())) || (!(x[2].isBlank()))){
                                        allEmpty = false;
                                    }
                                }
                                if(!(allEmpty)){
                                    for(int i = 0; i < r.size(); i++){
                                        String[] x = r.get(i);
                                        Utente u = new UtenteImpl();
                                        if(x.length == 3){
                                            u.setNome(x[0]);
                                            u.setEmail(x[1]);
                                            u.setPassword(x[2]);
                                        } else if (x.length == 2){
                                            u.setNome(x[0]);
                                            u.setEmail(x[1]);
                                            u.setPassword("");
                                        } else if (x.length == 1){
                                            u.setNome(x[0]);
                                            u.setEmail("");
                                            u.setPassword("");
                                        } else {
                                            u.setNome("");
                                            u.setEmail("");
                                            u.setPassword("");
                                        }
                                        partecipants.add(u);
                                        if(!partecipants.isEmpty()){
                                            ArrayList<Utente> tempPartecipants = new ArrayList<Utente>();
                                            for(int k = partecipants.size()-1; k >= 0 ; k--){
                                                if (partecipants.get(k).getNome().isBlank() && partecipants.get(k).getEmail().isBlank() && partecipants.get(k).getPassword().isBlank()){
                                                    partecipants.remove(k); 
                                                }
                                            }
                                            for(int k = 0; k < partecipants.size(); k++){
                                                tempPartecipants.add(partecipants.get(k));
                                            }
                                            for(int j = 0; j < partecipants.size(); j++){
                                                Utente checkU = partecipants.get(j);
                                                if(checkU.getNome().isBlank() || checkU.getEmail().isBlank() || checkU.getPassword().isBlank()){
                                                    partecipants.remove(checkU);
                                                }
                                                String passwordOfU = checkU.getPassword();
                                                Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                                                Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
                                                Pattern lowerCasePatten = Pattern.compile("[a-z ]");
                                                Pattern digitCasePatten = Pattern.compile("[0-9 ]");
                                                Pattern emailPattern = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
                                                if(passwordOfU.length()<8 || !UpperCasePatten.matcher(passwordOfU).find() || !lowerCasePatten.matcher(passwordOfU).find() || !digitCasePatten.matcher(passwordOfU).find() || !specailCharPatten.matcher(passwordOfU).find()){
                                                    partecipants.remove(checkU);
                                                }
                                                if (!emailPattern.matcher(checkU.getEmail()).find()) {
                                                    partecipants.remove(checkU);
                                                }
                                                tempPartecipants.remove(0);
                                                for(int k = 0; k < tempPartecipants.size(); k++){
                                                    Utente tempUtente = tempPartecipants.get(k);
                                                    if(tempUtente.getEmail().equals(checkU.getEmail()) || tempUtente.getPassword().equals(checkU.getPassword())){
                                                        partecipants.remove(checkU);
                                                        partecipants.remove(tempUtente);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                uploaded_file.delete();
                            } else if(request.getParameter("usersName[]")!=null || request.getParameter("usersMail[]")!=null || request.getParameter("usersPass[]")!=null){
                                String[] name = request.getParameterValues("usersName[]");
                                String[] mail = request.getParameterValues("usersMail[]");
                                String[] pass = request.getParameterValues("usersPass[]");
                                int maxIndex; 
                                maxIndex = Integer.max(name.length, mail.length);
                                maxIndex = Integer.max(maxIndex, pass.length);
                                System.out.println("MAXINDEX = " + maxIndex);
                                boolean allEmpty = true;
                                for(int i = 0; i < maxIndex; i++){
                                    if((!(name[i].isBlank())) || (!(mail[i].isBlank())) || (!(pass[i].isBlank()))){
                                        allEmpty = false;
                                    }
                                }
                                if(!(allEmpty)){
                                    for(int i = 0; i < maxIndex; i++){
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
                                        for(int k = partecipants.size()-1; k >= 0 ; k--){
                                            if (partecipants.get(k).getNome().isBlank() && partecipants.get(k).getEmail().isBlank() && partecipants.get(k).getPassword().isBlank()){
                                                partecipants.remove(k); 
                                            }
                                        }
                                        for(int k = 0; k < partecipants.size(); k++){
                                            tempPartecipants.add(partecipants.get(k));
                                        }
                                        for(int j = 0; j < partecipants.size(); j++){
                                            Utente checkU = partecipants.get(j);
                                            if(checkU.getNome().isBlank() || checkU.getEmail().isBlank() || checkU.getPassword().isBlank()){
                                                partecipants.remove(checkU);
                                            }
                                            String passwordOfU = checkU.getPassword();
                                            Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                                            Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
                                            Pattern lowerCasePatten = Pattern.compile("[a-z ]");
                                            Pattern digitCasePatten = Pattern.compile("[0-9 ]");
                                            Pattern emailPattern = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
                                            if(passwordOfU.length()<8 || !UpperCasePatten.matcher(passwordOfU).find() || !lowerCasePatten.matcher(passwordOfU).find() || !digitCasePatten.matcher(passwordOfU).find() || !specailCharPatten.matcher(passwordOfU).find()){
                                                partecipants.remove(checkU);
                                            }
                                            if (!emailPattern.matcher(checkU.getEmail()).find()) {
                                                partecipants.remove(checkU);
                                            }
                                            tempPartecipants.remove(0);
                                            for(int k = 0; k < tempPartecipants.size(); k++){
                                                System.out.println("terzo ciclo for, ciclo " + k);
                                                Utente tempUtente = tempPartecipants.get(k);
                                                if(tempUtente.getEmail().equals(checkU.getEmail()) || tempUtente.getPassword().equals(checkU.getPassword())){
                                                    partecipants.remove(checkU);
                                                }
                                            }
                                        }
                                    }
                                }    
                            }
                            if(!partecipants.isEmpty()){
                                for(int i = 0; i < partecipants.size(); i++){
                                    dl.getUtenteDAO().insertUtenteListaPartecipanti(partecipants.get(i), (int)s.getAttribute("sondaggio-in-creazione"));
                                }
                            }
                            Sondaggio sondaggio = dl.getSondaggioDAO().getSondaggio((int)s.getAttribute("sondaggio-in-creazione"));
                            sondaggio.setCompleto(true);
                            if(request.getParameter("createOnly")!=null){
                                sondaggio.setVisibilita(false);
                            } else {
                                sondaggio.setVisibilita(true);
                            }
                            sondaggio.setCompleto(true);
                            dl.getSondaggioDAO().storeSondaggio(sondaggio);
                            s.setAttribute("sondaggio-in-creazione", 0);
                            s.setAttribute("continue", "no");
                            s.setAttribute("sondaggio-in-conferma", "no");
                            s.setAttribute("domanda-in-creazione", 0);
                            response.sendRedirect("../dashboard");
                            return;
                        } else {
                            action_error(request, response);
                            return;
                        }
                    }
                    if(request.getParameter("goUpQuestion") != null){ //goUp Vuol dire diminuire la posizione (so che suona strano)
                        System.out.println("sono passato per UP");
                        int position = Integer.parseInt(request.getParameter("goUpQuestion").substring(18));
                        Domanda domandaToGoUp = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), position);
                        Domanda domandaToGoDown = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), position-1);
                        domandaToGoUp.setPosizione(position-1);
                        domandaToGoDown.setPosizione(position);
                        dl.getDomandaDAO().storeDomanda(domandaToGoUp);
                        dl.getDomandaDAO().storeDomanda(domandaToGoDown);
                        action_default(request, response);
                        return;
                    }else if(request.getParameter("goDownQuestion") != null){ //goDown Vuol dire aumentare la posizione (so che suona strano)
                        System.out.println("sono passato per DOWN");
                        int position = Integer.parseInt(request.getParameter("goDownQuestion").substring(20));
                        Domanda domandaToGoDown = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), position);
                        Domanda domandaToGoUp = dl.getDomandaDAO().getDomandaByIdSondaggioAndPosition((int)s.getAttribute("sondaggio-in-creazione"), position+1);
                        domandaToGoDown.setPosizione(position+1);
                        domandaToGoUp.setPosizione(position);
                        dl.getDomandaDAO().storeDomanda(domandaToGoDown);
                        dl.getDomandaDAO().storeDomanda(domandaToGoUp);
                        action_default(request, response);
                        return;
                    }else if(request.getParameter("removeQuestion") != null){
                        System.out.println("sono passato per REMOVE");
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
                    }
                    
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

        } catch (CsvException ex) {
            Logger.getLogger(ConfirmSection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void action_redirect(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        response.sendRedirect("confirmSection");
        return;
    }
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException, DataException, CsvException {
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
            if(!(request.getParameter("withCSV") != null && request.getParameter("withCSV").equals("withCSV"))){
                if(request.getParameter("usersName[]")!=null || request.getParameter("usersMail[]")!=null || request.getParameter("usersPass[]")!=null){
                    String[] name = request.getParameterValues("usersName[]");
                    String[] mail = request.getParameterValues("usersMail[]");
                    String[] pass = request.getParameterValues("usersPass[]");
                    int maxIndex; 
                    maxIndex = Integer.max(name.length, mail.length);
                    maxIndex = Integer.max(maxIndex, pass.length);
                    ArrayList<Utente> partecipants = new ArrayList<Utente>();
                    System.out.println("MAXINDEX = " + maxIndex);
                    boolean allEmpty = true;
                    for(int i = 0; i < maxIndex; i++){
                        if((!(name[i].isBlank())) || (!(mail[i].isBlank())) || (!(pass[i].isBlank()))){
                            allEmpty = false;
                        }
                    }
                    if(!(allEmpty)){
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