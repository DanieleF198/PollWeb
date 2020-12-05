/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.dao;

import com.mycompany.pollweb.data.DAO;
import com.mycompany.pollweb.data.DataException;
import com.mycompany.pollweb.data.DataLayer;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Cronio
 */
public class UtenteDAO_MySQL extends DAO implements UtenteDAO  {
    
    private PreparedStatement sUtenteByID;
    private PreparedStatement sUtenteByGruppo;
    private PreparedStatement sUtenteByemail;
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

            //precompiliamo tutte le query utilizzate nella classe
            sUtenteByID = connection.prepareStatement("SELECT * FROM Utente WHERE idUtente=?");
            sUtenteByGruppo = connection.prepareStatement("SELECT * FROM Utente WHERE idGruppo=?");
            sUtenteByemail = connection.prepareStatement("SELECT * FROM Utente WHERE email=?");
            
            iUtente = connection.prepareStatement("INSERT INTO Utente (nome,idGruppo,cognome,password,email,eta) VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uUtente = connection.prepareStatement("UPDATE Utente SET nome=?,idGruppo=?,cognome=?,password=?, email=?, eta=? WHERE idUtente=?");
            dUtente = connection.prepareStatement("DELETE FROM Utente WHERE idUtente=?");

            
        } catch (SQLException ex) {
            throw new DataException("Error initializing PollWeb data layer", ex);
        }
    }
    
    @Override
    public void destroy() throws DataException {
        //anche chiudere i PreparedStamenent � una buona pratica...
        try {
            
            sUtenteByID.close();
            
            sUtenteByGruppo.close();
            sUtenteByemail.close();
            
            iUtente.close();
            uUtente.close();
            dUtente.close();
            
        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }
    
//    @Override
//    public UtenteProxy createUtente() {
//        return new UtenteProxy(getDataLayer());
//    }
    
}
