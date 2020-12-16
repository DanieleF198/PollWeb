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
import javax.persistence.OptimisticLockException;

/**
 *
 * @author Cronio
 */
public class SondaggioDAO_MySQL extends DAO implements SondaggioDAO {
    
    private PreparedStatement sSondaggioByID;
    private PreparedStatement sSondaggioByIDUtente;
    private PreparedStatement sSondaggi;
    private PreparedStatement iSondaggio;
    private PreparedStatement uSondaggio;
    private PreparedStatement dSondaggio;
    
    SondaggioDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataException {
        try {
            super.init();
            
            sSondaggioByID = connection.prepareStatement("SELECT * FROM Sondaggio WHERE idSondaggio=?");
            sSondaggioByIDUtente = connection.prepareStatement("SELECT * FROM Sondaggio WHERE idUtente=?");
            sSondaggi = connection.prepareStatement("SELECT * FROM Sondaggio");
            
            iSondaggio = connection.prepareStatement("INSERT INTO Sondaggio (idSondaggio,idUtente,testoApertura,testoChiusura,stato,quiz,visibilita,dataCreazione,dataChiusura) VALUES(?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uSondaggio = connection.prepareStatement("UPDATE Sondaggio SET idSondaggio=?,idUtente=?,testoApertura=?,testoChiusura=?,stato=?,quiz=?,visibilita=?,dataCreazione=?,dataChiusura=? WHERE idSondaggio=?");
            dSondaggio = connection.prepareStatement("DELETE FROM Sondaggio WHERE idSondaggio=?");
            
            
        } catch (SQLException ex) {
            throw new DataException("Error initializing PollWebdb data layer", ex);
        }
    }
    
    @Override
    public void destroy() throws DataException {
        //anche chiudere i PreparedStamenent � una buona pratica...
        try {
            
            sSondaggioByID.close();
            sSondaggioByIDUtente.close();
            
            sSondaggi.close();
            
            iSondaggio.close();
            uSondaggio.close();
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
            s.setQuiz(rs.getBoolean("quiz"));
            s.setStato(rs.getInt("stato"));
            s.setVisibilita(rs.getBoolean("visibilita"));
            s.setTestoApertura(rs.getString("testoApertura"));
            s.setTestoChiusura(rs.getString("testoChiusura"));
            s.setCreazione(rs.getDate("dataCreazione"));
            s.setScadenza(rs.getDate("dataScadenza"));
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
    public void storeSondaggio (Sondaggio sondaggio) throws DataException {
        try {
            if (sondaggio.getKey() != null && sondaggio.getKey() > 0) { //update
                //non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                //do not store the object if it is a proxy and does not indicate any modification
                if (sondaggio instanceof DataItemProxy && !((DataItemProxy) sondaggio).isModified()) {
                    return;
                }
                uSondaggio.setString(1, sondaggio.getTestoApertura()); //TODO aggiungere lista di domande
                uSondaggio.setString(2, sondaggio.getTestoChiusura());
                uSondaggio.setInt(3, sondaggio.getIdUtente());
                uSondaggio.setBoolean(4, sondaggio.isQuiz());
                uSondaggio.setInt(5, sondaggio.getStato());
                uSondaggio.setBoolean(6, sondaggio.isVisibilita());
                uSondaggio.setDate(7, (Date) sondaggio.getCreazione());
                uSondaggio.setDate(8, (Date) sondaggio.getScadenza());
                uSondaggio.setInt(9, sondaggio.getKey());

                if (uSondaggio.executeUpdate() == 0) {
                    throw new OptimisticLockException(sondaggio);
                }
            }
            else { //insert
                iSondaggio.setString(1, sondaggio.getTestoApertura()); //TODO aggiungere lista di domande
                iSondaggio.setString(2, sondaggio.getTestoChiusura());
                iSondaggio.setInt(3, sondaggio.getIdUtente());
                iSondaggio.setBoolean(4, sondaggio.isQuiz());
                iSondaggio.setInt(5, sondaggio.getStato());
                iSondaggio.setBoolean(6, sondaggio.isVisibilita());
                iSondaggio.setDate(7, (Date) sondaggio.getCreazione());
                iSondaggio.setDate(8, (Date) sondaggio.getScadenza());
                
                if (iSondaggio.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iSondaggio.getGeneratedKeys()) {
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
                            sondaggio.setKey(key);
                            //inseriamo il nuovo oggetto nella cache
                            //add the new object to the cache
                            dataLayer.getCache().add(Sondaggio.class, sondaggio);
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
            if (sondaggio instanceof DataItemProxy) {
                ((DataItemProxy) sondaggio).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store sondaggio", ex);
        }
    }
    
}
