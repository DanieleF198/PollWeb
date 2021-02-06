/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.proxy;

import com.mycompany.pollweb.data.DataItemProxy;
import com.mycompany.pollweb.data.DataLayer;
import com.mycompany.pollweb.impl.RispostaDomandaImpl;
import org.json.JSONObject;

/**
 *
 * @author Cronio
 */
public class RispostaDomandaProxy extends RispostaDomandaImpl implements DataItemProxy {
    
    protected boolean modified;
    protected DataLayer dataLayer;
    protected int idRisposta;
    protected int idDomanda;
    
    public RispostaDomandaProxy(DataLayer d) {
        super();
        this.modified = false;
        this.dataLayer = d;
        this.idRisposta = 0;
        this.idDomanda = 0;
    }
    
    @Override
    public void setIdRisposta(int idRisposta){
        super.setIdRisposta(idRisposta);
        this.modified = true;
    }

    @Override
    public void setIdDomanda(int idDomanda){
        super.setIdDomanda(idDomanda);
        this.modified = true;
    }
 
    @Override
    public void setRisposta(JSONObject risposta){
        super.setRisposta(risposta);
        this.modified = true;
    }
    
    
    
    //METODI ESCLUSIVI DEL PROXY
    @Override
    public void setModified(boolean dirty) {
        this.modified = dirty;
    }

    @Override
    public boolean isModified() {
        return modified;
    }
    
}
