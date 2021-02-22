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
import com.mycompany.pollweb.model.Risposta;
import com.mycompany.pollweb.proxy.RispostaProxy;
import java.sql.Date;
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
public class RispostaDAO_MySQL extends DAO implements RispostaDAO {
    
    private PreparedStatement sRispostaByID;
    private PreparedStatement sRispostaByIDUtente;
    private PreparedStatement sRispostaByIPUtente;
    private PreparedStatement sRisposte;
    private PreparedStatement iRispostaUserReg;
    private PreparedStatement uRispostaUserReg;
    private PreparedStatement iRispostaUserNotReg;
    private PreparedStatement uRispostaUserNotReg;
    private PreparedStatement iRispostaUserPartecipante;
    private PreparedStatement dRisposta;
    
    RispostaDAO_MySQL(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataException {
        try {
            super.init();
            
            sRispostaByID = connection.prepareStatement("SELECT * FROM Risposta WHERE idRisposta=?");
            sRispostaByIDUtente = connection.prepareStatement("SELECT * FROM Risposta WHERE idUtente=?");
            sRispostaByIPUtente = connection.prepareStatement("SELECT * FROM Risposta WHERE ipUtenteRisposta=? AND usernameUtenteRisposta=?");
            sRisposte = connection.prepareStatement("SELECT * FROM Risposta");
            
            iRispostaUserReg = connection.prepareStatement("INSERT INTO Risposta (idUtente,dataCreazione,usernameUtenteRisposta,ipUtenteRisposta) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            iRispostaUserNotReg = connection.prepareStatement("INSERT INTO Risposta (dataCreazione,usernameUtenteRisposta,ipUtenteRisposta) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uRispostaUserNotReg = connection.prepareStatement("UPDATE Risposta SET dataCreazione=?, ipUtenteRisposta=?,version=? WHERE idRisposta=? AND version=?");
            iRispostaUserPartecipante = connection.prepareStatement("INSERT INTO Risposta (dataCreazione,usernameUtenteRisposta,ipUtenteRisposta) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uRispostaUserReg = connection.prepareStatement("UPDATE Risposta SET idUtente=?,dataCreazione=?,usernameUtenteRisposta=?,ipUtenteRisposta=?,version=? WHERE idRisposta=? AND version=?");

            dRisposta = connection.prepareStatement("DELETE FROM Risposta WHERE idRisposta=?");
            
            
        } catch (SQLException ex) {
            throw new DataException("Error initializing PollWebdb data layer", ex);
        }
    }
    
    @Override
    public void destroy() throws DataException {
        
        try {
            
            sRispostaByID.close();
            sRispostaByIDUtente.close();
            sRispostaByIPUtente.close();
            
            sRisposte.close();
            
            iRispostaUserReg.close();
            iRispostaUserNotReg.close();
            uRispostaUserReg.close();
            uRispostaUserNotReg.close();
            iRispostaUserPartecipante.close();
            dRisposta.close();
            
        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }
    
    @Override
    public RispostaProxy createRisposta() {
        return new RispostaProxy(getDataLayer());
    }
    
    //helper
    private RispostaProxy createRisposta(ResultSet rs) throws DataException {
        RispostaProxy r = createRisposta();
        try {
            r.setKey(rs.getInt("idRisposta"));
            r.setIdUtente(rs.getInt("idUtente"));
            r.setData(rs.getDate("dataCreazione"));
            r.setUsernameUtenteRisposta(rs.getString("usernameUtenteRisposta"));
            r.setIpUtenteRisposta(rs.getString("ipUtenteRisposta"));
            r.setVersion(rs.getLong("version"));
        } catch (SQLException ex) {
            throw new DataException("Unable to create Risposta object form ResultSet", ex);
        }
        return r;
    }
    
    @Override
    public Risposta getRisposta(int idRisposta) throws DataException {
        Risposta r = null;
        if (dataLayer.getCache().has(Risposta.class, idRisposta)) {
            r = dataLayer.getCache().get(Risposta.class, idRisposta);
        } else {
            try {
                sRispostaByID.setInt(1, idRisposta);
                try (ResultSet rs = sRispostaByID.executeQuery()) {
                    if (rs.next()) {
                        r = createRisposta(rs);
                        System.out.println("userId " + r.getIdUtente());
                        System.out.println("userId2 " + rs.getInt("idUtente"));
                        dataLayer.getCache().add(Risposta.class, r);
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load Risposta by idRisposta", ex);
            }
        }
        return r;
    }
    
    @Override
    public List<Risposta> getRispostaByIdUtente(int idUtente) throws DataException {
        List<Risposta> result = new ArrayList();
            try {
                sRispostaByIDUtente.setInt(1, idUtente);
                try (ResultSet rs = sRispostaByIDUtente.executeQuery()) {
                    while (rs.next()) {
                        result.add((Risposta) getRisposta(rs.getInt("idRisposta")));
                    }   
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load Risposta by idRisposta", ex);
            }
        return result;
    }
    
    @Override
    public List<Risposta> getRispostaByIPUtente(String ipUtente) throws DataException {
        List<Risposta> result = new ArrayList();
            try {
                sRispostaByIPUtente.setString(1, ipUtente);
                sRispostaByIPUtente.setString(2, "no!username");
                try (ResultSet rs = sRispostaByIPUtente.executeQuery()) {
                    while (rs.next()) {
                        result.add((Risposta) getRisposta(rs.getInt("idRisposta")));
                    }
                }
            } catch (SQLException ex) {
                throw new DataException("Unable to load Risposta by idRisposta", ex);
            }
        return result;
    }
    
    @Override
    public List<Risposta> getRisposte() throws DataException {
        List<Risposta> result = new ArrayList();

        try (ResultSet rs = sRisposte.executeQuery()) {
            while (rs.next()) {
                result.add((Risposta) getRisposta(rs.getInt("idRisposta")));
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load Risposte", ex);
        }
        return result;
    }
    
    @Override
    public int storeRispostaUserReg (Risposta risposta) throws DataException {
        try {
            if (risposta.getKey() != null && risposta.getKey() > 0) {
                if (risposta instanceof DataItemProxy && !((DataItemProxy) risposta).isModified()) {
                    return risposta.getKey();
                }
                java.sql.Date sqlCreazione = new java.sql.Date( risposta.getData().getTime() );
                uRispostaUserReg.setInt(1, risposta.getIdUtente());
                uRispostaUserReg.setDate(2, sqlCreazione);
                uRispostaUserReg.setString(3, risposta.getUsernameUtenteRisposta());
                uRispostaUserReg.setString(4, risposta.getIpUtenteRisposta());
                
                long currentVersion = risposta.getVersion();
                long nextVersion = currentVersion + 1;
                
                uRispostaUserReg.setLong(5, nextVersion);
                uRispostaUserReg.setInt(6, risposta.getKey());
                uRispostaUserReg.setLong(7, currentVersion);

                if (uRispostaUserReg.executeUpdate() == 0) {
                    throw new OptimisticLockException(risposta);
                }
                risposta.setVersion(nextVersion);
            }
            else {
                java.sql.Date sqlCreazione = new java.sql.Date( risposta.getData().getTime() );
                iRispostaUserReg.setInt(1, risposta.getIdUtente());
                iRispostaUserReg.setDate(2, sqlCreazione);
                iRispostaUserReg.setString(3, risposta.getUsernameUtenteRisposta());
                iRispostaUserReg.setString(4, risposta.getIpUtenteRisposta());
                
                if (iRispostaUserReg.executeUpdate() == 1) {
                    try (ResultSet keys = iRispostaUserReg.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            risposta.setKey(key);
                            dataLayer.getCache().add(Risposta.class, risposta);
                        }
                    }
                }
            }
            if (risposta instanceof DataItemProxy) {
                ((DataItemProxy) risposta).setModified(false);
            }
            return risposta.getKey();
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store risposta", ex);
        }
    }
    
    @Override
    public int storeRispostaUserNotReg (Risposta risposta) throws DataException {
        try {
            if (risposta.getKey() != null && risposta.getKey() > 0) { //update
                if (risposta instanceof DataItemProxy && !((DataItemProxy) risposta).isModified()) {
                    return risposta.getKey();
                }
                java.sql.Date sqlCreazione = new java.sql.Date( risposta.getData().getTime() );
                uRispostaUserNotReg.setDate(1, sqlCreazione);
                uRispostaUserNotReg.setString(2, risposta.getIpUtenteRisposta());
                
                long currentVersion = risposta.getVersion();
                long nextVersion = currentVersion + 1;
                
                uRispostaUserNotReg.setLong(3, nextVersion);
                uRispostaUserNotReg.setInt(4, risposta.getKey());
                uRispostaUserNotReg.setLong(5, currentVersion);
                

                if (uRispostaUserNotReg.executeUpdate() == 0) {
                    throw new OptimisticLockException(risposta);
                }
                risposta.setVersion(nextVersion);
            }
            else {
                java.sql.Date sqlCreazione = new java.sql.Date( risposta.getData().getTime() );
                iRispostaUserNotReg.setDate(1, sqlCreazione);
                iRispostaUserNotReg.setString(2, "no!username"); //siamo sicuri che questo username non esista perché non ammettiamo username con il "!"
                iRispostaUserNotReg.setString(3, risposta.getIpUtenteRisposta());
                
                if (iRispostaUserNotReg.executeUpdate() == 1) {
                    try (ResultSet keys = iRispostaUserNotReg.getGeneratedKeys()) {
                        if (keys.next()) {
                            int key = keys.getInt(1);
                            risposta.setKey(key);
                            dataLayer.getCache().add(Risposta.class, risposta);
                        }
                    }
                }
            }
            if (risposta instanceof DataItemProxy) {
                ((DataItemProxy) risposta).setModified(false);
            }
            return risposta.getKey();
        } catch (SQLException | OptimisticLockException ex) {
            throw new DataException("Unable to store risposta", ex);
        }
    }
    
    @Override
    public int insertRispostaUserPartecipante (Risposta risposta) throws DataException {
        try {
            java.sql.Date sqlCreazione = new java.sql.Date( risposta.getData().getTime() );
            iRispostaUserPartecipante.setDate(1, sqlCreazione);
            iRispostaUserPartecipante.setString(2, risposta.getUsernameUtenteRisposta()); //per il partecipante sarà la mail in quanto non ha username
            iRispostaUserPartecipante.setString(3, risposta.getIpUtenteRisposta());
            if (iRispostaUserPartecipante.executeUpdate() == 1) {
                try (ResultSet keys = iRispostaUserPartecipante.getGeneratedKeys()) {
                    if (keys.next()) {
                        int key = keys.getInt(1);
                        risposta.setKey(key);
                        dataLayer.getCache().add(Risposta.class, risposta);
                    }
                }
            }
            if (risposta instanceof DataItemProxy) {
                ((DataItemProxy) risposta).setModified(false);
            }
            return risposta.getKey();
        } catch (SQLException ex) {
            throw new DataException("Unable to store risposta", ex);
        }
    }
    
}
