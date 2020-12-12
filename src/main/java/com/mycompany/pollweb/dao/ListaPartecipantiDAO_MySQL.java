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
import com.mycompany.pollweb.model.ListaPartecipanti;
import com.mycompany.pollweb.proxy.ListaPartecipantiProxy;
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
public class ListaPartecipantiDAO_MySQL extends DAO implements ListaPartecipantiDAO {
    
    private PreparedStatement sListaPartecipantiByID;
    private PreparedStatement sListaPartecipanti;
    private PreparedStatement iListaPartecipanti;
    private PreparedStatement uListaPartecipanti;
    private PreparedStatement dListaPartecipanti;
    
    ListaPartecipantiDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataException {
        try {
            super.init();
            
            sListaPartecipantiByID = connection.prepareStatement("SELECT * FROM ListaPartecipanti WHERE idListaPartecipanti=?");
            sListaPartecipanti = connection.prepareStatement("SELECT * FROM ListaPartecipanti");
            
            iListaPartecipanti = connection.prepareStatement("INSERT INTO ListaPartecipanti (idListaPartecipanti,idUtente,idSondaggio) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uListaPartecipanti = connection.prepareStatement("UPDATE ListaPartecipanti SET idListaPartecipanti=?,idUtente=?,idSondaggio=? WHERE idListaPartecipanti=?");
            dListaPartecipanti = connection.prepareStatement("DELETE FROM ListaPartecipanti WHERE idListaPartecipanti=?");
            
            
        } catch (SQLException ex) {
            throw new DataException("Error initializing PollWebdb data layer", ex);
        }
    }
    
    @Override
    public void destroy() throws DataException {
        //anche chiudere i PreparedStamenent � una buona pratica...
        try {
            
            sListaPartecipantiByID.close();
            
            sListaPartecipanti.close();
            
            iListaPartecipanti.close();
            uListaPartecipanti.close();
            dListaPartecipanti.close();
            
        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }
    
    @Override
    public ListaPartecipantiProxy createListaPartecipanti() {
        return new ListaPartecipantiProxy(getDataLayer());
    }
    
    //helper
    private ListaPartecipantiProxy createListaPartecipanti(ResultSet rs) throws DataException {
        ListaPartecipantiProxy lp = createListaPartecipanti();
        try {
            lp.setKey(rs.getInt("idListaPartecipanti"));
            lp.setIdUtente(rs.getInt("idUtente"));
            lp.setIdSondaggio(rs.getInt("idSondaggio"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create ListaPartecipanti object form ResultSet", ex);
        }
        return lp;
    }
    
    @Override
    public ListaPartecipanti getListaPartecipanti(int idListaPartecipanti) throws DataException {
        ListaPartecipanti lp = null;
        //prima vediamo se l'oggetto è già stato caricato
        if (dataLayer.getCache().has(ListaPartecipanti.class, idListaPartecipanti)) {
            lp = dataLayer.getCache().get(ListaPartecipanti.class, idListaPartecipanti);
        } else {
            //altrimenti lo carichiamo dal database
            try {
                sListaPartecipantiByID.setInt(1, idListaPartecipanti);
                try (ResultSet rs = sListaPartecipantiByID.executeQuery()) {
                    if (rs.next()) {
                        lp = createListaPartecipanti(rs);
                        //e lo mettiamo anche nella cache
                        dataLayer.getCache().add(ListaPartecipanti.class, lp);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load ListaPartecipanti by idListaPartecipanti", ex);
            }
        }
        return lp;
    }
    
    @Override
    public List<ListaPartecipanti> getListePartecipanti() throws DataException {
        List<ListaPartecipanti> result = new ArrayList();

        try (ResultSet rs = sListaPartecipanti.executeQuery()) {
            while (rs.next()) {
                result.add((ListaPartecipanti) getListaPartecipanti(rs.getInt("idListaPartecipanti")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load ListePartecipanti", ex);
        }
        return result;
    }
    
    
    @Override
    public void storeListaPartecipanti (ListaPartecipanti listaPartecipanti) throws DataException {
        try {
            if (listaPartecipanti.getKey() != null && listaPartecipanti.getKey() > 0) { //update
                //non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                //do not store the object if it is a proxy and does not indicate any modification
                if (listaPartecipanti instanceof DataItemProxy && !((DataItemProxy) listaPartecipanti).isModified()) {
                    return;
                }
                uListaPartecipanti.setInt(1, listaPartecipanti.getIdUtente()); //TODO aggiungere lista di domande
                uListaPartecipanti.setInt(2, listaPartecipanti.getIdSondaggio());      
                uListaPartecipanti.setInt(3, listaPartecipanti.getKey());

                if (uListaPartecipanti.executeUpdate() == 0) {
                    throw new OptimisticLockException(listaPartecipanti);
                }
            }
            else { //insert
                iListaPartecipanti.setInt(1, listaPartecipanti.getIdUtente()); //TODO aggiungere lista di domande
                iListaPartecipanti.setInt(2, listaPartecipanti.getIdSondaggio());      
                iListaPartecipanti.setInt(3, listaPartecipanti.getKey());
                
                if (iListaPartecipanti.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iListaPartecipanti.getGeneratedKeys()) {
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
                            listaPartecipanti.setKey(key);
                            //inseriamo il nuovo oggetto nella cache
                            //add the new object to the cache
                            dataLayer.getCache().add(ListaPartecipanti.class, listaPartecipanti);
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
            if (listaPartecipanti instanceof DataItemProxy) {
                ((DataItemProxy) listaPartecipanti).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store listaPartecipanti", ex);
        }
    }
    
}
