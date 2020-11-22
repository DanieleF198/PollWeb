/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.impl;

import com.mycompany.pollweb.data.DataItemImpl;
import java.util.Date;
import com.mycompany.pollweb.model.Risposta;

/**
 *
 * @author Cronio
 */
public class RispostaImpl extends DataItemImpl<Integer> implements Risposta {
    
    private int id;
    private int userId;
    private int punteggio;
    private Date data;
    private String nomeUtenteRisposta;

    public RispostaImpl(int id, int userId, int punteggio, Date data, String nomeUtenteRisposta) {
        this.id = id;
        this.userId = userId;
        this.punteggio = punteggio;
        this.data = data;
        this.nomeUtenteRisposta = nomeUtenteRisposta;
    }

    @Override
    public int getId() { //getter
        return id;
    }
    
    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public int getPunteggio() {
        return punteggio;
    }

    @Override
    public Date getData() {
        return data;
    }

    @Override
    public String getNomeUtenteRisposta() {
        return nomeUtenteRisposta;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public void setPunteggio(int punteggio) {
        this.punteggio = punteggio;
    }

    @Override
    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public void setNomeUtenteRisposta(String nomeUtenteRisposta) {
        this.nomeUtenteRisposta = nomeUtenteRisposta;
    }
    
}
