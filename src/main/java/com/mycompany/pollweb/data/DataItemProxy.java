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
public interface DataItemProxy {

    void setModified(boolean dirty);

    boolean isModified();
    
}
