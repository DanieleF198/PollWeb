/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.proxy;

import com.mycompany.pollweb.dao.UtenteDAO;
import com.mycompany.pollweb.data.DataException;
import com.mycompany.pollweb.data.DataItemProxy;
import com.mycompany.pollweb.data.DataLayer;
import com.mycompany.pollweb.impl.RispostaImpl;
import com.mycompany.pollweb.model.Utente;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cronio
 */
public class RispostaProxy extends RispostaImpl implements DataItemProxy {

    protected boolean modified;
    protected DataLayer dataLayer;
    protected int idUtente;
    
    public RispostaProxy(DataLayer d) {
        super();
        this.modified = false;
        this.dataLayer = d;
        this.idUtente = 0;
    }
    
    
    @Override
    public Utente getUtente() {
        if (super.getUtente() == null && idUtente > 0) {
            try {
                super.setUtente(((UtenteDAO) dataLayer.getDAO(Utente.class)).getUtente(idUtente));
            } catch (DataException ex) {
                Logger.getLogger(GruppoProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getUtente();
    }
    
    @Override
    public void setUtente(Utente utente) {
        super.setUtente(utente);
        this.idUtente = utente.getKey();
        this.modified = true;
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
    public void setData(Date data){
        super.setData(data);
        this.modified = true;
    }

    @Override
    public void setUsernameUtenteRisposta(String usernameUtenteRisposta) {
        super.setUsernameUtenteRisposta(usernameUtenteRisposta);
        this.modified = true;
    }
    
    @Override
    public void setIpUtenteRisposta(String ipUtenteRisposta) {
        super.setIpUtenteRisposta(ipUtenteRisposta);
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
