/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.model;

import com.mycompany.pollweb.data.DataItem;

/**
 *
 * @author Cronio
 */
public interface Gruppo extends DataItem<Integer> {

    public String getNomeGruppo();
    
    public void setNomeGruppo(String nomeGruppo);

}
