/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.impl;

import com.mycompany.pollweb.data.DataItemImpl;
import com.mycompany.pollweb.model.Gruppo;

/**
 *
 * @author Cronio
 */
public class GruppoImpl extends DataItemImpl<Integer> implements Gruppo {
    
    private String nomeGruppo;

    public GruppoImpl(String nomeGruppo) {
        super();
        this.nomeGruppo = nomeGruppo;
    }
    
    public GruppoImpl() {
        super();
        nomeGruppo = "";
    }

    @Override
    public String getNomeGruppo() {
        return nomeGruppo;
    }

    @Override
    public void setNomeGruppo(String nomeGruppo) {
        this.nomeGruppo = nomeGruppo;
    }
    
}
