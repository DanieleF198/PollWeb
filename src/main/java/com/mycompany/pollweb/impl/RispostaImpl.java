/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.impl;

import com.mycompany.pollweb.data.DataItemImpl;
import java.util.Date;
import com.mycompany.pollweb.model.Risposta;
import com.mycompany.pollweb.model.Utente;

/**
 *
 * @author Cronio
 */
public class RispostaImpl extends DataItemImpl<Integer> implements Risposta {
    
    private int idUtente;
    private Utente utente;
    private Date data;
    private String usernameUtenteRisposta;
    private String ipUtenteRisposta;

    public RispostaImpl() {
        super();
        this.utente = null;
        this.data = null;
        this.usernameUtenteRisposta = "";
        this.ipUtenteRisposta = "";
    }
    
    @Override
    public int getIdUtente() {
        return idUtente;
    }
    
    @Override
    public Utente getUtente() {
        return utente;
    }

    @Override
    public Date getData() {
        return data;
    }

    @Override
    public String getUsernameUtenteRisposta() {
        return usernameUtenteRisposta;
    }

    @Override
    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }
    
    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    @Override
    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public void setUsernameUtenteRisposta(String usernameUtenteRisposta) {
        this.usernameUtenteRisposta = usernameUtenteRisposta;
    }

    @Override
    public String getIpUtenteRisposta() {
        return ipUtenteRisposta;
    }

    @Override
    public void setIpUtenteRisposta(String ipUtenteRisposta) {
        this.ipUtenteRisposta = ipUtenteRisposta;
    }
    
}
