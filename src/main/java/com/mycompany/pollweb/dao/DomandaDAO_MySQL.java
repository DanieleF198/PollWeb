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
import javax.persistence.OptimisticLockException;
import org.json.JSONObject;

/**
 *
 * @author Cronio
 */
public class DomandaDAO_MySQL extends DAO implements DomandaDAO  {
    
    private PreparedStatement sDomandaByID;
    private PreparedStatement sDomandaBySondaggioID;
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
            sDomande = connection.prepareStatement("SELECT * FROM Domanda");
            
            iDomanda = connection.prepareStatement("INSERT INTO Domanda (idDomanda,idSondaggio,titolo,obbligatoria,descrizione,posizione,opzioni,rispostaCorretta,tipo) VALUES(?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uDomanda = connection.prepareStatement("UPDATE Domanda SET idDomanda=?,idSondaggio=?,titolo=?,obbligatoria=?,stadescrizioneto=?,posizione=?,opzioni=?,rispostaCorretta=?,tipo=? WHERE idDomanda=?");
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
            d.setKey(rs.getInt("idDomanda")); //TODO add opzioni - rispostaCorretta
            d.setIdSondaggio(rs.getInt("idSondaggio"));
            d.setTitolo(rs.getString("titolo"));
            d.setObbligatoria(rs.getBoolean("obbligatoria"));
            d.setDescrizione(rs.getString("descrizione"));
            JSONObject opzioni = (JSONObject) rs.getObject("opzioni");
            d.setOpzioni(opzioni);
            JSONObject rispostaCorretta = (JSONObject) rs.getObject("rispostaCorretta");
            d.setRispostaCorretta(rispostaCorretta);
            d.setPosizione(rs.getInt("posizione"));
            d.setTipo(rs.getString("tipo"));
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
    
    @Override
    public Domanda getDomandaByIdSondaggio(int idSondaggio) throws DataException {
        Domanda d = null;
        //prima vediamo se l'oggetto è già stato caricato
        if (dataLayer.getCache().has(Domanda.class, idSondaggio)) {
            d = dataLayer.getCache().get(Domanda.class, idSondaggio);
        } else {
            //altrimenti lo carichiamo dal database
            try {
                sDomandaBySondaggioID.setInt(1, idSondaggio);
                try (ResultSet rs = sDomandaBySondaggioID.executeQuery()) {
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
                        
                
                uDomanda.setInt(1, domanda.getIdSondaggio());  //TODO aggiungere i JSON
                uDomanda.setString(2, domanda.getTitolo());
                uDomanda.setBoolean(3, domanda.isObbligatoria());
                uDomanda.setString(4, domanda.getDescrizione());
                uDomanda.setInt(5, domanda.getPosizione());
                uDomanda.setString(6, domanda.getTipo());
                uDomanda.setObject(7, domanda.getOpzioni());
                uDomanda.setObject(8, domanda.getRispostaCorretta());
                uDomanda.setInt(9, domanda.getKey());

                if (uDomanda.executeUpdate() == 0) {
                    throw new OptimisticLockException(domanda);
                }
            }
            else { //insert
                iDomanda.setInt(1, domanda.getIdSondaggio());
                iDomanda.setString(2, domanda.getTitolo());
                iDomanda.setBoolean(3, domanda.isObbligatoria());
                iDomanda.setString(4, domanda.getDescrizione());
                iDomanda.setInt(5, domanda.getPosizione());
                iDomanda.setString(6, domanda.getTipo());
                iDomanda.setObject(7, domanda.getOpzioni());
                iDomanda.setObject(8, domanda.getRispostaCorretta());
                
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
    
}