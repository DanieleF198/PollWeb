/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impl;

import data.DataItemImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Sondaggio;

/**
 *
 * @author Cronio
 */
public class SondaggioImpl extends DataItemImpl<Integer> implements Sondaggio {
    
    private int id;
    private int userId;
    private boolean quiz;
    private List <DomandaImpl> domande = new ArrayList<>();
    private String testoApertura;
    private String testoChiusura;
    private Date creazione;
    private Date scadenza; 

    public SondaggioImpl(int id, int userId, boolean quiz, Date creazione, Date scadenza ,String testoApertura,String testoChiusura) {
        this.id = id;
        this.userId = userId;
        this.quiz = quiz;
        this.creazione = creazione;
        this.scadenza = scadenza;
        this.testoApertura = testoApertura;
        this.testoChiusura = testoChiusura;
    }
    
    @Override
    public int getId(){ //getter
	return id;
    }
    
    @Override
    public boolean isQuiz(){
	return quiz;
    }

    @Override
    public List<DomandaImpl> getDomande() {
        return domande;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public Date getCreazione() {
        return creazione;
    }

    @Override
    public Date getScadenza() {
        return scadenza;
    }

    public String getTestoApertura() {
        return testoApertura;
    }

    public String getTestoChiusura() {
        return testoChiusura;
    }
    
    @Override
    public void setId(int newId){ //setter
	this.id = newId;
    }

    @Override
    public void setQuiz(boolean quiz) {
        this.quiz = quiz;
    }

    @Override
    public void setDomande(List<DomandaImpl> domande) {
        this.domande = domande;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public void setCreazione(Date creazione) {
        this.creazione = creazione;
    }

    @Override
    public void setScadenza(Date scadenza) {
        this.scadenza = scadenza;
    }

    public void setTestoApertura(String testoApertura) {
        this.testoApertura = testoApertura;
    }

    public void setTestoChiusura(String testoChiusura) {
        this.testoChiusura = testoChiusura;
    }
    
}
