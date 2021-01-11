/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.dao;

import com.mycompany.pollweb.data.DataException;
import com.mycompany.pollweb.model.ListaPartecipanti;
import com.mycompany.pollweb.proxy.ListaPartecipantiProxy;
import java.util.List;

/**
 *
 * @author Cronio
 */
public interface ListaPartecipantiDAO {
    
    public ListaPartecipantiProxy createListaPartecipanti();
    
    public ListaPartecipanti getListaPartecipanti(int idListaPartecipanti) throws DataException;
    
    public List<ListaPartecipanti> getListePartecipanti() throws DataException;
    
    public void storeListaPartecipanti(ListaPartecipanti listaPartecipanti) throws DataException;
    
}
