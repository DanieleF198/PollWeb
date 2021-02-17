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
import com.mycompany.pollweb.model.Domanda;
import com.mycompany.pollweb.proxy.DomandaProxy;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.pollweb.data.OptimisticLockException;
import org.json.JSONObject;

/**
 *
 * @author Cronio
 */
public class DomandaDAO_MySQL extends DAO implements DomandaDAO  {
    
    private PreparedStatement sDomandaByID;
    private PreparedStatement sDomandaBySondaggioID;
    private PreparedStatement sDomandaBySondaggioIDAndPosition;
    private PreparedStatement sDomande;
    private PreparedStatement iDomanda;
    private PreparedStatement uDomanda;
    private PreparedStatement dDomanda;
    
    DomandaDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataException {
        try {
            super.init();
            
            sDomandaByID = connection.prepareStatement("SELECT * FROM Domanda WHERE idDomanda=?");
            sDomandaBySondaggioID = connection.prepareStatement("SELECT * FROM Domanda WHERE idSondaggio=?");
            sDomandaBySondaggioIDAndPosition = connection.prepareStatement("SELECT * FROM Domanda WHERE idSondaggio=? AND posizione=?");
            sDomande = connection.prepareStatement("SELECT * FROM Domanda");
            
            iDomanda = connection.prepareStatement("INSERT INTO Domanda (idSondaggio,titolo,obbligatoria,descrizione,posizione,opzioni,tipo,vincolo) VALUES(?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uDomanda = connection.prepareStatement("UPDATE Domanda SET idSondaggio=?,titolo=?,obbligatoria=?,descrizione=?,posizione=?,opzioni=?,tipo=?,vincolo=?,version=? WHERE idDomanda=? AND version=?");
            dDomanda = connection.prepareStatement("DELETE FROM Domanda WHERE idDomanda=?");
            
            
        } catch (SQLException ex) {
            throw new DataException("Error initializing PollWebdb data layer", ex);
        }
    }
    
    @Override
    public void destroy() throws DataException {
        //anche chiudere i PreparedStamenent � una buona pratica...
        try {
            
            sDomandaByID.close();
            sDomandaBySondaggioID.close();
            
            sDomande.close();
            
            iDomanda.close();
            uDomanda.close();
            dDomanda.close();
            
        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }
    
    @Override
    public DomandaProxy createDomanda() {
        return new DomandaProxy(getDataLayer());
    }
    
    //helper
    private DomandaProxy createDomanda(ResultSet rs) throws DataException {
        DomandaProxy d = createDomanda();
        try {
            d.setKey(rs.getInt("idDomanda"));
            d.setIdSondaggio(rs.getInt("idSondaggio"));
            d.setTitolo(rs.getString("titolo"));
            d.setObbligatoria(rs.getBoolean("obbligatoria"));
            d.setDescrizione(rs.getString("descrizione"));
            d.setPosizione(rs.getInt("posizione"));
            String jsonString = rs.getObject("opzioni").toString().replaceAll("\"", "\\\"");
            JSONObject opzioni = new JSONObject(jsonString);
            d.setOpzioni(opzioni);
            d.setTipo(rs.getString("tipo"));
            d.setVincolo(rs.getString("vincolo"));
            d.setVersion(rs.getLong("version"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create Domanda object form ResultSet", ex);
        }
        return d;
    }
    
    @Override
    public Domanda getDomanda(int idDomanda) throws DataException {
        Domanda d = null;
        //prima vediamo se l'oggetto è già stato caricato
        if (dataLayer.getCache().has(Domanda.class, idDomanda)) {
            d = dataLayer.getCache().get(Domanda.class, idDomanda);
        } else {
            //altrimenti lo carichiamo dal database
            try {
                sDomandaByID.setInt(1, idDomanda);
                try (ResultSet rs = sDomandaByID.executeQuery()) {
                    if (rs.next()) {
                        d = createDomanda(rs);
                        //e lo mettiamo anche nella cache
                        dataLayer.getCache().add(Domanda.class, d);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load Domanda by idDomanda", ex);
            }
        }
        return d;
    }
    
    public boolean checkDomanda(int idDomanda) throws DataException {
        boolean b = false;
        if (dataLayer.getCache().has(Domanda.class, idDomanda)) {
            b = true;
        }
        else{
            try {
                sDomandaByID.setInt(1, idDomanda);
                try (ResultSet rs = sDomandaByID.executeQuery()) {
                    if (rs.next()) {
                        b = true;
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load Domanda by idDomanda", ex);
            }
        }
        return b;
    }
    
    @Override
    public List<Domanda> getDomandaByIdSondaggio(int idSondaggio) throws DataException {
        List<Domanda> domande = new ArrayList();
            try {
                sDomandaBySondaggioID.setInt(1, idSondaggio);
                try (ResultSet rs = sDomandaBySondaggioID.executeQuery()) {
                    while (rs.next()) {
                        domande.add((Domanda)getDomanda(rs.getInt("idDomanda")));
                        
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load Domanda by idSondaggio", ex);
            }
        
        return domande;
    }
    
    @Override
    public Domanda getDomandaByIdSondaggioAndPosition(int idSondaggio, int posizione) throws DataException {
        Domanda d = null;
            try {
                sDomandaBySondaggioIDAndPosition.setInt(1, idSondaggio);
                sDomandaBySondaggioIDAndPosition.setInt(2, posizione);
                try (ResultSet rs = sDomandaBySondaggioIDAndPosition.executeQuery()) {
                    while (rs.next()) {
                        d = (Domanda)getDomanda(rs.getInt("idDomanda"));
                        d.setKey(rs.getInt("idDomanda"));
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load Domanda by idSondaggio", ex);
            }
        return d;
    }
    
    @Override
    public List<Domanda> getDomande() throws DataException {
        List<Domanda> result = new ArrayList();

        try (ResultSet rs = sDomande.executeQuery()) {
            while (rs.next()) {
                result.add((Domanda) getDomanda(rs.getInt("idDomanda")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Domande", ex);
        }
        return result;
    }

    @Override
    public void storeDomanda (Domanda domanda) throws DataException {
        try {
            if (domanda.getKey() != null && domanda.getKey() > 0) { //update
                //non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                //do not store the object if it is a proxy and does not indicate any modification
                if (domanda instanceof DataItemProxy && !((DataItemProxy) domanda).isModified()) {
                    return;
                }
                        
                
                uDomanda.setInt(1, domanda.getIdSondaggio());
                uDomanda.setString(2, domanda.getTitolo());
                uDomanda.setBoolean(3, domanda.isObbligatoria());
                uDomanda.setString(4, domanda.getDescrizione());
                uDomanda.setInt(5, domanda.getPosizione());
                if(domanda.getOpzioni() != null){
                    uDomanda.setObject(6, domanda.getOpzioni().toString());
                }else{
                    uDomanda.setObject(6, "{}");
                }
                uDomanda.setString(7, domanda.getTipo());
                uDomanda.setString(8, domanda.getVincolo());
                
                long currentVersion = domanda.getVersion();
                long nextVersion = currentVersion + 1;
                
                uDomanda.setLong(9, nextVersion);
                uDomanda.setInt(10, domanda.getKey());
                uDomanda.setLong(11, currentVersion);

                if (uDomanda.executeUpdate() == 0) {
                    throw new OptimisticLockException(domanda);
                }
                domanda.setVersion(nextVersion);
            }
            else { //insert
                iDomanda.setInt(1, domanda.getIdSondaggio());
                iDomanda.setString(2, domanda.getTitolo());
                iDomanda.setBoolean(3, domanda.isObbligatoria());
                iDomanda.setString(4, domanda.getDescrizione());
                iDomanda.setInt(5, domanda.getPosizione());
                if(domanda.getOpzioni() != null){
                    iDomanda.setObject(6, domanda.getOpzioni().toString());
                }else{
                    iDomanda.setObject(6, "{}");
                }
                iDomanda.setString(7, domanda.getTipo());
                iDomanda.setString(8, domanda.getVincolo());
                
                if (iDomanda.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iDomanda.getGeneratedKeys()) {
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
                            domanda.setKey(key);
                            //inseriamo il nuovo oggetto nella cache
                            //add the new object to the cache
                            dataLayer.getCache().add(Domanda.class, domanda);
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
            if (domanda instanceof DataItemProxy) {
                ((DataItemProxy) domanda).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store domanda", ex);
        }
    }
    
    @Override
    public void deleteDomanda(int idDomanda) throws DataException {
        try {
            if (dataLayer.getCache().has(Domanda.class, idDomanda)) {
            dataLayer.getCache().delete(Domanda.class, idDomanda);
        }
            dDomanda.setInt(1, idDomanda);
            dDomanda.execute();
        } catch (SQLException ex) {
            throw new DataException("Unable to delete Domanda By ID", ex);
        }
    }
    
}