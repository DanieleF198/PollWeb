/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.model;

import com.mycompany.pollweb.data.DataItem;
import com.mycompany.pollweb.impl.RispostaImpl;
import com.mycompany.pollweb.impl.SondaggioImpl;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Cronio
 */
public interface Utente extends DataItem<Integer> {
    
    public String getNome();
    
    public String getPassword();

    public List<SondaggioImpl> getSondaggi();
    
    public List<RispostaImpl> getRisposte();
    
    public int getIdGruppo();
    
    public Date getDataNascita();

    public String getEmail();
    
    public String getCognome();
    
    public String getUsername();
    
    public Gruppo getGruppo();
    
    public boolean isBloccato();
    
    public void setNome(String newNome);
    
    public void setPassword(String newPassword);
    
    public void setSondaggi(List<SondaggioImpl> sondaggio);
    
    public void setRisposte(List<RispostaImpl> risposta);   
    
    public void setIdGruppo(int idGruppo);

    public void setDataNascita(Date dataNascita);

    public void setEmail(String email);
    
    public void setCognome (String cognome);
    
    public void setUsername (String username);
    
    public void setGruppo (Gruppo gruppo);
    
    public void setBloccato (boolean b);
    
    public int calculateAge ();   
}
