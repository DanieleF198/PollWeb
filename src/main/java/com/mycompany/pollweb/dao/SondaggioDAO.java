/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.dao;

import com.mycompany.pollweb.data.DataException;
import com.mycompany.pollweb.model.Sondaggio;
import com.mycompany.pollweb.proxy.SondaggioProxy;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cronio
 */
public interface SondaggioDAO {
    
    public SondaggioProxy createSondaggio();
    
    public Sondaggio getSondaggio(int idSondaggio) throws DataException;
    
    public Sondaggio getSondaggioByIdUtente(int idUtente) throws DataException;
    
    public List<Sondaggio> getSondaggiByIdUtente(int idUtente) throws DataException;
    
    public List<Sondaggio> getSondaggi() throws DataException;
    
    public List<Sondaggio> getSondaggiAdmin() throws DataException;
    
    public ArrayList<Sondaggio> getSondaggiRecenti() throws DataException;
    
    public ArrayList<Sondaggio> getSondaggiMenoRecenti() throws DataException;
    
    public ArrayList<Sondaggio> getSondaggiPopolari() throws DataException;
    
    public ArrayList<Sondaggio> getSondaggiPopolari9() throws DataException;
    
    public void storeSondaggio(Sondaggio sondaggio) throws DataException;
    
    public ArrayList<Sondaggio> getSondaggiPrivati(int idUtente) throws DataException;
    
    public ArrayList<Sondaggio> getSondaggiCompilati(int idUtente) throws DataException;
    
    public ArrayList<Sondaggio> searchSondaggi(ArrayList<Sondaggio> sondaggi, String ricerca) throws DataException;
    
    public void deleteSondaggio(int idSondaggio) throws DataException;
    
}
