/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.proxy;

import com.mycompany.pollweb.data.DataItemProxy;
import com.mycompany.pollweb.data.DataLayer;
import com.mycompany.pollweb.impl.UtenteImpl;

/**
 *
 * @author Cronio
 */
public class UtenteProxy extends UtenteImpl implements DataItemProxy  {
    
    protected boolean modified;
    protected DataLayer dataLayer;
    
    
    public UtenteProxy(DataLayer d) {
        super();
        this.modified = false;
        this.dataLayer = d;
    }
    
    @Override
    public void setKey(Integer key) {
        super.setKey(key);
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
