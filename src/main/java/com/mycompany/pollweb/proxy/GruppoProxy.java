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
    public void setModified(boolean arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isModified() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
