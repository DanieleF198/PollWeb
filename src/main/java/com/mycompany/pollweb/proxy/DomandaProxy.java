/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.proxy;

import com.mycompany.pollweb.data.DataItemProxy;
import com.mycompany.pollweb.data.DataLayer;
import com.mycompany.pollweb.impl.DomandaImpl;
import org.json.JSONObject;

/**
 *
 * @author Cronio
 */
public class DomandaProxy extends DomandaImpl implements DataItemProxy {
    
    protected boolean modified;
    protected DataLayer dataLayer;
    
    public DomandaProxy(DataLayer d) {
        super();
        this.modified = false;
        this.dataLayer = d;
    }
    
    @Override
    public int getIdSondaggio(){
        return super.getIdSondaggio(); 
    }
    
    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }
    
    @Override
    public void setTitolo(String titolo) {
        super.setTitolo(titolo);
        this.modified = true;
    }

    @Override
    public void setDescrizione(String descrizione) {
        super.setDescrizione(descrizione);
        this.modified = true;
    }

    @Override
    public void setTipo(String tipo) {
        super.setTipo(tipo);
        this.modified = true;
    }

    @Override
    public void setRispostaCorretta(JSONObject rispostaCorretta) {
        super.setRispostaCorretta(rispostaCorretta);
        this.modified = true;
    }

    @Override
    public void setOpzioni(JSONObject opzioni) {
        super.setOpzioni(opzioni);
        this.modified = true;
    }

    @Override
    public void setPosizione(int posizione) {
        super.setPosizione(posizione);
        this.modified = true;
    }

    @Override
    public void setObbligatoria(boolean obbligatoria) {
        super.setObbligatoria(obbligatoria);
        this.modified = true;
    }
    
    @Override
    public void setIdSondaggio(int idSondaggio) {
        super.setIdSondaggio(idSondaggio);
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
