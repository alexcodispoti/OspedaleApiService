package com.sanita.ospedale.model;

import jakarta.persistence.*;

@Entity
@Table(name="medici")
public class Medico{
    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;

    String nome;
    String cognome;
    String codicefiscale;
    String matricola;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodicefiscale() {
        return this.codicefiscale;
    }

    public void setCodicefiscale(String codicefiscale) {
        this.codicefiscale = codicefiscale;
    }

    

    //Costruttore
    public Medico(String nome,String cognome, String codicefiscale,String matricola) {
        this.nome=nome;
        this.cognome=cognome;
        this.codicefiscale=codicefiscale;
        this.matricola = matricola;
    }

    //Costruttore vuoto per DB
    public Medico(){

    }

    //Getter and Setter
    public String getMatricola() {
        return matricola;
    }
    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }
    
    
    
}
