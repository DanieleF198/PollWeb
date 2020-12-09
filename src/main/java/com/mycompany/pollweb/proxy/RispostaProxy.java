/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.proxy;

import com.mycompany.pollweb.data.DataItemProxy;
import com.mycompany.pollweb.data.DataLayer;
import com.mycompany.pollweb.impl.RispostaImpl;
import java.util.Date;

/**
 *
 * @author Cronio
 */
public class RispostaProxy extends RispostaImpl implements DataItemProxy {

    protected boolean modified;
    protected DataLayer dataLayer;
    
    public RispostaProxy(DataLayer d) {
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
    public void setIdUtente(int idUtente) {
        super.setIdUtente(idUtente);
        this.modified = true;
    }

    @Override
    public void setPunteggio(int punteggio) {
        super.setPunteggio(punteggio);
        this.modified = true;
    }

    @Override
    public void setData(Date data){
        super.setData(data);
        this.modified = true;
    }

    @Override
    public void setNomeUtenteRisposta(String nomeUtenteRisposta) {
        super.setNomeUtenteRisposta(nomeUtenteRisposta);
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
