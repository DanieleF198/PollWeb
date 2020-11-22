/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.model;

import com.mycompany.pollweb.data.DataItem;
import com.mycompany.pollweb.impl.RispostaImpl;
import com.mycompany.pollweb.impl.SondaggioImpl;
import java.util.List;

/**
 *
 * @author Cronio
 */
public interface Utente extends DataItem<Integer> {
    
    public int getId();
    
    public String getNome();
    
    public String getMail();
    
    public String getPassword();

    public List<SondaggioImpl> getSondaggi();
    
    public List<RispostaImpl> getRisposte();
    
    public int getIdGruppo();
    
    public void setId(int newId);
    
    public void setNome(String newNome);
    
    public void setMail(String newMail);
    
    public void setPassword(String newPassword);
    
    public void setSondaggi(List<SondaggioImpl> sondaggio);
    
    public void setRisposte(List<RispostaImpl> risposta);   
    
    public void setIdGruppo(int idGruppo);
    
}
