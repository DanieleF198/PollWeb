/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.model;

import com.mycompany.pollweb.data.DataItem;
import java.util.List;

/**
 *
 * @author Cronio
 */
public interface ListaPartecipanti extends DataItem<Integer> {

    public int getIdUtente();

    public int getIdSondaggio();
    
    public Utente getUtente();
    
    public Sondaggio getSondaggio();
    
    public String getEmail();

    public void setIdUtente(int utenteId);

    public void setIdSondaggio(int sondaggioId);
    
    public void setEmail(String newEmail);
    
    public void setUtente (Utente utente);
    
    public void setSondaggio (Sondaggio sondaggio);
    
}
