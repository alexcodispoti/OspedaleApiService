package com.sanita.ospedale.model;



import jakarta.persistence.*;

@Entity
@Table(name="pazienti")
public class Paziente {
    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;

    //Dichiaro gli attributi dell'oggetto Paziente
    String nome;
    String cognome;
    String codicefiscale;

    @ManyToOne
    private Medico medico;

    

    public Paziente(Medico medico) {
        this.medico = medico;
    }
    public Medico getMedico() {
        return medico;
    }
    public void setMedico(Medico medico) {
        this.medico = medico;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    public String getCodicefiscale() {
        return codicefiscale;
    }
    public void setCodicefiscale(String codicefiscale) {
        if (codicefiscale == null) {
            // Assegna un valore predefinito o genera un codice fiscale temporaneo
            this.codicefiscale = "CODICE_FISCALE_MANCANTE";
        } else {
            this.codicefiscale = codicefiscale;
        }    }
    //Getter and Setter
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    //Costruttore
    public Paziente(String nome, String cognome, String codicefiscale) {
        this.nome = nome;
        this.cognome = cognome;
        this.codicefiscale = codicefiscale;
    }
    //Costruttore vuoto per database
    public Paziente(){}
}
