/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.impl;

import com.mycompany.pollweb.data.DataItemImpl;
import com.mycompany.pollweb.model.Domanda;
import com.mycompany.pollweb.model.Sondaggio;
import org.json.JSONObject;



/**
 *
 * @author Cronio
 */
public class DomandaImpl extends DataItemImpl<Integer> implements Domanda {
    
    private int idSodnaggio;
    private Sondaggio sondaggio;
    private String titolo; 
    private String descrizione;
    private String tipo;
    private int posizione;
    public boolean obbligatoria;
    private JSONObject opzioni = new JSONObject();   
    private JSONObject rispostaCorretta = new JSONObject();   
    private String vincolo;

    public DomandaImpl() {
        super();
        this.idSodnaggio = 0;
        this.sondaggio = null;
        this.titolo = "";
        this.descrizione = "";
        this.tipo = "";
        this.posizione = 0;
        this.obbligatoria = false;
        this.opzioni = null;
        this.rispostaCorretta = null;
        this.vincolo = "";
    }

    @Override
    public int getIdSondaggio() {
        return idSodnaggio;
    }
    
    @Override
    public Sondaggio getSondaggio() {
        return sondaggio;
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
    public String getVincolo(){
        return vincolo;
    }

    @Override
    public boolean isObbligatoria() {
        return obbligatoria;
    }

    @Override
    public void setIdSondaggio(int idSodnaggio) {
        this.idSodnaggio = idSodnaggio;
    }
    
    @Override
     public void setSondaggio(Sondaggio sondaggio) {
        this.sondaggio = sondaggio;
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
    public void setVincolo(String vincolo){
        this.vincolo = vincolo;
    }
    
}
