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

    @Override
    public String getNomeGruppoByID(int idGruppo) {
        switch (idGruppo) {
            case 1:
                return "Utente base";
            case 2:
                return "Responsabile";
            case 3:
                return "Admin";
            default:
                return "error";
        }
    }
    
}
