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

/**
 *
 * @author Cronio
 */
public class UtenteImpl extends DataItemImpl<Integer> implements Utente  {
    
    private String nome; 
    private String password;
    private int eta;
    private String email;
    private int idGruppo;
    private List <SondaggioImpl> sondaggi = new ArrayList<>();
    private List <RispostaImpl> risposte = new ArrayList<>();
    
    public UtenteImpl (){
        super();
        this.nome = "";
        this.password = "";
        this.eta = 0;
        this.email = "";
    }
    
    
    @Override
    public String getNome(){
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
    public int getEta() {
        return eta;
    }

    @Override
    public String getEmail() {
        return email;
    }
    
    @Override
    public void setNome(String newNome){
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
    public void setEta(int eta) {
        this.eta = eta;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }
    
}
