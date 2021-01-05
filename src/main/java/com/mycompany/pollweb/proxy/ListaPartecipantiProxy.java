/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.proxy;

import com.mycompany.pollweb.dao.SondaggioDAO;
import com.mycompany.pollweb.dao.UtenteDAO;
import com.mycompany.pollweb.data.DataException;
import com.mycompany.pollweb.data.DataItemProxy;
import com.mycompany.pollweb.data.DataLayer;
import com.mycompany.pollweb.impl.ListaPartecipantiImpl;
import com.mycompany.pollweb.model.Sondaggio;
import com.mycompany.pollweb.model.Utente;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cronio
 */
public class ListaPartecipantiProxy extends ListaPartecipantiImpl implements DataItemProxy {
    
    protected boolean modified;
    protected DataLayer dataLayer;
    protected int idUtente;
    protected int idSondaggio;

    public ListaPartecipantiProxy(DataLayer d) {
        super();
        this.modified = false;
        this.dataLayer = d;
        this.idUtente = 0;
        this.idSondaggio = 0;
    }
    
    @Override
    public Utente getUtente() {
        //notare come il Gruppo in relazione venga caricato solo su richiesta
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
    public Sondaggio getSondaggio() {
        //notare come il Gruppo in relazione venga caricato solo su richiesta
        if (super.getSondaggio() == null && idSondaggio > 0) {
            try {
                super.setSondaggio(((SondaggioDAO) dataLayer.getDAO(Sondaggio.class)).getSondaggio(idSondaggio));
            } catch (DataException ex) {
                Logger.getLogger(GruppoProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getSondaggio();
    }
    
    @Override
    public void setSondaggio(Sondaggio sondaggio) {
        super.setSondaggio(sondaggio);
        this.idSondaggio = sondaggio.getKey();
        this.modified = true;
    }
    
    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }
    
    @Override
    public int getIdUtente() {
        return super.getIdUtente(); 
    }

    @Override
    public int getIdSondaggio() {
        return super.getIdSondaggio(); 
    }
    
    @Override
    public String getEmail() {
        return super.getEmail(); 
    }

    @Override
    public void setIdUtente(int idUtente) {
        super.setIdUtente(idUtente);
        this.modified = true;
    }

    @Override
    public void setIdSondaggio(int idSondaggio) {
        super.setIdSondaggio(idSondaggio);
        this.modified = true;
    }
    
    @Override
    public void setEmail(String newEmail) {
        super.setEmail(newEmail);
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
