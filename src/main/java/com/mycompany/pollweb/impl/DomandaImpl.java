/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.impl;

import com.mycompany.pollweb.data.DataItemImpl;
import com.mycompany.pollweb.model.Domanda;
import org.json.JSONObject;



/**
 *
 * @author Cronio
 */
public class DomandaImpl extends DataItemImpl<Integer> implements Domanda {
    
    private int idSodnaggio;
    private String titolo; 
    private String descrizione;
    private String tipo;
    private int posizione;
    public boolean obbligatoria;
    private JSONObject opzioni = new JSONObject();   
    private JSONObject rispostaCorretta = new JSONObject();   

    public DomandaImpl() {
        super();
        this.idSodnaggio = 0;
        this.titolo = "";
        this.descrizione = "";
        this.tipo = "";
        this.posizione = 0;
        this.obbligatoria = false;
        this.opzioni = null;
        this.rispostaCorretta = null;
    }

    @Override
    public int getIdSondaggio() {
        return idSodnaggio;
    }

    @Override
    public String getTitolo() {
        return titolo;
    }

    @Override
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public String getTipo() {
        return tipo;
    }

    @Override
    public JSONObject getRispostaCorretta() {
        return rispostaCorretta;
    }

    @Override
    public JSONObject getOpzioni() {
        return opzioni;
    }

    @Override
    public int getPosizione() {
        return posizione;
    }

    @Override
    public boolean isObbligatoria() {
        return obbligatoria;
    }

    @Override
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    @Override
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public void setRispostaCorretta(JSONObject rispostaCorretta) {
        this.rispostaCorretta = rispostaCorretta;
    }

    @Override
    public void setOpzioni(JSONObject opzioni) {
        this.opzioni = opzioni;
    }

    @Override
    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    @Override
    public void setObbligatoria(boolean obbligatoria) {
        this.obbligatoria = obbligatoria;
    }

    @Override
    public void setIdSondaggio(int sondaggioId) {
        this.idSodnaggio = sondaggioId;
    }
    
}
