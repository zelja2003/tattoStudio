package com.example.salon.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Zaposleni {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ime;

    private String specijalizacija;

    @OneToMany(mappedBy = "zaposleni")
    private Set<Termin> termini;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }

    public String getSpecijalizacija() { return specijalizacija; }
    public void setSpecijalizacija(String specijalizacija) { this.specijalizacija = specijalizacija; }

    public Set<Termin> getTermini() { return termini; }
    public void setTermini(Set<Termin> termini) { this.termini = termini; }
}
