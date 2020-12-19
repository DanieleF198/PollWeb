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
import com.mycompany.pollweb.model.Utente;
import com.mycompany.pollweb.proxy.UtenteProxy;
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
public class UtenteDAO_MySQL extends DAO implements UtenteDAO  {
    
    private PreparedStatement sUtenteByID;
    private PreparedStatement sUtenteByGruppo;
    private PreparedStatement sUtenteByemail;
    private PreparedStatement sUtenti;
    private PreparedStatement iUtente;
    private PreparedStatement uUtente;
    private PreparedStatement dUtente;

    UtenteDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataException {
        try {
            super.init();

            sUtenteByID = connection.prepareStatement("SELECT * FROM Utente WHERE idUtente=?");
            sUtenteByGruppo = connection.prepareStatement("SELECT * FROM Utente WHERE idGruppo=?");
            sUtenteByemail = connection.prepareStatement("SELECT * FROM Utente WHERE email=?");
            sUtenti = connection.prepareStatement("SELECT * FROM Utente");   
            
            iUtente = connection.prepareStatement("INSERT INTO Utente (idGruppo,nome,cognome,username,password,email,eta) VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uUtente = connection.prepareStatement("UPDATE Utente SET idGruppo=?,nome=?,cognome=?,password=?,username=?,email=?,eta=? WHERE idUtente=?");
            dUtente = connection.prepareStatement("DELETE FROM Utente WHERE idUtente=?");

            
        } catch (SQLException ex) {
            throw new DataException("Error initializing PollWebdb data layer", ex);
        }
    }
    
    @Override
    public void destroy() throws DataException {
        //anche chiudere i PreparedStamenent � una buona pratica...
        try {
            
            sUtenteByID.close();
            
            sUtenteByGruppo.close();
            sUtenteByemail.close();
            sUtenti.close();
            
            iUtente.close();
            uUtente.close();
            dUtente.close();
            
        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }
    
    @Override
    public UtenteProxy createUtente() {
        return new UtenteProxy(getDataLayer());
    }

    //helper
    private UtenteProxy createUtente(ResultSet rs) throws DataException {
        UtenteProxy u = createUtente();
        try {
            u.setKey(rs.getInt("idUtente"));
            u.setNome(rs.getString("nome"));
            u.setPassword(rs.getString("password"));
            u.setIdGruppo(rs.getInt("idGruppo"));
            u.setEta(rs.getInt("eta"));
            u.setCognome(rs.getString("cognome"));
            u.setUsername(rs.getString("username"));
            u.setEmail(rs.getString("email"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create Utente object form ResultSet", ex);
        }
        return u;
    }
    
    @Override
    public Utente getUtente(int idUtente) throws DataException {
        Utente u = null;
        //prima vediamo se l'oggetto è già stato caricato
        if (dataLayer.getCache().has(Utente.class, idUtente)) {
            u = dataLayer.getCache().get(Utente.class, idUtente);
        } else {
            //altrimenti lo carichiamo dal database
            try {
                sUtenteByID.setInt(1, idUtente);
                try (ResultSet rs = sUtenteByID.executeQuery()) {
                    if (rs.next()) {
                        u = createUtente(rs);
                        //e lo mettiamo anche nella cache
                        dataLayer.getCache().add(Utente.class, u);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load Utente by idUtente", ex);
            }
        }
        return u;
    }
    
    @Override
    public List<Utente> getUtenti() throws DataException {
        List<Utente> result = new ArrayList();

        try (ResultSet rs = sUtenti.executeQuery()) {
            while (rs.next()) {
                result.add((Utente) getUtente(rs.getInt("idUtente")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Utenti", ex);
        }
        return result;
    }
    
    @Override
    public void storeUtente (Utente utente) throws DataException {
        try {
            if (utente.getKey() != null && utente.getKey() > 0) { //update
                //non facciamo nulla se l'oggetto è un proxy e indica di non aver subito modifiche
                //do not store the object if it is a proxy and does not indicate any modification
                if (utente instanceof DataItemProxy && !((DataItemProxy) utente).isModified()) {
                    return;
                }
                uUtente.setString(1, utente.getNome());
                uUtente.setString(2, utente.getPassword());
                uUtente.setInt(3, utente.getEta());
                uUtente.setString(4, utente.getEmail());
                uUtente.setInt(5, utente.getIdGruppo());
                uUtente.setString(6, utente.getCognome());
                uUtente.setString(7, utente.getUsername());
                uUtente.setInt(8, utente.getKey());


                if (uUtente.executeUpdate() == 0) {
                    throw new OptimisticLockException(utente);
                }
            }
            else { //insert
                iUtente.setString(1, utente.getNome());
                iUtente.setString(2, utente.getPassword());
                iUtente.setInt(3, utente.getEta());
                iUtente.setString(4, utente.getEmail());
                iUtente.setInt(5, utente.getIdGruppo());
                iUtente.setString(6, utente.getCognome());
                iUtente.setString(7, utente.getUsername());
                
                if (iUtente.executeUpdate() == 1) {
                    //per leggere la chiave generata dal database
                    //per il record appena inserito, usiamo il metodo
                    //getGeneratedKeys sullo statement.
                    //to read the generated record key from the database
                    //we use the getGeneratedKeys method on the same statement
                    try (ResultSet keys = iUtente.getGeneratedKeys()) {
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
                            utente.setKey(key);
                            //inseriamo il nuovo oggetto nella cache
                            //add the new object to the cache
                            dataLayer.getCache().add(Utente.class, utente);
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
            if (utente instanceof DataItemProxy) {
                ((DataItemProxy) utente).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store utente", ex);
        }
    }
    
}
