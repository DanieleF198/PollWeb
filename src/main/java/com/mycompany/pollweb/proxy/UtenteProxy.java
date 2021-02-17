/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.proxy;

import com.mycompany.pollweb.dao.GruppoDAO;
import com.mycompany.pollweb.data.DataException;
import com.mycompany.pollweb.data.DataItemProxy;
import com.mycompany.pollweb.data.DataLayer;
import com.mycompany.pollweb.impl.RispostaImpl;
import com.mycompany.pollweb.impl.SondaggioImpl;
import com.mycompany.pollweb.impl.UtenteImpl;
import com.mycompany.pollweb.model.Gruppo;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Cronio
 */
public class UtenteProxy extends UtenteImpl implements DataItemProxy  {
    
    protected boolean modified;
    protected DataLayer dataLayer;
    protected int idGruppo = 0;
    
    public UtenteProxy(DataLayer d) {
        super();
        this.modified = false;
        this.dataLayer = d;
        this.idGruppo = 0;
    }
    
    @Override
    public int getIdGruppo(){
        return super.getIdGruppo(); 
    }
    
    @Override
    public Gruppo getGruppo() {
        //notare come il Gruppo in relazione venga caricato solo su richiesta
        if (super.getGruppo() == null && idGruppo > 0) {
            try {
                super.setGruppo(((GruppoDAO) dataLayer.getDAO(Gruppo.class)).getGruppo(idGruppo));
            } catch (DataException ex) {
                Logger.getLogger(GruppoProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getGruppo();
    }
    
    @Override
    public void setGruppo(Gruppo gruppo) {
        super.setGruppo(gruppo);
        this.idGruppo = gruppo.getKey();
        this.modified = true;
    }

    
    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }
    
    @Override
    public void setVersion(long version) {
        super.setVersion(version);
        this.modified = true;
    }
    
    @Override
    public void setNome(String newNome){
        super.setNome(newNome);
	this.modified = true;
    }
    
    @Override
    public void setCognome(String newCognome){
        super.setCognome(newCognome);
	this.modified = true;
    }
    
    @Override
    public void setUsername(String newUsername){
        super.setUsername(newUsername);
	this.modified = true;
    }
    
    @Override
    public void setPassword(String newPassword){
        super.setPassword(newPassword);
	this.modified = true;
    }
    
    @Override
    public void setSondaggi(List<SondaggioImpl> sondaggio) {
        super.setSondaggi(sondaggio);
        this.modified = true;
    }
    
    @Override
    public void setRisposte(List<RispostaImpl> risposta) {
        super.setRisposte(risposta);
        this.modified = true;
    }

    @Override
    public void setIdGruppo(int idGruppo) {
        super.setIdGruppo(idGruppo);
        this.modified = true;
    }

    @Override
    public void setDataNascita(Date dataNascita) {
        super.setDataNascita(dataNascita);
        this.modified = true;
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
        this.modified = true;
    }
    
    @Override
    public void setBloccato(boolean b) {
        super.setBloccato(b);
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
