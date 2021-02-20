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
import com.mycompany.pollweb.data.OptimisticLockException;
import com.mycompany.pollweb.impl.UtenteImpl;
import com.mycompany.pollweb.model.Utente;
import com.mycompany.pollweb.proxy.UtenteProxy;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cronio
 */
public class UtenteDAO_MySQL extends DAO implements UtenteDAO  {
    
    private PreparedStatement sUtenteByID;
    private PreparedStatement sUtenteByGruppo;
    private PreparedStatement sUtenteLogin;
    private PreparedStatement sUtenteEmail;
    private PreparedStatement sPartecipanteLogin;
    private PreparedStatement sUtenteExistByUsername;
    private PreparedStatement sUtenteExistByEmail;
    private PreparedStatement sUtenti;
    private PreparedStatement iUtente;
    private PreparedStatement uUtente;
    private PreparedStatement dUtente;
    private PreparedStatement iUtenteListaPartecipanti;
    private PreparedStatement iUtenteListaPartecipanti2;
    private PreparedStatement uUtenteListaPartecipantiScaduto;
    private PreparedStatement uUtenteListaPartecipanti;
    private PreparedStatement uPartecipanteSetScaduto;
    private PreparedStatement sPartecipanteCheckScaduto;
    private PreparedStatement uUtenteListaPartecipantiSendEmail;
    private PreparedStatement sListaPartecipantiBySondaggio;
    private PreparedStatement sListaPartecipantiWithMailToSendBySondaggio;
    private PreparedStatement sListaPartecipantiBySondaggioAll;
    private PreparedStatement dListaPartecipanti;
    private PreparedStatement sListaPartecipantiEmailSondaggio;

    UtenteDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataException {
        try {
            super.init();

            sUtenteByID = connection.prepareStatement("SELECT * FROM Utente WHERE idUtente=?");
            sUtenteByGruppo = connection.prepareStatement("SELECT * FROM Utente WHERE idGruppo=?");
            sUtenteLogin = connection.prepareStatement("SELECT * FROM Utente WHERE username=? and password=?");
            sPartecipanteLogin = connection.prepareStatement("SELECT * FROM ListaPartecipanti WHERE email=? and password=? and scaduto=?");
            sUtenteExistByUsername = connection.prepareStatement("SELECT * FROM Utente WHERE username=?");
            sUtenteExistByEmail = connection.prepareStatement("SELECT * FROM Utente WHERE email=?");
            sUtenti = connection.prepareStatement("SELECT * FROM Utente");   
            
            iUtente = connection.prepareStatement("INSERT INTO Utente (idGruppo,nome,cognome,dataNascita,username,password,email,bloccato) VALUES(?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uUtente = connection.prepareStatement("UPDATE Utente SET idGruppo=?,nome=?,cognome=?,dataNascita=?,username=?,password=?,email=?,bloccato=?,version=? WHERE idUtente=? AND version=?");
            dUtente = connection.prepareStatement("DELETE FROM Utente WHERE idUtente=?");
            
            iUtenteListaPartecipanti = connection.prepareStatement("INSERT INTO ListaPartecipanti (idSondaggio,nome,email,password,scaduto,emailMandata) VALUES(?,?,?,?,?,?)");
            iUtenteListaPartecipanti2 = connection.prepareStatement("INSERT INTO ListaPartecipanti (idSondaggio,idUtente,nome,email,password,scaduto,emailMandata) VALUES(?,?,?,?,?,?,?)");
            uUtenteListaPartecipantiScaduto = connection.prepareStatement("UPDATE ListaPartecipanti SET scaduto=?, version=? WHERE idSondaggio=? AND email=? AND version=?");
            uUtenteListaPartecipanti = connection.prepareStatement("UPDATE ListaPartecipanti SET nome=?, password=?, scaduto=?, version=? WHERE idSondaggio=? AND email=? AND version=?");
            uPartecipanteSetScaduto = connection.prepareStatement("UPDATE ListaPartecipanti SET scaduto=?, version=? WHERE idSondaggio=? AND email=? AND version=?");
            sPartecipanteCheckScaduto = connection.prepareStatement("SELECT * FROM ListaPartecipanti WHERE idSondaggio=? AND email=?");
            uUtenteListaPartecipantiSendEmail = connection.prepareStatement("UPDATE ListaPartecipanti SET emailMandata=?, version=? WHERE idSondaggio=? AND scaduto=? AND version=?");
            sListaPartecipantiBySondaggio = connection.prepareStatement("SELECT * FROM ListaPartecipanti WHERE idSondaggio=? AND scaduto=?");
            sListaPartecipantiWithMailToSendBySondaggio = connection.prepareStatement("SELECT * FROM ListaPartecipanti WHERE idSondaggio=? AND emailMandata=? AND scaduto=?");
            sListaPartecipantiBySondaggioAll = connection.prepareStatement("SELECT * FROM ListaPartecipanti WHERE idSondaggio=?");
            dListaPartecipanti = connection.prepareStatement("DELETE FROM ListaPartecipanti WHERE idSondaggio=?");
            
            sListaPartecipantiEmailSondaggio = connection.prepareStatement("SELECT * FROM ListaPartecipanti WHERE email=? AND idSondaggio=?");
            
        } catch (SQLException ex) {
            throw new DataException("Error initializing PollWebdb data layer", ex);
        }
    }
    
    @Override
    public void destroy() throws DataException {
        try {
            
            sUtenteByID.close();
            
            sUtenteByGruppo.close();
            sUtenteLogin.close();
            sPartecipanteLogin.close();
            sUtenteExistByUsername.close();
            sUtenteExistByEmail.close();
            sUtenti.close();
            iUtenteListaPartecipanti.close();
            iUtenteListaPartecipanti2.close();
            uPartecipanteSetScaduto.close();
            sPartecipanteCheckScaduto.close();
            sListaPartecipantiEmailSondaggio.close();
            
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
            u.setIdGruppo(rs.getInt("idGruppo"));
            u.setNome(rs.getString("nome"));
            u.setCognome(rs.getString("cognome"));
            u.setDataNascita(rs.getDate("dataNascita"));
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            u.setEmail(rs.getString("email"));
            u.setBloccato(rs.getBoolean("bloccato"));
            u.setVersion(rs.getLong("version"));
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
    public void banUtente(int idUtente) throws DataException {
        Utente u = null;
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
    
    @Override
    public ArrayList<Utente> getUtenti() throws DataException {
        ArrayList<Utente> result = new ArrayList();

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
    public Utente getUtenteLogin(String username, String password) throws DataException {
        Utente u = null;
        try {
                sUtenteLogin.setString(1, username);
                sUtenteLogin.setString(2, password);
                try (ResultSet rs = sUtenteLogin.executeQuery()) {
                    if (rs.next()) {
                        u = createUtente(rs);
                        //e lo mettiamo anche nella cache
                        dataLayer.getCache().add(Utente.class, u);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load Utente by idUtente", ex);
            }
        return u;
    }
    
    @Override
    public HashMap<Integer, Utente> getPartecipanteLogin(String email, String password) throws DataException {
        HashMap<Integer, Utente> result = new HashMap<Integer,Utente>();
        try {
                sPartecipanteLogin.setString(1, email);
                sPartecipanteLogin.setString(2, password);
                sPartecipanteLogin.setBoolean(3, false);
                try (ResultSet rs = sPartecipanteLogin.executeQuery()) {
                    while (rs.next()) {
                        result.put(rs.getInt("idSondaggio"),(Utente) getUtente(rs.getInt("idUtente")));
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load Utente by idUtente", ex);
            }
        return result;
    }
    
    @Override
    public String checkIfExist(String username, String email) throws DataException {
        String usernameExist = "";
        String emailExist = "";
        try {
            sUtenteExistByUsername.setString(1, username);
            sUtenteExistByEmail.setString(1, email);
            try (ResultSet rs = sUtenteExistByUsername.executeQuery(); ResultSet rs2 = sUtenteExistByEmail.executeQuery()) {
                if (rs.next()) {
                    usernameExist = "username";
                }
                if (rs2.next()) {
                    emailExist = "email";
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAO_MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }  
        if(!usernameExist.isEmpty() && !emailExist.isEmpty()){
            return usernameExist.concat(emailExist);
        }
        else if(usernameExist.isEmpty()){
            return emailExist;
        }
        else if(emailExist.isEmpty()){
            return usernameExist;
        }
        else{
            return "correct";
        }
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
                
                java.sql.Date sqlNascita = new java.sql.Date( utente.getDataNascita().getTime() ); 
                
                uUtente.setInt(1, utente.getIdGruppo());
                uUtente.setString(2, utente.getNome());
                uUtente.setString(3, utente.getCognome());
                uUtente.setDate(4, sqlNascita);
                uUtente.setString(5, utente.getUsername());
                uUtente.setString(6, utente.getPassword());
                uUtente.setString(7, utente.getEmail());
                uUtente.setBoolean(8, utente.isBloccato());
                
                long currentVersion = utente.getVersion();
                long nextVersion = currentVersion + 1;
                
                uUtente.setLong(9, nextVersion);
                uUtente.setInt(10, utente.getKey());
                uUtente.setLong(11, currentVersion);


                if (uUtente.executeUpdate() == 0) {
                    throw new OptimisticLockException(utente);
                }
                utente.setVersion(nextVersion);
                dataLayer.getCache().add(Utente.class, utente); //salviamo nella cache l'utente modificato così che db e cache siano allineati
            }
            else { //insert
                
                java.sql.Date sqlDate;
                sqlDate = new java.sql.Date( utente.getDataNascita().getTime() );
                
                
                iUtente.setInt(1, utente.getIdGruppo());
                iUtente.setString(2, utente.getNome());
                iUtente.setString(3, utente.getCognome());
                iUtente.setDate(4, sqlDate);
                iUtente.setString(5, utente.getUsername());
                iUtente.setString(6, utente.getPassword());
                iUtente.setString(7, utente.getEmail());
                iUtente.setBoolean(8, utente.isBloccato());
                
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
    
    @Override
    public void deleteUtente(int idUtente) throws DataException {
        try {
            if (dataLayer.getCache().has(Utente.class, idUtente)) {
            dataLayer.getCache().delete(Utente.class, idUtente);
        }
            dUtente.setInt(1, idUtente);
            dUtente.execute();
        } catch (SQLException ex) {
            throw new DataException("Unable to delete Utente By ID", ex);
        }
    }

    @Override
    public void insertUtenteListaPartecipanti(Utente partecipant, int idSondaggio) throws DataException {
        try {
            sUtenteExistByEmail.setString(1, partecipant.getEmail());
            try(ResultSet rs = sUtenteExistByEmail.executeQuery()){
                if (rs.next()) {
                    Utente u = createUtente(rs);
                    iUtenteListaPartecipanti2.setInt(1, idSondaggio);
                    iUtenteListaPartecipanti2.setInt(2, u.getKey());
                    iUtenteListaPartecipanti2.setString(3, u.getNome());
                    iUtenteListaPartecipanti2.setString(4, u.getEmail());
                    iUtenteListaPartecipanti2.setString(5, u.getPassword());
                    iUtenteListaPartecipanti2.setBoolean(6, false);
                    iUtenteListaPartecipanti2.setBoolean(7, false);
                    iUtenteListaPartecipanti2.executeUpdate();
                } else {
                    iUtenteListaPartecipanti.setInt(1, idSondaggio);
                    iUtenteListaPartecipanti.setString(2, partecipant.getNome());
                    iUtenteListaPartecipanti.setString(3, partecipant.getEmail());
                    iUtenteListaPartecipanti.setString(4, partecipant.getPassword());
                    iUtenteListaPartecipanti.setBoolean(5, false);
                    iUtenteListaPartecipanti.setBoolean(6, false);
                    iUtenteListaPartecipanti.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAO_MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Utente> getListaPartecipantiBySondaggioId(int idSondaggio, boolean allPartecipants) throws DataException {
        List<Utente> result = new ArrayList();
        if(allPartecipants){
            try {
                sListaPartecipantiBySondaggioAll.setInt(1, idSondaggio);
                try (ResultSet rs = sListaPartecipantiBySondaggioAll.executeQuery()) {
                    while (rs.next()) {
                        Utente u = new UtenteImpl();
                        u.setNome(rs.getString("nome"));
                        u.setEmail(rs.getString("email"));
                        u.setPassword(rs.getString("password"));
                        result.add(u);
                    }
                } catch (SQLException ex) {
                    throw new DataException("Unable to load Utenti", ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(UtenteDAO_MySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            try {
                sListaPartecipantiBySondaggio.setInt(1, idSondaggio);
                sListaPartecipantiBySondaggio.setBoolean(2, false);
                try (ResultSet rs = sListaPartecipantiBySondaggio.executeQuery()) {
                    while (rs.next()) {
                        Utente u = new UtenteImpl();
                        u.setNome(rs.getString("nome"));
                        u.setEmail(rs.getString("email"));
                        u.setPassword(rs.getString("password"));
                        result.add(u);
                    }
                } catch (SQLException ex) {
                    throw new DataException("Unable to load Utenti", ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(UtenteDAO_MySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    @Override
    public void deleteListaPartecipanti(int idSondaggio) throws DataException {
        try {
            dListaPartecipanti.setInt(1, idSondaggio);
            dListaPartecipanti.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAO_MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void updatePartecipanteSetScaduto(String email, int idSondaggio) throws DataException {
        long currentVersion;
        long nextVersion;
        try {
            sListaPartecipantiEmailSondaggio.setString(1, email);
            sListaPartecipantiEmailSondaggio.setInt(2, idSondaggio);
            try (ResultSet rs = sListaPartecipantiEmailSondaggio.executeQuery()){
                if(rs.next()){
                    currentVersion = rs.getInt("version");
                    nextVersion = currentVersion + 1;


                    uPartecipanteSetScaduto.setBoolean(1, true);
                    uPartecipanteSetScaduto.setLong(2, nextVersion);
                    uPartecipanteSetScaduto.setInt(3, idSondaggio);
                    uPartecipanteSetScaduto.setString(4, email);
                    uPartecipanteSetScaduto.setLong(5, currentVersion);
                    uPartecipanteSetScaduto.execute();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAO_MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 

    @Override
    public void updateUtenteListaPartecipanti(ArrayList<Utente> partecipants, int idSondaggio) throws DataException {
        ArrayList<Utente> listaPartecipantiAttuale = (ArrayList<Utente>) this.getListaPartecipantiBySondaggioId(idSondaggio, true);
        System.out.println("LUNGHEZZA VECCHIO: " + listaPartecipantiAttuale.size());
        System.out.println("LUNGHEZZA NUOVO: " + partecipants.size());
        
        long currentVersion;
        long nextVersion;
        
        for(int i = listaPartecipantiAttuale.size()-1; i >= 0; i--){
            Utente u = listaPartecipantiAttuale.get(i);
            for(int j = partecipants.size()-1; j >= 0; j--){
                Utente u2 = partecipants.get(j);
                if(u.getEmail().equals(u2.getEmail())){
                    System.out.println("SONO ENTRATO QUI PER LA " + i + "-ESIMA VOLTA");
                    
                    try {
                        
                        sListaPartecipantiEmailSondaggio.setString(1, u2.getEmail());
                        sListaPartecipantiEmailSondaggio.setInt(2, idSondaggio);
                        
                        try (ResultSet rs = sListaPartecipantiEmailSondaggio.executeQuery()){
                            if(rs.next()){
                                if(!(rs.getString("nome").equals(u2.getNome()) && rs.getString("password").equals(u2.getPassword()))){
                                    currentVersion = rs.getLong("version");
                                    nextVersion = currentVersion + 1;

                                    uUtenteListaPartecipanti.setString(1, u2.getNome());
                                    uUtenteListaPartecipanti.setString(2, u2.getPassword());
                                    uUtenteListaPartecipanti.setBoolean(3, false);
                                    uUtenteListaPartecipanti.setLong(4, nextVersion);
                                    uUtenteListaPartecipanti.setInt(5, idSondaggio);
                                    uUtenteListaPartecipanti.setString(6, u2.getEmail()); 
                                    uUtenteListaPartecipanti.setLong(7, currentVersion);

                                    uUtenteListaPartecipanti.execute();
                                }
                            }
                        } catch (SQLException ex) {
                        Logger.getLogger(UtenteDAO_MySQL.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        partecipants.remove(u2);
                        listaPartecipantiAttuale.remove(u);
                        break;
                    }   catch (SQLException ex) {
                        Logger.getLogger(UtenteDAO_MySQL.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        System.out.println("A QUESTO PUNTO LISTA VECCHIA CONTIENE " +partecipants.size() + " ELEMENTI");
        for(int i=0; i<listaPartecipantiAttuale.size(); i++){
            Utente u = listaPartecipantiAttuale.get(i);
            System.out.println(u.getEmail() + "SCADUTO");
            try {
                
                sListaPartecipantiEmailSondaggio.setString(1, u.getEmail());
                sListaPartecipantiEmailSondaggio.setInt(2, idSondaggio);
                try (ResultSet rs = sListaPartecipantiEmailSondaggio.executeQuery()){
                    if(rs.next()){
                    currentVersion = rs.getInt("version");
                    nextVersion = currentVersion + 1;
                
                
                
                    uUtenteListaPartecipantiScaduto.setBoolean(1, true);
                    uUtenteListaPartecipantiScaduto.setLong(2, nextVersion);
                    uUtenteListaPartecipantiScaduto.setInt(3, idSondaggio);
                    uUtenteListaPartecipantiScaduto.setString(4, u.getEmail()); 
                    uUtenteListaPartecipantiScaduto.setLong(5, currentVersion); 
                    uUtenteListaPartecipantiScaduto.execute();
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(UtenteDAO_MySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("A QUESTO PUNTO PARTECIPANTS CONTIENE " +partecipants.size() + " ELEMENTI");
        for(int i=0; i<partecipants.size();i++){
            this.insertUtenteListaPartecipanti(partecipants.get(i), idSondaggio);
        }
    }

    @Override
    public List<Utente> getListaPartecipantiWithMailToSendBySondaggioId(int idSondaggio) throws DataException {
        List<Utente> result = new ArrayList();
            try {
                sListaPartecipantiWithMailToSendBySondaggio.setInt(1, idSondaggio);
                sListaPartecipantiWithMailToSendBySondaggio.setBoolean(2, false);
                sListaPartecipantiWithMailToSendBySondaggio.setBoolean(3, false);
                try (ResultSet rs = sListaPartecipantiWithMailToSendBySondaggio.executeQuery()) {
                    while (rs.next()) {
                        Utente u = new UtenteImpl();
                        u.setNome(rs.getString("nome"));
                        u.setEmail(rs.getString("email"));
                        u.setPassword(rs.getString("password"));
                        result.add(u);
                    }
                } catch (SQLException ex) {
                    throw new DataException("Unable to load Utenti", ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(UtenteDAO_MySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        return result;
    }

    @Override
    public void ListaPartecipantiSetMailSend(int idSondaggio) throws DataException {
        ArrayList<Utente> listaPartecipantiAttuale = (ArrayList<Utente>) this.getListaPartecipantiBySondaggioId(idSondaggio, false);
        long currentVersion;
        long nextVersion;
        
        for(int i = 0; i < listaPartecipantiAttuale.size(); i++){
            try {
                Utente u = listaPartecipantiAttuale.get(i);
                
                sListaPartecipantiEmailSondaggio.setString(1, u.getEmail());
                sListaPartecipantiEmailSondaggio.setInt(2, idSondaggio);
                try (ResultSet rs = sListaPartecipantiEmailSondaggio.executeQuery()){
                    if(rs.next()){
                        
                        currentVersion = rs.getLong("version");
                        nextVersion = currentVersion + 1;


                        uUtenteListaPartecipantiSendEmail.setBoolean(1, true);
                        uUtenteListaPartecipantiSendEmail.setLong(2, nextVersion);
                        uUtenteListaPartecipantiSendEmail.setInt(3, idSondaggio);
                        uUtenteListaPartecipantiSendEmail.setBoolean(4, false);
                        uUtenteListaPartecipantiSendEmail.setLong(5, currentVersion);
                        uUtenteListaPartecipantiSendEmail.execute();
                        
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(UtenteDAO_MySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public boolean checkifAlreadyAnswered(String email, int idSondaggio) throws DataException {
        boolean check = false;
        try {
            sPartecipanteCheckScaduto.setInt(1, idSondaggio);
            sPartecipanteCheckScaduto.setString(2, email);
            ResultSet rs = sPartecipanteCheckScaduto.executeQuery();
                if (rs.next()) {
                    if (rs.getBoolean("scaduto")){
                        check = true;
                    }
                }
        }catch (SQLException ex) {
            Logger.getLogger(UtenteDAO_MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    @Override
    public boolean checkWithMail(String email) throws DataException {
        boolean check = false;
        try {
            sUtenteExistByEmail.setString(1, email);
            try (ResultSet rs = sUtenteExistByEmail.executeQuery()) {
                if (rs.next()) {
                    check = true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAO_MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }  
        return check;
    }

    @Override
    public Utente getUtenteByMail(String mail) throws DataException {
        Utente u = null;
        try {
            sUtenteExistByEmail.setString(1, mail);
            try (ResultSet rs = sUtenteExistByEmail.executeQuery()) {
                if (rs.next()) {
                    u = createUtente(rs);
                    //e lo mettiamo anche nella cache
                    dataLayer.getCache().add(Utente.class, u);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Utente by idUtente", ex);
        }
        return u;
    }
}
