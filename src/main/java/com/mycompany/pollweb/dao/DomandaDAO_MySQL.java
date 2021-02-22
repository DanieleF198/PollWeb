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
        try {
            
            sDomandaByID.close();
            sDomandaBySondaggioID.close();
            sDomandaBySondaggioIDAndPosition.close();

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
        if (dataLayer.getCache().has(Domanda.class, idDomanda)) {
            d = dataLayer.getCache().get(Domanda.class, idDomanda);
        } else {
            try {
                sDomandaByID.setInt(1, idDomanda);
                try (ResultSet rs = sDomandaByID.executeQuery()) {
                    if (rs.next()) {
                        d = createDomanda(rs);
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
                
                System.out.println("VERSIONE old: " + currentVersion);
                System.out.println("VERSIONE old: " + nextVersion);
                
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
                    try (ResultSet keys = iDomanda.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            domanda.setKey(key);
                            dataLayer.getCache().add(Domanda.class, domanda);
                        }
                    }
                }
            }

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