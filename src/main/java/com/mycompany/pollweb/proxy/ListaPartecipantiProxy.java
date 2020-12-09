/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.proxy;

import com.mycompany.pollweb.data.DataItemProxy;
import com.mycompany.pollweb.data.DataLayer;
import com.mycompany.pollweb.impl.ListaPartecipantiImpl;

/**
 *
 * @author Cronio
 */
public class ListaPartecipantiProxy extends ListaPartecipantiImpl implements DataItemProxy {
    
    protected boolean modified;
    protected DataLayer dataLayer;

    public ListaPartecipantiProxy(DataLayer d) {
        super();
        this.modified = false;
        this.dataLayer = d;
    }
    
    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }
    
    @Override
    public int getIdUtente() {
        return super.getIdUtente(); 
    }

    @Override
    public int getIdSondaggio() {
        return super.getIdSondaggio(); 
    }

    @Override
    public void setIdUtente(int idUtente) {
        super.setIdUtente(idUtente);
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
