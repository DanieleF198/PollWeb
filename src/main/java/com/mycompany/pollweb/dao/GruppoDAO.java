/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.dao;

import com.mycompany.pollweb.data.DataException;
import com.mycompany.pollweb.model.Gruppo;
import com.mycompany.pollweb.proxy.GruppoProxy;
import java.util.List;

/**
 *
 * @author Cronio
 */
public interface GruppoDAO {
    
    public GruppoProxy createGruppo();
    
    Gruppo getGruppo(int article_key) throws DataException;
    
    public List<Gruppo> getGruppi() throws DataException;
    
    public void storeGruppo(Gruppo gruppo) throws DataException;
    
}
