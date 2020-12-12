/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.dao;

import com.mycompany.pollweb.data.DataException;
import com.mycompany.pollweb.model.Utente;
import com.mycompany.pollweb.proxy.UtenteProxy;
import java.util.List;

/**
 *
 * @author Cronio
 */
public interface UtenteDAO {
    
    public UtenteProxy createUtente();
    
    Utente getUtente(int idUtente) throws DataException;
    
    public List<Utente> getUtenti() throws DataException;
    
    public void storeUtente(Utente utente) throws DataException;
    
}