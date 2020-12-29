/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pollweb.impl;

import com.mycompany.pollweb.data.DataItemImpl;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.pollweb.model.Utente;
import java.util.Date; //vediamo sql

/**
 *
 * @author Cronio
 */
public class UtenteImpl extends DataItemImpl<Integer> implements Utente  {
    
    private String nome; 
    private String cognome; //new
    private String username; //new
    private String password;
    private Date dataNascita;
    private String email;
    private int idGruppo;
    private List <SondaggioImpl> sondaggi = new ArrayList<>();
    private List <RispostaImpl> risposte = new ArrayList<>();
    
    public UtenteImpl (){
        super();
        this.nome = "";
        this.cognome ="";
        this.password = "";
        this.dataNascita = null;
        this.email = "";
        this.idGruppo = 0;
        this.username = "";
    }
    
    
    @Override
    public String getNome(){ //getter
	return nome;
    }
    
    @Override
    public String getPassword(){
	return password;
    }

    @Override
    public List<SondaggioImpl> getSondaggi() {
        return sondaggi;
    }
    
    @Override
    public List<RispostaImpl> getRisposte() {
        return risposte;
    }

    @Override
    public int getIdGruppo() {
        return idGruppo;
    }

    @Override
    public Date getDataNascita() {
        return dataNascita;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getCognome() {
        return cognome;
    }

    @Override
    public String getUsername() {
        return username;
    }
    
    @Override
    public void setNome(String newNome){ //setter
	this.nome = newNome;
    }
    
    @Override
    public void setPassword(String newPassword){
	this.password = newPassword;
    }
    
    @Override
    public void setSondaggi(List<SondaggioImpl> sondaggio) {
        this.sondaggi = sondaggio;
    }
    
    @Override
    public void setRisposte(List<RispostaImpl> risposta) {
        this.risposte = risposta;
    }

    @Override
    public void setIdGruppo(int idGruppo) {
        this.idGruppo = idGruppo;
    }

    @Override
    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }
    
}
