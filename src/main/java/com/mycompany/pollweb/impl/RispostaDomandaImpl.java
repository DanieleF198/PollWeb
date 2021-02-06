/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.impl;

import com.mycompany.pollweb.data.DataItemImpl;
import com.mycompany.pollweb.model.RispostaDomanda;
import org.json.JSONObject;

/**
 *
 * @author Cronio
 */
public class RispostaDomandaImpl extends DataItemImpl<Integer> implements RispostaDomanda{
    
    private int idRisposta;
    private int idDomanda;
    private JSONObject risposta = new JSONObject();

    public RispostaDomandaImpl() {
        super();
        this.idRisposta = 0;
        this.idDomanda = 0;
        this.risposta = null;
    }

    @Override
    public int getIdRisposta() {
        return idRisposta;
    }

    @Override
    public void setIdRisposta(int idRisposta) {
        this.idRisposta = idRisposta;
    }

    @Override
    public int getIdDomanda() {
        return idDomanda;
    }

    @Override
    public void setIdDomanda(int idDomanda) {
        this.idDomanda = idDomanda;
    }

    @Override
    public JSONObject getRisposta() {
        return risposta;
    }

    @Override
    public void setRisposta(JSONObject risposta) {
        this.risposta = risposta;
    }
    
    
    
    
    
}
