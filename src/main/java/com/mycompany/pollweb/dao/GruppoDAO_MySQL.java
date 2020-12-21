/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.dao;

import com.mycompany.pollweb.data.DataLayer;

import com.mycompany.pollweb.data.DAO;
import com.mycompany.pollweb.data.DataException;
import com.mycompany.pollweb.data.DataItemProxy;
import com.mycompany.pollweb.model.Gruppo;
import com.mycompany.pollweb.proxy.GruppoProxy;
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
public class GruppoDAO_MySQL extends DAO implements GruppoDAO {
    
    private PreparedStatement sGruppoByID;
    private PreparedStatement sGruppi;
    private PreparedStatement iGruppo;
    private PreparedStatement uGruppo;
    private PreparedStatement dGruppo;
    
    
    
    public GruppoDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    
    @Override
    public void init() throws DataException {
        try {
            super.init();
            
            sGruppoByID = connection.prepareStatement("SELECT * FROM Gruppo WHERE idGruppo=?");
            sGruppi = connection.prepareStatement("SELECT * FROM Gruppo");
            
            iGruppo = connection.prepareStatement("INSERT INTO Gruppo (idGruppo, nomeGruppo) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            uGruppo = connection.prepareStatement("UPDATE Gruppo SET idGruppo=?, nomeGruppo=? WHERE idGruppo=?");
            dGruppo = connection.prepareStatement("DELETE FROM Gruppo WHERE idGruppo=?");
            
            
        } catch (SQLException ex) {
            throw new DataException("Error initializing PollWebdb data layer", ex);
        }
    }
    
    @Override
    public void destroy() throws DataException {
        try {

            sGruppoByID.close();

            sGruppi.close();
            iGruppo.close();
            uGruppo.close();

            dGruppo.close();

        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }
    
    @Override
    public GruppoProxy createGruppo(){
        return new GruppoProxy(getDataLayer());
    }
    
    //helper
    private GruppoProxy createGruppo(ResultSet rs) throws DataException {
        GruppoProxy g = createGruppo();
        try {
            g.setKey(rs.getInt("idGruppo"));
            g.setNomeGruppo(rs.getString("nomeGruppo"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create Gruppo object form ResultSet", ex);
        }
        return g;
    }
    
    @Override
    public Gruppo getGruppo(int idGruppo) throws DataException {
        Gruppo g = null;
        //prima vediamo se l'oggetto è già stato caricato
        if (dataLayer.getCache().has(Gruppo.class, idGruppo)) {
            g = dataLayer.getCache().get(Gruppo.class, idGruppo);
        } else {
            //altrimenti lo carichiamo dal database
            try {
                sGruppoByID.setInt(1, idGruppo);
                try (ResultSet rs = sGruppoByID.executeQuery()) {
                    if (rs.next()) {
                        g = createGruppo(rs);
                        //e lo mettiamo anche nella cache
                        dataLayer.getCache().add(Gruppo.class, g);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load Gruppo by idGruppo", ex);
            }
        }
        return g;
    }
    
    @Override
    public List<Gruppo> getGruppi() throws DataException {
        List<Gruppo> result = new ArrayList();

        try (ResultSet rs = sGruppi.executeQuery()) {
            while (rs.next()) {
                result.add((Gruppo) getGruppo(rs.getInt("idGruppo")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Gruppi", ex);
        }
        return result;
    }
    
    @Override
    public void storeGruppo (Gruppo gruppo) throws DataException {
        try {
            if (gruppo.getKey() != null && gruppo.getKey() > 0) { //update
                //non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                //do not store the object if it is a proxy and does not indicate any modification
                if (gruppo instanceof DataItemProxy && !((DataItemProxy) gruppo).isModified()) {
                    return;
                }
                uGruppo.setString(1, gruppo.getNomeGruppo());
                uGruppo.setInt(2, gruppo.getKey());


                if (uGruppo.executeUpdate() == 0) {
                    throw new OptimisticLockException(gruppo);
                }
            }
            else { //insert
                iGruppo.setString(1, gruppo.getNomeGruppo());
                
                if (iGruppo.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iGruppo.getGeneratedKeys()) {
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
                            gruppo.setKey(key);
                            //inseriamo il nuovo oggetto nella cache
                            //add the new object to the cache
                            dataLayer.getCache().add(Gruppo.class, gruppo);
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
            if (gruppo instanceof DataItemProxy) {
                ((DataItemProxy) gruppo).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store gruppo", ex);
        }
    }
    
}
