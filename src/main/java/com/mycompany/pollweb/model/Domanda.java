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
    
    int getId ();
    
    String getTitolo();
    
    String getDescrizione();
    
    String getTipo();
    
    JSONObject getRispostaCorretta();
    
    public JSONObject getOpzioni();
    
    public int getPosizione();

    public boolean isObbligatoria();

    void setTitolo(String titolo);

    void setDescrizione(String descrizione);

    void setTipo(String tipo);

    void setRispostaCorretta(JSONObject rispostaCorretta);
    
    public void setOpzioni(JSONObject opzioni);
    
    public void setPosizione(int posizione);

    public void setObbligatoria(boolean obbligatoria);
    
}
