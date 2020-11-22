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
    
    private int id;
    private String nome; 
    private String mail;
    private String password;
    private int idGruppo;
    private List <SondaggioImpl> sondaggi = new ArrayList<>();
    private List <RispostaImpl> risposte = new ArrayList<>();

    public UtenteImpl(int id, String nome, String mail, String password) {
        this.id = id;
        this.nome = nome;
        this.mail = mail;
        this.password = password;
    }
    
    @Override
    public int getId(){ //getter
	return id;
    }
    
    @Override
    public String getNome(){
	return nome;
    }
    
    @Override
    public String getMail(){
	return mail;
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

    public int getIdGruppo() {
        return idGruppo;
    }
    
    @Override
    public void setId(int newId){ //setter
	this.id = newId;
    }
    
    @Override
    public void setNome(String newNome){
	this.nome = newNome;
    }
    
    @Override
    public void setMail(String newMail){
	this.mail = newMail;
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

    public void setIdGruppo(int idGruppo) {
        this.idGruppo = idGruppo;
    }
    
}
