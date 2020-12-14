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
    
    private int idUtente;
    private int punteggio;
    private Date data;
    private String nomeUtenteRisposta;

    public RispostaImpl() {
        super();
        this.punteggio = 0;
        this.data = null;
        this.nomeUtenteRisposta = "";
    }
    
    @Override
    public int getIdUtente() {
        return idUtente;
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
    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
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
