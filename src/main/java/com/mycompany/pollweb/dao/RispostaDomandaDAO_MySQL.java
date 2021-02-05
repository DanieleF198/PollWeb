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
import com.mycompany.pollweb.model.RispostaDomanda;
import com.mycompany.pollweb.proxy.RispostaDomandaProxy;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Cronio
 */
public class RispostaDomandaDAO_MySQL extends DAO implements RispostaDomandaDAO  {

    private PreparedStatement sRispostaDomanda;
    private PreparedStatement iRispostaDomanda;
    private PreparedStatement uRispostaDomanda;
    
    RispostaDomandaDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataException {
        try {
            super.init();
            
            sRispostaDomanda = connection.prepareStatement("SELECT * FROM RispostaDomanda WHERE idRisposta=? AND idDomanda=?");
            iRispostaDomanda = connection.prepareStatement("INSERT INTO RispostaDomanda (idRisposta,idDomanda,risposta) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uRispostaDomanda = connection.prepareStatement("UPDATE RispostaDomanda SET idRisposta=?,idDomanda=?,risposta=? WHERE idRisposta=? AND idDomanda=?");
            
            
        } catch (SQLException ex) {
            throw new DataException("Error initializing PollWebdb data layer", ex);
        }
    }
    
    @Override
    public void destroy() throws DataException {
        
        try {

            sRispostaDomanda.close();
            iRispostaDomanda.close();
            uRispostaDomanda.close();
            
        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }
    
    @Override
    public RispostaDomandaProxy createRispostaDomanda() {
        return new RispostaDomandaProxy(getDataLayer());
    }
    
    //helper
    private RispostaDomandaProxy createRispostaDomanda(ResultSet rs) throws DataException {
        RispostaDomandaProxy r = createRispostaDomanda();
        try {
            r.setKey(rs.getInt("idRisposta"));
            r.setIdDomanda(rs.getInt("idDomanda"));
            String jsonString = rs.getObject("risposta").toString().replaceAll("\"", "\\\"");
            JSONObject risposta = new JSONObject(jsonString);
            r.setRisposta(risposta);
        } catch (SQLException ex) {
            throw new DataException("Unable to create Risposta object form ResultSet", ex);
        }
        return r;
    }

    @Override
    public RispostaDomanda getRispostaDomanda(int idRisposta,int idDomanda) throws DataException {
        RispostaDomanda r = null;
        try {
                sRispostaDomanda.setInt(1, idRisposta);
                sRispostaDomanda.setInt(2, idDomanda);
                try (ResultSet rs = sRispostaDomanda.executeQuery()) {
                    if (rs.next()) {
                        r = createRispostaDomanda(rs);
                        //e lo mettiamo anche nella cache
                        dataLayer.getCache().add(RispostaDomanda.class, r);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load RispostaDomanda by idRisposta and idDomanda", ex);
            }
        return r;
    }

    @Override
    public void storeRisposta(RispostaDomanda rispostaDomanda) throws DataException { //DA TESTARE
        try {
            if (rispostaDomanda.getIdRisposta() > 0) {
                if (rispostaDomanda instanceof DataItemProxy && !((DataItemProxy) rispostaDomanda).isModified()) {
                    return;
                }
                uRispostaDomanda.setInt(1, rispostaDomanda.getIdRisposta());
                uRispostaDomanda.setInt(2, rispostaDomanda.getIdDomanda());
                if(rispostaDomanda.getRisposta() != null){
                    uRispostaDomanda.setObject(3, rispostaDomanda.getRisposta().toString());
                }else{
                    uRispostaDomanda.setObject(3, "{}");
                }

                if (uRispostaDomanda.executeUpdate() == 0) {
                    throw new OptimisticLockException(rispostaDomanda);
                }
            }

            else { //insert
                iRispostaDomanda.setInt(1, rispostaDomanda.getIdRisposta());
                iRispostaDomanda.setInt(2, rispostaDomanda.getIdDomanda());
                if(rispostaDomanda.getRisposta() != null){
                    iRispostaDomanda.setObject(3, rispostaDomanda.getRisposta().toString());
                }else{
                    iRispostaDomanda.setObject(3, "{}");
                }
            }
        if (rispostaDomanda instanceof DataItemProxy) {
            ((DataItemProxy) rispostaDomanda).setModified(false);
        }
        } catch (SQLException ex) {
            Logger.getLogger(RispostaDomandaDAO_MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }      
}
    
    

