/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.dao;

import com.mycompany.pollweb.data.DataException;
import com.mycompany.pollweb.model.Domanda;
import com.mycompany.pollweb.proxy.DomandaProxy;
import java.util.List;

/**
 *
 * @author Cronio
 */
public interface DomandaDAO {
    
    public DomandaProxy createDomanda();
    
    Domanda getDomanda(int idSondaggio) throws DataException;
    
    public Domanda getDomandaByIdSondaggio(int idSondaggio) throws DataException;
    
    public List<Domanda> getDomande() throws DataException;
    
    public void storeDomanda(Domanda domanda) throws DataException;
    
}
