/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.model;

import com.mycompany.pollweb.data.DataItem;
import com.mycompany.pollweb.impl.DomandaImpl;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Cronio
 */
public interface Sondaggio extends DataItem<Integer> {
    
    public int getId();
    
    public boolean isQuiz();

    public List<DomandaImpl> getDomande();

    public int getUserId();
    
    public Date getCreazione ();
    
    public Date getScadenza();
    
    public String getTestoApertura();

    public String getTestoChiusura();
    
    public void setId(int newId);

    public void setQuiz(boolean quiz);

    public void setDomande(List<DomandaImpl> domande);

    public void setUserId(int userId);

    public void setCreazione(Date creazione);

    public void setScadenza(Date chiusura);
    
    public void setTestoApertura(String testoApertura);

    public void setTestoChiusura(String testoChiusura);
    
}
