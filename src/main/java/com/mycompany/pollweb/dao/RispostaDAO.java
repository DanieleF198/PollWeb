/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.dao;

import com.mycompany.pollweb.data.DataException;
import com.mycompany.pollweb.model.Risposta;
import com.mycompany.pollweb.proxy.RispostaProxy;
import java.util.List;

/**
 *
 * @author Cronio
 */
public interface RispostaDAO {
    
    public RispostaProxy createRisposta();
    
    Risposta getRisposta(int idRisposta) throws DataException;
    
    public List<Risposta> getRispostaByIdUtente(int idUtente) throws DataException;
    
    public List<Risposta> getRispostaByIPUtente(String ipUtente) throws DataException;
    
    public List<Risposta> getRisposte() throws DataException;
    
    public int storeRispostaUserReg(Risposta risposta) throws DataException;
    
    public int storeRispostaUserNotReg(Risposta risposta) throws DataException;
    
    public int insertRispostaUserPartecipante(Risposta risposta) throws DataException;
    
}
