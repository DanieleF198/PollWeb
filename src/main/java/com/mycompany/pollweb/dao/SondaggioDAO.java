/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.dao;

import com.mycompany.pollweb.data.DataException;
import com.mycompany.pollweb.model.Sondaggio;
import com.mycompany.pollweb.proxy.SondaggioProxy;
import java.util.List;

/**
 *
 * @author Cronio
 */
public interface SondaggioDAO {
    
    public SondaggioProxy createSondaggio();
    
    Sondaggio getSondaggio(int idSondaggio) throws DataException;
    
    public Sondaggio getSondaggioByIdUtente(int idUtente) throws DataException;
    
    public List<Sondaggio> getSondaggi() throws DataException;
    
    public void storeSondaggio(Sondaggio sondaggio) throws DataException;
    
}
