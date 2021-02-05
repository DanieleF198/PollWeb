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
public interface RispostaDomanda extends DataItem<Integer> {
    
    public int getIdRisposta();

    public int getIdDomanda();
    
    public JSONObject getRisposta();
    
    public void setIdRisposta(int idRisposta);

    public void setIdDomanda(int idDomanda);

    public void setRisposta(JSONObject risposta);
    
}
