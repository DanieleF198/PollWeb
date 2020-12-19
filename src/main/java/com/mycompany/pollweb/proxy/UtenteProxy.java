/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.proxy;

import com.mycompany.pollweb.data.DataItemProxy;
import com.mycompany.pollweb.data.DataLayer;
import com.mycompany.pollweb.impl.RispostaImpl;
import com.mycompany.pollweb.impl.SondaggioImpl;
import com.mycompany.pollweb.impl.UtenteImpl;
import java.util.List;


/**
 *
 * @author Cronio
 */
public class UtenteProxy extends UtenteImpl implements DataItemProxy  {
    
    protected boolean modified;
    protected DataLayer dataLayer;
    
    public UtenteProxy(DataLayer d) {
        super();
        this.modified = false;
        this.dataLayer = d;
    }
    
    @Override
    public int getIdGruppo(){
        return super.getIdGruppo(); 
    }
    
    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }
    
    @Override
    public void setNome(String newNome){
        super.setNome(newNome);
	this.modified = true;
    }
    
    @Override
    public void setCognome(String newCognome){
        super.setNome(newCognome);
	this.modified = true;
    }
    
    @Override
    public void setUsername(String newUsername){
        super.setNome(newUsername);
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
    public void setEta(int eta) {
        super.setEta(eta);
        this.modified = true;
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
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
