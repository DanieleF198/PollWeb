/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impl;

import data.DataItemImpl;
import model.Gruppo;

/**
 *
 * @author Cronio
 */
public class GruppoImpl extends DataItemImpl<Integer> implements Gruppo {
    
    private String id;
    private String nomeGruppo;

    public GruppoImpl(String id, String nomeGruppo) {
        this.id = id;
        this.nomeGruppo = nomeGruppo;
    }

    @Override
    public String getId() { //getter
        return id;
    }

    @Override
    public String getNomeGruppo() {
        return nomeGruppo;
    }
    
}
