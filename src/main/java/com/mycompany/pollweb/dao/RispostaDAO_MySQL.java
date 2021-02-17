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
import com.mycompany.pollweb.model.Risposta;
import com.mycompany.pollweb.proxy.RispostaProxy;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.pollweb.data.OptimisticLockException;

/**
 *
 * @author Cronio
 */
public class RispostaDAO_MySQL extends DAO implements RispostaDAO {
    
    private PreparedStatement sRispostaByID;
    private PreparedStatement sRispostaByIDUtente;
    private PreparedStatement sRispostaByIPUtente;
    private PreparedStatement sRisposte;
    private PreparedStatement iRispostaUserReg;
    private PreparedStatement uRispostaUserReg;
    private PreparedStatement iRispostaUserNotReg;
    private PreparedStatement uRispostaUserNotReg;
    private PreparedStatement dRisposta;
    
    RispostaDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataException {
        try {
            super.init();
            
            sRispostaByID = connection.prepareStatement("SELECT * FROM Risposta WHERE idRisposta=?");
            sRispostaByIDUtente = connection.prepareStatement("SELECT * FROM Risposta WHERE idUtente=?");
            sRispostaByIPUtente = connection.prepareStatement("SELECT * FROM Risposta WHERE ipUtenteRisposta=? AND usernameUtenteRisposta=?");
            sRisposte = connection.prepareStatement("SELECT * FROM Risposta");
            
            iRispostaUserReg = connection.prepareStatement("INSERT INTO Risposta (idUtente,dataCreazione,usernameUtenteRisposta,ipUtenteRisposta) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            uRispostaUserReg = connection.prepareStatement("UPDATE Risposta SET idUtente=?,dataCreazione=?,usernameUtenteRisposta=?,ipUtenteRisposta=?,version=? WHERE idRisposta=? AND version=?");
            iRispostaUserNotReg = connection.prepareStatement("INSERT INTO Risposta (dataCreazione,ipUtenteRisposta) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            uRispostaUserNotReg = connection.prepareStatement("UPDATE Risposta SET dataCreazione=?,ipUtenteRisposta=? WHERE idRisposta=?"); //chiedere a dan

            dRisposta = connection.prepareStatement("DELETE FROM Risposta WHERE idRisposta=?");
            
            
        } catch (SQLException ex) {
            throw new DataException("Error initializing PollWebdb data layer", ex);
        }
    }
    
    @Override
    public void destroy() throws DataException {
        
        try {
            
            sRispostaByID.close();
            sRispostaByIDUtente.close();
            sRispostaByIPUtente.close();
            
            sRisposte.close();
            
            iRispostaUserReg.close();
            uRispostaUserReg.close();
            dRisposta.close();
            
        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }
    
    @Override
    public RispostaProxy createRisposta() {
        return new RispostaProxy(getDataLayer());
    }
    
    //helper
    private RispostaProxy createRisposta(ResultSet rs) throws DataException {
        RispostaProxy r = createRisposta();
        try {
            r.setKey(rs.getInt("idRisposta"));
            r.setIdUtente(rs.getInt("idUtente"));
            r.setData(rs.getDate("dataCreazione"));
            r.setUsernameUtenteRisposta(rs.getString("usernameUtenteRisposta"));
            r.setIpUtenteRisposta(rs.getString("ipUtenteRisposta"));
            r.setVersion(rs.getLong("version"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create Risposta object form ResultSet", ex);
        }
        return r;
    }
    
    @Override
    public Risposta getRisposta(int idRisposta) throws DataException {
        Risposta r = null;
        //prima vediamo se l'oggetto è già stato caricato
        if (dataLayer.getCache().has(Risposta.class, idRisposta)) {
            r = dataLayer.getCache().get(Risposta.class, idRisposta);
        } else {
            //altrimenti lo carichiamo dal database
            try {
                sRispostaByID.setInt(1, idRisposta);
                try (ResultSet rs = sRispostaByID.executeQuery()) {
                    if (rs.next()) {
                        r = createRisposta(rs);
                        //e lo mettiamo anche nella cache
                        dataLayer.getCache().add(Risposta.class, r);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load Risposta by idRisposta", ex);
            }
        }
        return r;
    }
    
    @Override
    public List<Risposta> getRispostaByIdUtente(int idUtente) throws DataException {
        List<Risposta> result = new ArrayList();
            //altrimenti lo carichiamo dal database
            try {
                sRispostaByIDUtente.setInt(1, idUtente);
                try (ResultSet rs = sRispostaByIDUtente.executeQuery()) {
                    while (rs.next()) {
                        result.add((Risposta) getRisposta(rs.getInt("idRisposta")));
                    }   
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load Risposta by idRisposta", ex);
            }
        return result;
    }
    
    @Override
    public List<Risposta> getRispostaByIPUtente(String ipUtente) throws DataException {
        List<Risposta> result = new ArrayList();
            //altrimenti lo carichiamo dal database
            try {
                sRispostaByIPUtente.setString(1, ipUtente);
                sRispostaByIPUtente.setString(2, "no!username");
                try (ResultSet rs = sRispostaByIPUtente.executeQuery()) {
                    while (rs.next()) {
                        result.add((Risposta) getRisposta(rs.getInt("idRisposta")));
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load Risposta by idRisposta", ex);
            }
        return result;
    }
    
    @Override
    public List<Risposta> getRisposte() throws DataException {
        List<Risposta> result = new ArrayList();

        try (ResultSet rs = sRisposte.executeQuery()) {
            while (rs.next()) {
                result.add((Risposta) getRisposta(rs.getInt("idRisposta")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Risposte", ex);
        }
        return result;
    }
    
    @Override
    public int storeRispostaUserReg (Risposta risposta) throws DataException {
        try {
            if (risposta.getKey() != null && risposta.getKey() > 0) { //update
                //non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                //do not store the object if it is a proxy and does not indicate any modification
                if (risposta instanceof DataItemProxy && !((DataItemProxy) risposta).isModified()) {
                    return risposta.getKey();
                }
                java.sql.Date sqlCreazione = new java.sql.Date( risposta.getData().getTime() );
                uRispostaUserReg.setInt(1, risposta.getIdUtente());
                uRispostaUserReg.setDate(2, sqlCreazione);
                uRispostaUserReg.setString(3, risposta.getUsernameUtenteRisposta());
                uRispostaUserReg.setString(4, risposta.getIpUtenteRisposta());
                
                long currentVersion = risposta.getVersion();
                long nextVersion = currentVersion + 1;
                
                uRispostaUserReg.setLong(5, nextVersion);
                uRispostaUserReg.setInt(6, risposta.getKey());
                uRispostaUserReg.setLong(7, currentVersion);

                if (uRispostaUserReg.executeUpdate() == 0) {
                    throw new OptimisticLockException(risposta);
                }
                risposta.setVersion(nextVersion);
            }
            else { //insert
                java.sql.Date sqlCreazione = new java.sql.Date( risposta.getData().getTime() );
                iRispostaUserReg.setInt(1, risposta.getIdUtente());
                iRispostaUserReg.setDate(2, sqlCreazione);
                iRispostaUserReg.setString(3, risposta.getUsernameUtenteRisposta());
                iRispostaUserReg.setString(4, risposta.getIpUtenteRisposta());
                
                if (iRispostaUserReg.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iRispostaUserReg.getGeneratedKeys()) {
                        //il valore restituito è un ResultSet con un record
                        //per ciascuna chiave generata (uno solo nel nostro caso)
                        //the returned value is a ResultSet with a distinct record for
                        //each generated key (only one in our case)
                        if (keys.next()) {
                            //i campi del record sono le componenti della chiave
                            //(nel nostro caso, un solo intero)
                            //the record fields are the key componenets
                            //(a single integer in our case)
                            int key = keys.getInt(1);
                            //aggiornaimo la chiave in caso di inserimento
                            //after an insert, uopdate the object key
                            risposta.setKey(key);
                            //inseriamo il nuovo oggetto nella cache
                            //add the new object to the cache
                            dataLayer.getCache().add(Risposta.class, risposta);
                        }
                    }
                }
            }

//            //se possibile, restituiamo l'oggetto appena inserito RICARICATO
//            //dal database tramite le API del modello. In tal
//            //modo terremo conto di ogni modifica apportata
//            //durante la fase di inserimento
//            //if possible, we return the just-inserted object RELOADED from the
//            //database through our API. In this way, the resulting
//            //object will ambed any data correction performed by
//            //the DBMS
//            if (key > 0) {
//                gruppo.copyFrom(getGruppo(key));
//            }
            //se abbiamo un proxy, resettiamo il suo attributo dirty
            //if we have a proxy, reset its dirty attribute
            if (risposta instanceof DataItemProxy) {
                ((DataItemProxy) risposta).setModified(false);
            }
            return risposta.getKey();
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store risposta", ex);
        }
    }
    
    @Override
    public int storeRispostaUserNotReg (Risposta risposta) throws DataException {
        try {
            if (risposta.getKey() != null && risposta.getKey() > 0) { //update
                //non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                //do not store the object if it is a proxy and does not indicate any modification
                if (risposta instanceof DataItemProxy && !((DataItemProxy) risposta).isModified()) {
                    return risposta.getKey();
                }
                java.sql.Date sqlCreazione = new java.sql.Date( risposta.getData().getTime() );
                uRispostaUserNotReg.setDate(1, sqlCreazione);
                uRispostaUserNotReg.setString(2, risposta.getIpUtenteRisposta());
                
                long currentVersion = risposta.getVersion();
                long nextVersion = currentVersion + 1;
                
                uRispostaUserNotReg.setLong(3, nextVersion);
                uRispostaUserNotReg.setInt(4, risposta.getKey());
                uRispostaUserNotReg.setLong(5, currentVersion);
                

                if (uRispostaUserNotReg.executeUpdate() == 0) {
                    throw new OptimisticLockException(risposta);
                }
                risposta.setVersion(nextVersion);
            }
            else { //insert
                java.sql.Date sqlCreazione = new java.sql.Date( risposta.getData().getTime() );
                iRispostaUserNotReg.setDate(1, sqlCreazione);
                iRispostaUserNotReg.setString(2, "no!username"); //siamo sicuri che questo username non esista perché non ammettiamo username con il "!"
                iRispostaUserNotReg.setString(3, risposta.getIpUtenteRisposta());
                
                if (iRispostaUserNotReg.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iRispostaUserNotReg.getGeneratedKeys()) {
                        //il valore restituito è un ResultSet con un record
                        //per ciascuna chiave generata (uno solo nel nostro caso)
                        //the returned value is a ResultSet with a distinct record for
                        //each generated key (only one in our case)
                        if (keys.next()) {
                            //i campi del record sono le componenti della chiave
                            //(nel nostro caso, un solo intero)
                            //the record fields are the key componenets
                            //(a single integer in our case)
                            int key = keys.getInt(1);
                            //aggiornaimo la chiave in caso di inserimento
                            //after an insert, uopdate the object key
                            risposta.setKey(key);
                            //inseriamo il nuovo oggetto nella cache
                            //add the new object to the cache
                            dataLayer.getCache().add(Risposta.class, risposta);
                        }
                    }
                }
            }

//            //se possibile, restituiamo l'oggetto appena inserito RICARICATO
//            //dal database tramite le API del modello. In tal
//            //modo terremo conto di ogni modifica apportata
//            //durante la fase di inserimento
//            //if possible, we return the just-inserted object RELOADED from the
//            //database through our API. In this way, the resulting
//            //object will ambed any data correction performed by
//            //the DBMS
//            if (key > 0) {
//                gruppo.copyFrom(getGruppo(key));
//            }
            //se abbiamo un proxy, resettiamo il suo attributo dirty
            //if we have a proxy, reset its dirty attribute
            if (risposta instanceof DataItemProxy) {
                ((DataItemProxy) risposta).setModified(false);
            }
            return risposta.getKey();
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store risposta", ex);
        }
    }
    
}
