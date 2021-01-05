/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.model;

import com.mycompany.pollweb.data.DataItem;
import org.json.JSONObject;

/**
 *
 * @author Cronio
 */
public interface Domanda extends DataItem<Integer> {
    
    public int getIdSondaggio();
    
    public Sondaggio getSondaggio();
    
    String getTitolo();
    
    String getDescrizione();
    
    String getTipo();
    
    JSONObject getRispostaCorretta();
    
    public JSONObject getOpzioni();
    
    public int getPosizione();

    public boolean isObbligatoria();
    
    public void setIdSondaggio(int sondaggioId);
    
    public void setSondaggio(Sondaggio sondaggio);

    void setTitolo(String titolo);

    void setDescrizione(String descrizione);

    void setTipo(String tipo);

    void setRispostaCorretta(JSONObject rispostaCorretta);
    
    public void setOpzioni(JSONObject opzioni);
    
    public void setPosizione(int posizione);

    public void setObbligatoria(boolean obbligatoria);
    
}
