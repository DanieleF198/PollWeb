/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.dao;

import com.mycompany.pollweb.data.DataLayer;

import com.mycompany.pollweb.data.DAO;
import com.mycompany.pollweb.data.DataException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
            sGruppi = connection.prepareStatement("SELECT ID AS articleID FROM Gruppo");
            
            iGruppo = connection.prepareStatement("INSERT INTO Gruppo (idGruppo, nomeGruppo) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            uGruppo = connection.prepareStatement("UPDATE Gruppo SET idGruppo=?, nomeGruppo=? WHERE ID=?");
            dGruppo = connection.prepareStatement("DELETE FROM Gruppo WHERE ID=?");
            
            
        } catch (SQLException ex) {
            throw new DataException("Error initializing newspaper data layer", ex);
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
}
