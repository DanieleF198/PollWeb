/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.impl;

import com.mycompany.pollweb.data.DataItemImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.mycompany.pollweb.model.Sondaggio;
import com.mycompany.pollweb.model.Utente;

/**
 *
 * @author Cronio
 */
public class SondaggioImpl extends DataItemImpl<Integer> implements Sondaggio {
    
    private int idUtente;
    private Utente utente;
    private String titolo;
    private boolean quiz;
    private int stato; //0 da fare - 1 fatto - 2 confermato
    private boolean visibilita;
    private List <DomandaImpl> domande = new ArrayList<>();
    private String testoApertura;
    private String testoChiusura;
    private Date creazione;
    private Date scadenza; 

    public SondaggioImpl() {
        super();
        this.idUtente = 0;
        this.utente = null;
        this.titolo = null;
        this.quiz = false;
        this.stato = 0;
        this.visibilita = false;
        this.creazione = null;
        this.scadenza = null;
        this.testoApertura = "";
        this.testoChiusura = "";
    }
    
    @Override
    public boolean isQuiz(){
	return quiz;
    }

    @Override
    public List<DomandaImpl> getDomande() {
        return domande;
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
    public Date getCreazione() {
        return creazione;
    }

    @Override
    public Date getScadenza() {
        return scadenza;
    }

    @Override
    public String getTestoApertura() {
        return testoApertura;
    }

    @Override
    public String getTestoChiusura() {
        return testoChiusura;
    }

    @Override
    public int getStato() {
        return stato;
    }

    @Override
    public String getTitolo() {
        return titolo;
    }
    
    @Override
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    @Override
    public boolean isVisibilita() {
        return visibilita;
    }

    @Override
    public void setQuiz(boolean quiz) {
        this.quiz = quiz;
    }

    @Override
    public void setDomande(List<DomandaImpl> domande) {
        this.domande = domande;
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
    public void setCreazione(Date creazione) {
        this.creazione = creazione;
    }

    @Override
    public void setScadenza(Date scadenza) {
        this.scadenza = scadenza;
    }

    @Override
    public void setTestoApertura(String testoApertura) {
        this.testoApertura = testoApertura;
    }

    @Override
    public void setTestoChiusura(String testoChiusura) {
        this.testoChiusura = testoChiusura;
    }

    @Override
    public void setStato(int stato) {
        this.stato = stato;
    }

    @Override
    public void setVisibilita(boolean visibilita) {
        this.visibilita = visibilita;
    }
    
}
