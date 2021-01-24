/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.proxy;

import com.mycompany.pollweb.dao.SondaggioDAO;
import com.mycompany.pollweb.data.DataException;
import com.mycompany.pollweb.data.DataItemProxy;
import com.mycompany.pollweb.data.DataLayer;
import com.mycompany.pollweb.impl.DomandaImpl;
import com.mycompany.pollweb.model.Sondaggio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Cronio
 */
public class DomandaProxy extends DomandaImpl implements DataItemProxy {
    
    protected boolean modified;
    protected DataLayer dataLayer;
    protected int idSondaggio;
    
    public DomandaProxy(DataLayer d) {
        super();
        this.modified = false;
        this.dataLayer = d;
        this.idSondaggio = 0;
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
    public int getIdSondaggio(){
        return super.getIdSondaggio(); 
    }
    
    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }
    
    @Override
    public void setTitolo(String titolo) {
        super.setTitolo(titolo);
        this.modified = true;
    }

    @Override
    public void setDescrizione(String descrizione) {
        super.setDescrizione(descrizione);
        this.modified = true;
    }

    @Override
    public void setTipo(String tipo) {
        super.setTipo(tipo);
        this.modified = true;
    }

    @Override
    public void setRispostaCorretta(JSONObject rispostaCorretta) {
        super.setRispostaCorretta(rispostaCorretta);
        this.modified = true;
    }

    @Override
    public void setOpzioni(JSONObject opzioni) {
        super.setOpzioni(opzioni);
        this.modified = true;
    }

    @Override
    public void setPosizione(int posizione) {
        super.setPosizione(posizione);
        this.modified = true;
    }
    
    @Override
    public void setVincolo(String vincolo) {
        super.setVincolo(vincolo);
        this.modified = true;
    }

    @Override
    public void setObbligatoria(boolean obbligatoria) {
        super.setObbligatoria(obbligatoria);
        this.modified = true;
    }
    
    @Override
    public void setIdSondaggio(int idSondaggio) {
        super.setIdSondaggio(idSondaggio);
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
