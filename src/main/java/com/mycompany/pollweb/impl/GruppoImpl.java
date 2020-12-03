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
    
    private int idGruppo;
    private String nomeGruppo;

    public GruppoImpl(int idGruppo, String nomeGruppo) {
        super();
        this.idGruppo = idGruppo;
        this.nomeGruppo = nomeGruppo;
    }
    
    public GruppoImpl() {
        super();
        idGruppo = 0;
        nomeGruppo = "";
    }

    @Override
    public int getId() { //getter
        return idGruppo;
    }

    @Override
    public String getNomeGruppo() {
        return nomeGruppo;
    }
    
}
