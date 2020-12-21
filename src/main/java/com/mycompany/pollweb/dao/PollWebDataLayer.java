/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.dao;

import com.mycompany.pollweb.data.DataException;
import com.mycompany.pollweb.data.DataLayer;
import com.mycompany.pollweb.model.Domanda;
import com.mycompany.pollweb.model.Gruppo;
import com.mycompany.pollweb.model.ListaPartecipanti;
import com.mycompany.pollweb.model.Risposta;
import com.mycompany.pollweb.model.Sondaggio;
import com.mycompany.pollweb.model.Utente;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author Cronio
 */
public class PollWebDataLayer extends DataLayer {
    
    public PollWebDataLayer(DataSource datasource) throws SQLException {
        super(datasource);
    }
    
    @Override
    public void init() throws DataException {
        //registriamo i nostri dao
        registerDAO(Gruppo.class, new GruppoDAO_MySQL(this));
        registerDAO(Utente.class, new UtenteDAO_MySQL(this));
        registerDAO(Domanda.class, new UtenteDAO_MySQL(this));
        registerDAO(ListaPartecipanti.class, new UtenteDAO_MySQL(this));
        registerDAO(Sondaggio.class, new UtenteDAO_MySQL(this));
        registerDAO(Risposta.class, new UtenteDAO_MySQL(this));
    }
    
    //helpers 
    public GruppoDAO getGruppoDAO() {
        return (GruppoDAO) getDAO(Gruppo.class);
    }
      
    public UtenteDAO getUtenteDAO() {
        return (UtenteDAO) getDAO(Utente.class);
    }
    
    public DomandaDAO getDomandaDAO() {
        return (DomandaDAO) getDAO(Domanda.class);
    }
    
    public ListaPartecipantiDAO getListaPartecipantiDAO() {
        return (ListaPartecipantiDAO) getDAO(ListaPartecipanti.class);
    }
    
    public SondaggioDAO getSondaggioDAO() {
        return (SondaggioDAO) getDAO(Sondaggio.class);
    }
    
    public RispostaDAO getRispostaDAO() {
        return (RispostaDAO) getDAO(Risposta.class);
    }
}
