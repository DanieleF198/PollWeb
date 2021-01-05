/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.impl;

import com.mycompany.pollweb.data.DataItemImpl;
import com.mycompany.pollweb.model.ListaPartecipanti;
import com.mycompany.pollweb.model.Sondaggio;
import com.mycompany.pollweb.model.Utente;

/**
 *
 * @author Cronio
 */
public class ListaPartecipantiImpl extends DataItemImpl<Integer> implements ListaPartecipanti  {
    
    private int idUtente;
    private Utente utente;
    private int idSondaggio;
    private Sondaggio sondaggio;
    private String email;

    public ListaPartecipantiImpl() {
        super();
        this.idUtente = 0;
        this.utente = null;
        this.idSondaggio = 0;
        this.sondaggio = null;
    }

    @Override
    public int getIdUtente() { //getter
        return idUtente;
    }

    @Override
    public int getIdSondaggio() {
        return idSondaggio;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Sondaggio getSondaggio() {
        return sondaggio;
    }

    public void setSondaggio(Sondaggio sondaggio) {
        this.sondaggio = sondaggio;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setIdUtente(int idUtente) { //setter
        this.idUtente = idUtente;
    }

    @Override
    public void setIdSondaggio(int idSondaggio) {
        this.idSondaggio = idSondaggio;
    }
    
    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    
    
}
