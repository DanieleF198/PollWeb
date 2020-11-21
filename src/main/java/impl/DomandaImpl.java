/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impl;

import data.DataItemImpl;
import model.Domanda;

/**
 *
 * @author Cronio
 */
public class DomandaImpl extends DataItemImpl<Integer> implements Domanda {
    
    private int id;
    private String titolo; 
    private String descrizione;
    private String tipo;
    private int posizione;
    public boolean obbligatoria;
    private String rispostaCorretta; //VEDIAMO SE DOBBIAMO GESTIRLO CON UN JSONoBJECT
    //implementare opzioni con jsonObject/jsonArray

    public DomandaImpl(int id, String titolo, String descrizione, String tipo, int posizione, boolean obbligatoria, String rispostaCorretta) {
        this.id = id;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.tipo = tipo;
        this.posizione = posizione;
        this.obbligatoria = obbligatoria;
        this.rispostaCorretta = rispostaCorretta;
    }
    
    @Override
    public int getId() { //getter
        return id;
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
    public String getRispostaCorretta() {
        return rispostaCorretta;
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
    public void setRispostaCorretta(String rispostaCorretta) {
        this.rispostaCorretta = rispostaCorretta;
    }

    @Override
    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    @Override
    public void setObbligatoria(boolean obbligatoria) {
        this.obbligatoria = obbligatoria;
    }
    
}
