/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.data;

/**
 *
 * @author Cronio
 */
public class DataItemProxyImpl extends DataItemImpl implements DataItemProxy {
    
    private boolean modified;

    public DataItemProxyImpl() {
        this.modified = false;
    }

    @Override
    public void setModified(boolean dirty) {
        this.modified = dirty;
    }

    @Override
    public boolean isModified() {
        return modified;
    }
    
}
