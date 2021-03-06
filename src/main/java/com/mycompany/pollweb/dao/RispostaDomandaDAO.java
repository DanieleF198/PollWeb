/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.dao;

import com.mycompany.pollweb.data.DataException;
import com.mycompany.pollweb.model.RispostaDomanda;
import com.mycompany.pollweb.proxy.RispostaDomandaProxy;
import java.util.List;

/**
 *
 * @author Cronio
 */
public interface RispostaDomandaDAO {
    
    public RispostaDomandaProxy createRispostaDomanda();
    
    public RispostaDomanda getRispostaDomanda(int idRisposta,int idDomanda) throws DataException;
    
    public List<RispostaDomanda> getRispostaDomandaByDomandaId(int idDomanda) throws DataException;
    
    public void insertRisposta(RispostaDomanda rispostaDomanda) throws DataException;
    
    public void updateRisposta(RispostaDomanda rispostaDomanda) throws DataException;
    
}
