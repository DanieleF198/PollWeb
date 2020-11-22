/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.impl;

import com.mycompany.pollweb.data.DataItemImpl;
import com.mycompany.pollweb.model.ListaPartecipanti;

/**
 *
 * @author Cronio
 */
public class ListaPartecipantiImpl extends DataItemImpl<Integer> implements ListaPartecipanti  {
    
    private int id;
    private int utenteId;
    private int sondaggioId;

    public ListaPartecipantiImpl(int id, int utenteId, int sondaggioId) {
        this.id = id;
        this.utenteId = utenteId;
        this.sondaggioId = sondaggioId;
    }

    @Override
    public int getId() { //getter
        return id;
    }

    @Override
    public int getUtenteId() {
        return utenteId;
    }

    @Override
    public int getSondaggioId() {
        return sondaggioId;
    }

    @Override
    public void setId(int id) { //setter
        this.id = id;
    }

    @Override
    public void setUtenteId(int utenteId) {
        this.utenteId = utenteId;
    }

    @Override
    public void setSondaggioId(int sondaggioId) {
        this.sondaggioId = sondaggioId;
    }
    
}
