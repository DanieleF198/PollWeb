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
import com.mycompany.pollweb.impl.DomandaImpl;
import com.mycompany.pollweb.impl.SondaggioImpl;
import com.mycompany.pollweb.model.Utente;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cronio
 */
public class SondaggioProxy extends SondaggioImpl implements DataItemProxy   {
    
    protected boolean modified;
    protected DataLayer dataLayer;
    protected int idUtente;
    
    public SondaggioProxy(DataLayer d) {
        super();
        this.modified = false;
        this.dataLayer = d;
        this.idUtente = 0;
    }
    
    @Override
    public int getIdUtente() {
        return super.getIdUtente(); 
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
    public void setTitolo(String titolo){
        super.setTitolo(titolo);
        this.modified = true;
    }
    
    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }
    
    @Override
    public void setQuiz(boolean quiz) {
        super.setQuiz(quiz);
        this.modified = true;
    }

    @Override
    public void setDomande(List<DomandaImpl> domande) {
        super.setDomande(domande);
        this.modified = true;
    }

    @Override
    public void setIdUtente(int idUtente) {
        super.setIdUtente(idUtente);
        this.modified = true;
    }

    @Override
    public void setCreazione(Date creazione) {
        super.setCreazione(creazione);
        this.modified = true;
    }

    @Override
    public void setScadenza(Date scadenza) {
        super.setScadenza(scadenza);
        this.modified = true;
    }

    @Override
    public void setTestoApertura(String testoApertura) {
        super.setTestoApertura(testoApertura);
        this.modified = true;
    }

    @Override
    public void setTestoChiusura(String testoChiusura) {
        super.setTestoChiusura(testoChiusura);
        this.modified = true;
    }

    @Override
    public void setCompleto(boolean completo) {
        super.setCompleto(completo);
        this.modified = true;
    }

    @Override
    public void setVisibilita(boolean visibilita) {
        super.setVisibilita(visibilita);
        this.modified = true;
    }
    
    @Override
    public void setPrivato(boolean privato) {
        super.setPrivato(privato);
        this.modified = true;
    }
    
    @Override
    public void setModificabile(boolean modificabile){
        super.setModificabile(modificabile);
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
