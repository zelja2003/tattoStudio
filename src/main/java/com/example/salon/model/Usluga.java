package com.example.salon.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Usluga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;

    private double cena;

    private int trajanjeMinuta;

    @ManyToMany(mappedBy = "usluge")
    private Set<Termin> termini;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }

    public double getCena() { return cena; }
    public void setCena(double cena) { this.cena = cena; }

    public int getTrajanjeMinuta() { return trajanjeMinuta; }
    public void setTrajanjeMinuta(int trajanjeMinuta) { this.trajanjeMinuta = trajanjeMinuta; }

    public Set<Termin> getTermini() { return termini; }
    public void setTermini(Set<Termin> termini) { this.termini = termini; }
}
