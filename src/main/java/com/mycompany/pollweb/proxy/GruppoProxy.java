/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.proxy;

import com.mycompany.pollweb.data.DataItemProxy;
import com.mycompany.pollweb.data.DataLayer;
import com.mycompany.pollweb.impl.GruppoImpl;


/**
 *
 * @author Cronio
 */
public class GruppoProxy extends GruppoImpl implements DataItemProxy {
    
    protected boolean modified;
    protected DataLayer dataLayer;

    public GruppoProxy(DataLayer d) {
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
    public void setVersion(long version) {
        super.setVersion(version);
        this.modified = true;
    }
    
    @Override
    public void setNomeGruppo(String nomeGruppo){
        super.setNomeGruppo(nomeGruppo);
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
