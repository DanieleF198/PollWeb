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
    
    private int idUtente;
    private int idSondaggio;

    public ListaPartecipantiImpl() {
        super();
        this.idUtente = 0;
        this.idSondaggio = 0;
    }

    @Override
    public int getIdUtente() {
        return idUtente;
    }

    @Override
    public int getIdSondaggio() {
        return idSondaggio;
    }

    @Override
    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    @Override
    public void setIdSondaggio(int idSondaggio) {
        this.idSondaggio = idSondaggio;
    }
    
}
