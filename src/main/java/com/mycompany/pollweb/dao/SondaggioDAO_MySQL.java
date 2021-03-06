/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.dao;

import com.mycompany.pollweb.data.DAO;
import com.mycompany.pollweb.data.DataException;
import com.mycompany.pollweb.data.DataItemProxy;
import com.mycompany.pollweb.data.DataLayer;
import com.mycompany.pollweb.model.Sondaggio;
import com.mycompany.pollweb.proxy.SondaggioProxy;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.pollweb.data.OptimisticLockException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cronio
 */
public class SondaggioDAO_MySQL extends DAO implements SondaggioDAO {
    
    private PreparedStatement sSondaggioByID;
    private PreparedStatement sSondaggioByIDUtente;
    private PreparedStatement sSondaggiByIDUtente;
    private PreparedStatement sSondaggi;
    private PreparedStatement sSondaggiAdmin;
    private PreparedStatement iSondaggio;
    private PreparedStatement uSondaggio;
    private PreparedStatement uSondaggioCompilazioni;
    private PreparedStatement dSondaggio;
    private PreparedStatement searchSondaggiTitolo;
    private PreparedStatement searchSondaggiDataCreazione;
    private PreparedStatement searchSondaggiDataChiusura;
    private PreparedStatement searchSondaggiNoData;
    private PreparedStatement sSondaggiPrivatiByIdUtente;
    private PreparedStatement sSondaggiCompilati;
    private PreparedStatement sSondaggiCompilati2;
    private PreparedStatement sSondaggiCompilati3;
    private PreparedStatement sSondaggiRecent;
    private PreparedStatement sSondaggiOld;
    private PreparedStatement sSondaggiPopolari;
    private PreparedStatement sSondaggiPopolari9;
    
    SondaggioDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataException {
        try {
            super.init();
            
            sSondaggioByID = connection.prepareStatement("SELECT * FROM Sondaggio WHERE idSondaggio=?");
            sSondaggioByIDUtente = connection.prepareStatement("SELECT * FROM Sondaggio WHERE idUtente=?");
            sSondaggiByIDUtente = connection.prepareStatement("SELECT * FROM Sondaggio WHERE idUtente=?");
            sSondaggi = connection.prepareStatement("SELECT * FROM Sondaggio WHERE privato=0 AND visibilita=1 AND (dataChiusura >= CURDATE() OR dataChiusura IS NULL)"); //update per far apparire i sondaggi solo non modificabili
            sSondaggiAdmin = connection.prepareStatement("SELECT * FROM Sondaggio");
            
            iSondaggio = connection.prepareStatement("INSERT INTO Sondaggio (idUtente,titolo,testoApertura,testoChiusura,completo,visibilita,dataCreazione,dataChiusura,privato,modificabile,compilazioni) VALUES(?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            uSondaggio = connection.prepareStatement("UPDATE Sondaggio SET idUtente=?,titolo=?,testoApertura=?,testoChiusura=?,completo=?,visibilita=?,dataChiusura=?, privato=?,modificabile=?,compilazioni=?,version=? WHERE idSondaggio=? AND version=?");
            uSondaggioCompilazioni = connection.prepareStatement("UPDATE SONDAGGIO SET compilazioni =? WHERE idSondaggio=?");

            dSondaggio = connection.prepareStatement("DELETE FROM Sondaggio WHERE idSondaggio=?");
            searchSondaggiTitolo = connection.prepareStatement("SELECT * FROM Sondaggio WHERE titolo LIKE ?");
            searchSondaggiDataCreazione = connection.prepareStatement("SELECT * FROM Sondaggio WHERE dataCreazione = ?");
            searchSondaggiDataChiusura = connection.prepareStatement("SELECT * FROM Sondaggio WHERE dataChiusura = ?");
            searchSondaggiNoData = connection.prepareStatement("SELECT * FROM Sondaggio WHERE dataChiusura IS NULL");
            
            sSondaggiPrivatiByIdUtente = connection.prepareStatement("SELECT * FROM ListaPartecipanti WHERE idUtente=?");
            
            sSondaggiCompilati = connection.prepareStatement("SELECT * FROM Risposta WHERE idUtente=?");
            sSondaggiCompilati2 = connection.prepareStatement("SELECT * FROM RispostaDomanda WHERE idRisposta=?");
            sSondaggiCompilati3 = connection.prepareStatement("SELECT * FROM Domanda WHERE idDomanda=?");
            
            sSondaggiRecent = connection.prepareStatement("SELECT * FROM Sondaggio WHERE privato=0 AND visibilita=1 AND (dataChiusura >= CURDATE() OR dataChiusura IS NULL) ORDER BY dataCreazione DESC");
            sSondaggiOld = connection.prepareStatement("SELECT * FROM Sondaggio WHERE privato=0 AND visibilita=1 AND (dataChiusura >= CURDATE() OR dataChiusura IS NULL) ORDER BY dataCreazione");
            sSondaggiPopolari = connection.prepareStatement("SELECT * FROM Sondaggio WHERE privato=0 AND visibilita=1 AND (dataChiusura >= CURDATE() OR dataChiusura IS NULL) ORDER BY compilazioni DESC");
            sSondaggiPopolari9 = connection.prepareStatement("SELECT * FROM Sondaggio WHERE privato=0 AND visibilita=1 AND (dataChiusura >= CURDATE() OR dataChiusura IS NULL) ORDER BY compilazioni DESC LIMIT 9");
            
            
            
        } catch (SQLException ex) {
            throw new DataException("Error initializing PollWebdb data layer", ex);
        }
    }
    
    @Override
    public void destroy() throws DataException {

        try {
            
            sSondaggioByID.close();
            sSondaggioByIDUtente.close();
            sSondaggiByIDUtente.close();
            searchSondaggiTitolo.close();
            searchSondaggiDataCreazione.close();
            searchSondaggiDataChiusura.close();
            searchSondaggiNoData.close();
            sSondaggiPrivatiByIdUtente.close();
            sSondaggiCompilati.close();
            sSondaggiCompilati2.close();
            sSondaggiCompilati3.close();
            sSondaggiRecent.close();
            sSondaggiOld.close();
            sSondaggiPopolari.close();
            sSondaggiPopolari9.close();
            
            sSondaggi.close();
            sSondaggiAdmin.close();
            
            iSondaggio.close();
            uSondaggio.close();
            uSondaggioCompilazioni.close();
            dSondaggio.close();
            
        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }
    
    @Override
    public SondaggioProxy createSondaggio() {
        return new SondaggioProxy(getDataLayer());
    }
    
    //helper
    private SondaggioProxy createSondaggio(ResultSet rs) throws DataException {
        SondaggioProxy s = createSondaggio();
        try {
            s.setKey(rs.getInt("idSondaggio"));
            s.setIdUtente(rs.getInt("idUtente"));
            s.setTitolo(rs.getString("titolo"));
            s.setTestoApertura(rs.getString("testoApertura"));
            s.setTestoChiusura(rs.getString("testoChiusura"));
            s.setCompleto(rs.getBoolean("completo"));
            s.setVisibilita(rs.getBoolean("visibilita"));
            s.setCreazione(rs.getDate("dataCreazione"));
            s.setScadenza(rs.getDate("dataChiusura"));
            s.setPrivato(rs.getBoolean("privato"));
            s.setModificabile(rs.getBoolean("modificabile"));
            s.setCompilazioni(rs.getInt("compilazioni"));
            s.setVersion(rs.getLong("version"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create Sondaggio object form ResultSet", ex);
        }
        return s;
    }
    
    @Override
    public Sondaggio getSondaggio(int idSondaggio) throws DataException {
        Sondaggio s = null;
        //prima vediamo se l'oggetto è già stato caricato
        if (dataLayer.getCache().has(Sondaggio.class, idSondaggio)) {
            s = dataLayer.getCache().get(Sondaggio.class, idSondaggio);
        } else {
            //altrimenti lo carichiamo dal database
            try {
                sSondaggioByID.setInt(1, idSondaggio);
                try (ResultSet rs = sSondaggioByID.executeQuery()) {
                    if (rs.next()) {
                        s = createSondaggio(rs);
                        //e lo mettiamo anche nella cache
                        dataLayer.getCache().add(Sondaggio.class, s);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load Sondaggio by idSondaggio", ex);
            }
        }
        return s;
    }
    
    @Override
    public Sondaggio getSondaggioByIdUtente(int idUtente) throws DataException {
        Sondaggio s = null;
        //prima vediamo se l'oggetto è già stato caricato
        if (dataLayer.getCache().has(Sondaggio.class, idUtente)) {
            s = dataLayer.getCache().get(Sondaggio.class, idUtente);
        } else {
            //altrimenti lo carichiamo dal database
            try {
                sSondaggioByIDUtente.setInt(1, idUtente);
                try (ResultSet rs = sSondaggioByIDUtente.executeQuery()) {
                    if (rs.next()) {
                        s = createSondaggio(rs);
                        //e lo mettiamo anche nella cache
                        dataLayer.getCache().add(Sondaggio.class, s);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load Sondaggio by idSondaggio", ex);
            }
        }
        return s;
    }
    
    @Override
    public List<Sondaggio> getSondaggi() throws DataException {
        List<Sondaggio> result = new ArrayList();

        try (ResultSet rs = sSondaggi.executeQuery()) {
            while (rs.next()) {
                result.add((Sondaggio) getSondaggio(rs.getInt("idSondaggio")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Sondaggi", ex);
        }
        return result;
    }
    
    @Override
    public List<Sondaggio> getSondaggiAdmin() throws DataException {
        List<Sondaggio> result = new ArrayList();

        try (ResultSet rs = sSondaggiAdmin.executeQuery()) {
            while (rs.next()) {
                result.add((Sondaggio) getSondaggio(rs.getInt("idSondaggio")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Sondaggi", ex);
        }
        return result;
    }
    
    @Override
    public ArrayList<Sondaggio> getSondaggiRecenti() throws DataException {
        ArrayList<Sondaggio> result = new ArrayList();

        try (ResultSet rs = sSondaggiRecent.executeQuery()) {
            while (rs.next()) {
                result.add((Sondaggio) getSondaggio(rs.getInt("idSondaggio")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Sondaggi", ex);
        }
        return result;
    }
    
    @Override
    public ArrayList<Sondaggio> getSondaggiMenoRecenti() throws DataException {
        ArrayList<Sondaggio> result = new ArrayList();

        try (ResultSet rs = sSondaggiOld.executeQuery()) {
            while (rs.next()) {
                result.add((Sondaggio) getSondaggio(rs.getInt("idSondaggio")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Sondaggi", ex);
        }
        return result;
    }
    
    @Override
    public ArrayList<Sondaggio> getSondaggiPopolari() throws DataException {
        ArrayList<Sondaggio> result = new ArrayList();

        try (ResultSet rs = sSondaggiPopolari.executeQuery()) {
            while (rs.next()) {
                result.add((Sondaggio) getSondaggio(rs.getInt("idSondaggio")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Sondaggi", ex);
        }
        return result;
    }
    
    @Override
    public ArrayList<Sondaggio> getSondaggiPopolari9() throws DataException {
        ArrayList<Sondaggio> result = new ArrayList();

        try (ResultSet rs = sSondaggiPopolari9.executeQuery()) {
            while (rs.next()) {
                result.add((Sondaggio) getSondaggio(rs.getInt("idSondaggio")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Sondaggi", ex);
        }
        return result;
    }
    
    @Override
    public ArrayList<Sondaggio> getSondaggiByIdUtente(int idUtente) throws DataException {
        ArrayList<Sondaggio> sondaggi = new ArrayList();
        try {
            sSondaggiByIDUtente.setInt(1, idUtente);
            try (ResultSet rs = sSondaggiByIDUtente.executeQuery()) {
                while (rs.next()) {
                    sondaggi.add((Sondaggio) getSondaggio(rs.getInt("idSondaggio")));
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Sondaggi", ex);
        }
        return sondaggi;
    }


    @Override
    public void storeSondaggio (Sondaggio sondaggio) throws DataException {
        try {
            if (sondaggio.getKey() != null && sondaggio.getKey() > 0) { //update
                if (sondaggio instanceof DataItemProxy && !((DataItemProxy) sondaggio).isModified()) {
                    return;
                }
                if(sondaggio.getScadenza() != null){
                    java.sql.Date sqlScadenza = new java.sql.Date( sondaggio.getScadenza().getTime() );
                    uSondaggio.setDate(7, sqlScadenza);
                }
                else{
                    uSondaggio.setDate(7, null);
                }
            
                uSondaggio.setInt(1, sondaggio.getIdUtente()); //update
                System.out.println("IDUTENTE: " + sondaggio.getIdUtente());
                uSondaggio.setString(2, sondaggio.getTitolo());
                if(sondaggio.getTestoApertura()!=null){
                    if(!sondaggio.getTestoApertura().isBlank()){
                        uSondaggio.setString(3, sondaggio.getTestoApertura());
                    }
                    else{
                        uSondaggio.setString(3, null);
                    }
                }
                else{
                    uSondaggio.setString(3, null);
                }
                if(sondaggio.getTestoChiusura() != null){
                    if(!sondaggio.getTestoChiusura().isBlank()){
                        uSondaggio.setString(4, sondaggio.getTestoChiusura());
                    }
                    else{
                        uSondaggio.setString(4, null);
                    }
                    
                }
                else{
                    uSondaggio.setString(4, null);
                }
                uSondaggio.setBoolean(5, sondaggio.isCompleto());
                uSondaggio.setBoolean(6, sondaggio.isVisibilita());
                uSondaggio.setBoolean(8, sondaggio.isPrivato());
                uSondaggio.setBoolean(9, sondaggio.isModificabile());
                uSondaggio.setInt(10, sondaggio.getCompilazioni());
                
                long currentVersion = sondaggio.getVersion();
                long nextVersion = currentVersion + 1;
                
                uSondaggio.setLong(11, nextVersion);
                uSondaggio.setInt(12, sondaggio.getKey());
                uSondaggio.setLong(13, currentVersion);

                if (uSondaggio.executeUpdate() == 0) {
                    throw new OptimisticLockException(sondaggio);
                }
                sondaggio.setVersion(nextVersion);
            }
            else {
                
                java.sql.Date sqlCreazione = new java.sql.Date( sondaggio.getCreazione().getTime() );
                if(sondaggio.getScadenza() != null){
                    java.sql.Date sqlScadenza = new java.sql.Date( sondaggio.getScadenza().getTime() );
                    iSondaggio.setDate(8, sqlScadenza);
                }
                else{
                    iSondaggio.setDate(8, null);
                }
                
                iSondaggio.setInt(1, sondaggio.getIdUtente()); //insert
                iSondaggio.setString(2, sondaggio.getTitolo());
                if(!sondaggio.getTestoApertura().isEmpty()){
                    iSondaggio.setString(3, sondaggio.getTestoApertura());
                }
                else{
                    iSondaggio.setString(3, null);
                }
                if(!sondaggio.getTestoChiusura().isEmpty()){
                    iSondaggio.setString(4, sondaggio.getTestoChiusura());
                }
                else{
                    iSondaggio.setString(4, null);
                }
                iSondaggio.setBoolean(5, sondaggio.isCompleto());
                iSondaggio.setBoolean(6, sondaggio.isVisibilita());
                iSondaggio.setDate(7, sqlCreazione);
                iSondaggio.setBoolean(9, sondaggio.isPrivato());
                iSondaggio.setBoolean(10, sondaggio.isModificabile());
                iSondaggio.setInt(11, sondaggio.getCompilazioni());
                
                if (iSondaggio.executeUpdate() == 1) {
                    try (ResultSet keys = iSondaggio.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            sondaggio.setKey(key);
                            dataLayer.getCache().add(Sondaggio.class, sondaggio);
                        }
                    }
                }
            }
            if (sondaggio instanceof DataItemProxy) {
                ((DataItemProxy) sondaggio).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store sondaggio", ex);
        }
    }
    
    @Override
    public ArrayList<Sondaggio> getSondaggiPrivati(int idUtente) throws DataException{
        ArrayList<Sondaggio> sp = new ArrayList();
        Sondaggio s;
        int idSondaggio; 
        ArrayList<Sondaggio> sondaggiCompilati = getSondaggiCompilati(idUtente);
        try {
            sSondaggiPrivatiByIdUtente.setInt(1, idUtente);
            try (ResultSet rs = sSondaggiPrivatiByIdUtente.executeQuery()) {
                while (rs.next()) {
                    idSondaggio = rs.getInt("idSondaggio");
                    s = getSondaggio(idSondaggio);
                    if(!sondaggiCompilati.contains(s)){      
                        if (s.isVisibilita()){
                            sp.add(s);
                        }
                    }
                }
            }
            } catch (SQLException ex) {
                //
        }
        return sp;
    }
    
    @Override
    public ArrayList<Sondaggio> getSondaggiCompilati(int idUtente) throws DataException{
        ArrayList<Sondaggio> sp = new ArrayList();
        Sondaggio s;
        int idRisposta; 
        int idDomanda; 
        int idSondaggio; 
        
        try {
            sSondaggiCompilati.setInt(1, idUtente);
            try(ResultSet rs = sSondaggiCompilati.executeQuery()) { //prendiamo l'id delle risposte che ci interessano
                while (rs.next()) {
                    idRisposta = rs.getInt("idRisposta");
                    sSondaggiCompilati2.setInt(1, idRisposta);
                    try (ResultSet rs2 = sSondaggiCompilati2.executeQuery()) { //prendiamo un solo idDomanda per ogni idRisposta che abbiamo (tanto ogni domanda per quell'id è riferita allo stesso sondaggio)
                        if (rs2.next()) {
                            idDomanda = rs2.getInt("idDomanda");
                            
                            sSondaggiCompilati3.setInt(1, idDomanda);
                            try (ResultSet rs3 = sSondaggiCompilati3.executeQuery()) { //prendiamo tutta la domanda che conterrà l'idSondaggio al quale è riferita
                                if (rs3.next()) {
                                    idSondaggio = rs3.getInt("idSondaggio");
                                    s = getSondaggio(idSondaggio);
                                    sp.add(s);
                                }
                            }  
                        }
                    }   
                }
            } catch (SQLException ex) {
                //
            }
        
        }catch (SQLException ex) {
            Logger.getLogger(SondaggioDAO_MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sp;
    }
    
    
    @Override
    public ArrayList<Sondaggio> searchSondaggi(ArrayList<Sondaggio> sondaggi, String ricerca) throws DataException{
        
        if(ricerca == null || ricerca.isEmpty()){
            return sondaggi;
        }
        
        ArrayList<Sondaggio> sondaggiSearch = new ArrayList();
        
        boolean data = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //controllo se sto ricercando una data nel formato corretto
        sdf.setLenient(false);
        try {
            sdf.parse(ricerca);
            data = true;
        } catch (ParseException e) {
            data = false;
        }
        try {
            if(!data){
                if("indeterminata".equals(ricerca) || "indeterminato".equals(ricerca) || "N/A".equals(ricerca)){
                    try (ResultSet rsNoData = searchSondaggiNoData.executeQuery()) {
                        while (rsNoData.next()) {
                            Sondaggio sondaggio = ((Sondaggio) getSondaggio(rsNoData.getInt("idSondaggio"))); // SONDAGGIO: il sondaggio del result
                            for (int i = 0; i < sondaggi.size(); i++){
                                Sondaggio sondaggioList = (Sondaggio) sondaggi.get(i); //SONDAGGIOLIST: il sondaggio corrente in "sondaggi" scrollando la lista
                                if(sondaggio.getKey() == sondaggioList.getKey()){
                                    boolean add = true;
                                    for (int j = 0; j < sondaggiSearch.size(); j++){
                                        Sondaggio sondaggioCheck = (Sondaggio) sondaggiSearch.get(j);
                                        if(sondaggioCheck.getKey() == sondaggioList.getKey()){
                                            add = false;
                                        }
                                    }
                                    if(add){
                                        sondaggiSearch.add(sondaggioList);
                                    }
                                    else{add = true;}
                                } 
                            }
                        }
                    }
                }
                searchSondaggiTitolo.setString(1, ricerca);
                try (ResultSet rsTitolo = searchSondaggiTitolo.executeQuery()) {
                    while (rsTitolo.next()) {
                        Sondaggio sondaggio = ((Sondaggio) getSondaggio(rsTitolo.getInt("idSondaggio"))); //il sondaggio del result
                        for (int i = 0; i < sondaggi.size(); i++){
                            Sondaggio sondaggioList = (Sondaggio) sondaggi.get(i); //il sondaggio corrente in "sondaggi"
                            if(sondaggio.getKey() == sondaggioList.getKey()){
                                boolean add = true;
                                for (int j = 0; j < sondaggiSearch.size(); j++){
                                    Sondaggio sondaggioCheck = (Sondaggio) sondaggiSearch.get(j);
                                    if(sondaggioCheck.getKey() == sondaggioList.getKey()){
                                        add = false;
                                    }
                                }
                                if(add){
                                    sondaggiSearch.add(sondaggioList);
                                }
                                else{add = true;}
                            } 
                        }
                    }
                }
            }
            else{
                System.out.println("Siamo nella zona DataCreazione");
                java.util.Date dataRicerca = new SimpleDateFormat("yyyy-MM-dd").parse(ricerca);  //creiamo una data per cercarla nel DB
                java.sql.Date dataRicercaSQL = new java.sql.Date(dataRicerca.getTime());
                
                searchSondaggiDataCreazione.setDate(1, dataRicercaSQL);
                try (ResultSet rsCreazione = searchSondaggiDataCreazione.executeQuery()) {
                    while (rsCreazione.next()) {
                        Sondaggio sondaggio = ((Sondaggio) getSondaggio(rsCreazione.getInt("idSondaggio"))); //il sondaggio del result
                        for (int i = 0; i < sondaggi.size(); i++){
                            Sondaggio sondaggioList = (Sondaggio) sondaggi.get(i); //il sondaggio corrente in "sondaggi"
                            if(sondaggio.getKey() == sondaggioList.getKey()){
                                boolean add = true;
                                for (int j = 0; j < sondaggiSearch.size(); j++){
                                    Sondaggio sondaggioCheck = (Sondaggio) sondaggiSearch.get(j);
                                    if(sondaggioCheck.getKey() == sondaggioList.getKey()){
                                        add = false;
                                    }
                                }
                                if(add){
                                    sondaggiSearch.add(sondaggioList);
                                }
                            } 
                        }
                    }
                }
                
                searchSondaggiDataChiusura.setDate(1, dataRicercaSQL);
                try (ResultSet rsChiusura = searchSondaggiDataChiusura.executeQuery()) {
                    while (rsChiusura.next()) {
                        Sondaggio sondaggio = ((Sondaggio) getSondaggio(rsChiusura.getInt("idSondaggio"))); //il sondaggio del result
                        for (int i = 0; i < sondaggi.size(); i++){
                            Sondaggio sondaggioList = (Sondaggio) sondaggi.get(i); //il sondaggio corrente in "sondaggi"
                            System.out.println("DATA CHIUSURA - : " + sondaggioList.getScadenza());
                            if(sondaggio.getKey() == sondaggioList.getKey()){
                                boolean add = true;
                                for (int j = 0; j < sondaggi.size(); j++){
                                    Sondaggio sondaggioCheck = (Sondaggio) sondaggiSearch.get(j);
                                    if(sondaggioCheck.getKey() == sondaggioList.getKey()){
                                        add = false;
                                    }
                                }
                                if(add){
                                    sondaggiSearch.add(sondaggioList);
                                }
                            } 
                        }
                    }
                }
            }

        }catch (SQLException ex) {
                throw new DataException("Unable to search sondaggio", ex);
        } catch (ParseException ex) {
            Logger.getLogger(SondaggioDAO_MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sondaggiSearch;
    }

    
    @Override
    public void deleteSondaggio(int idSondaggio) throws DataException {
        try {
            if (dataLayer.getCache().has(Sondaggio.class, idSondaggio)) {
            dataLayer.getCache().delete(Sondaggio.class, idSondaggio);
        }
            dSondaggio.setInt(1, idSondaggio);
            dSondaggio.execute();
        } catch (SQLException ex) {
            throw new DataException("Unable to delete Sondaggio By ID", ex);
        }
    }

    @Override
    public void updateCompilazioni(int idSondaggio) throws DataException {
        Sondaggio s = getSondaggio(idSondaggio);
        try {
            uSondaggioCompilazioni.setInt(1, s.getCompilazioni()+1);
            uSondaggioCompilazioni.setInt(2, idSondaggio);
            if (uSondaggioCompilazioni.executeUpdate() == 0) {
                throw new OptimisticLockException(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SondaggioDAO_MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
