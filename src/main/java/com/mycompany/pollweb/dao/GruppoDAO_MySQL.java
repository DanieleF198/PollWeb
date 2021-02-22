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
            uGruppo = connection.prepareStatement("UPDATE Gruppo SET idGruppo=?, nomeGruppo=?,version=? WHERE idGruppo=? AND version=?");
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
            g.setVersion(rs.getLong("version"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create Gruppo object form ResultSet", ex);
        }
        return g;
    }
    
    @Override
    public Gruppo getGruppo(int idGruppo) throws DataException {
        Gruppo g = null;
        if (dataLayer.getCache().has(Gruppo.class, idGruppo)) {
            g = dataLayer.getCache().get(Gruppo.class, idGruppo);
        } else {
            try {
                sGruppoByID.setInt(1, idGruppo);
                try (ResultSet rs = sGruppoByID.executeQuery()) {
                    if (rs.next()) {
                        g = createGruppo(rs);
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
                if (gruppo instanceof DataItemProxy && !((DataItemProxy) gruppo).isModified()) {
                    return;
                }
                uGruppo.setString(1, gruppo.getNomeGruppo());
                
                long currentVersion = gruppo.getVersion();
                long nextVersion = currentVersion + 1;
                
                uGruppo.setLong(2, nextVersion);
                uGruppo.setInt(3, gruppo.getKey());
                uGruppo.setLong(4, currentVersion);

                if (uGruppo.executeUpdate() == 0) {
                    throw new OptimisticLockException(gruppo);
                }
                gruppo.setVersion(nextVersion);
            }
            else { //insert
                iGruppo.setString(1, gruppo.getNomeGruppo());
                if (iGruppo.executeUpdate() == 1) {
                    try (ResultSet keys = iGruppo.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            gruppo.setKey(key);
                            dataLayer.getCache().add(Gruppo.class, gruppo);
                        }
                    }
                }
            }
            if (gruppo instanceof DataItemProxy) {
                ((DataItemProxy) gruppo).setModified(false);
            }
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store gruppo", ex);
        }
    }
    
}
