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
    
    public List<DomandaImpl> getDomande();

    public int getIdUtente();
    
    public Utente getUtente();
    
    public String getTitolo();
    
    public Date getCreazione ();
    
    public Date getScadenza();
    
    public String getTestoApertura();

    public String getTestoChiusura();
    
    public int getCompilazioni();
    
    public boolean isCompleto();

    public boolean isVisibilita();
    
    public boolean isPrivato();
    
    public boolean isModificabile();
  
    public void setModificabile(boolean modificabile);
   
    public void setPrivato(boolean privato);

    public void setDomande(List<DomandaImpl> domande);

    public void setIdUtente(int idUtente);
    
    public void setUtente(Utente utente);
    
    public void setTitolo (String titolo);

    public void setCreazione(Date creazione);

    public void setScadenza(Date chiusura);
    
    public void setTestoApertura(String testoApertura);

    public void setTestoChiusura(String testoChiusura);
    
    public void setCompleto(boolean completo);

    public void setVisibilita(boolean visibilita);
    
    public void setCompilazioni(int compilazioni);
    
}
