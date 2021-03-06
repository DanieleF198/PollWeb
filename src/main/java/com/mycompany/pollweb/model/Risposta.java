/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.model;

import com.mycompany.pollweb.data.DataItem;
import com.mycompany.pollweb.impl.RispostaImpl;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Cronio
 */
public interface Risposta extends DataItem<Integer> {
    
    public int getIdUtente();
    
    public Utente getUtente();

    public Date getData();
    
    public String getUsernameUtenteRisposta();
    
    public String getIpUtenteRisposta();

    public void setIdUtente(int userId);
    
    public void setUtente(Utente utente);

    public void setData(Date data);
    
    public void setUsernameUtenteRisposta(String usernameUtenteRisposta);
    
    public void setIpUtenteRisposta(String ipUtenteRisposta);
    
}
