/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import data.DataItem;
import impl.RispostaImpl;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Cronio
 */
public interface Risposta extends DataItem<Integer> {
    
    public int getId();
    
    public int getUserId();
    
    public int getPunteggio();

    public Date getData();
    
    public String getNomeUtenteRisposta();

    public void setUserId(int userId);

    public void setPunteggio(int punteggio);

    public void setData(Date data);
    
    public void setNomeUtenteRisposta(String nomeUtenteRisposta);
    
}
