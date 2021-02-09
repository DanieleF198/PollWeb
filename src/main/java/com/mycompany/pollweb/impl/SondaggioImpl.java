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
    private boolean completo; //true= completo, false= non completo
    private boolean visibilita; //true= attivato, false= non attivato
    private boolean privato; //true= privato, false= pubblico
    private boolean modificabile;
    private List <DomandaImpl> domande = new ArrayList<>();
    private String testoApertura;
    private String testoChiusura;
    private Date creazione;
    private Date scadenza; 
    private int compilazioni; 

    public SondaggioImpl() {
        super();
        this.idUtente = 0;
        this.utente = null;
        this.titolo = null;
        this.completo = false;
        this.privato = false;
        this.visibilita = false;
        this.modificabile = false;
        this.creazione = null;
        this.scadenza = null;
        this.testoApertura = "";
        this.testoChiusura = "";
        this.compilazioni = 0;
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
    public int getCompilazioni() {
        return compilazioni;
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
    public boolean isCompleto() {
        return completo;
    }

    @Override
    public boolean isPrivato() {
        return privato;
    }

    @Override
    public boolean isModificabile() {
        return modificabile;
    }

    @Override
    public void setModificabile(boolean modificabile) {
        this.modificabile = modificabile;
    }

    @Override
    public void setPrivato(boolean privato) {
        this.privato = privato;
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
    public void setCompleto(boolean completo) {
        this.completo = completo;
    }

    @Override
    public void setVisibilita(boolean visibilita) {
        this.visibilita = visibilita;
    }
    
    @Override
     public void setCompilazioni(int compilazioni) {
        this.compilazioni = compilazioni;
    }
    
}
