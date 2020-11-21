/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import data.DataItem;

/**
 *
 * @author Cronio
 */
public interface Domanda extends DataItem<Integer> {
    
    int getId ();
    
    String getTitolo();
    
    String getDescrizione();
    
    String getTipo();
    
    String getRispostaCorretta();
    
    public int getPosizione();

    public boolean isObbligatoria();

    void setTitolo(String titolo);

    void setDescrizione(String descrizione);

    void setTipo(String tipo);

    void setRispostaCorretta(String rispostaCorretta);
    
    public void setPosizione(int posizione);

    public void setObbligatoria(boolean obbligatoria);
    
}
