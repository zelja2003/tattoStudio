package com.example.salon.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Rola {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;

    @OneToMany(mappedBy = "rola")
    private Set<Korisnik> korisnici;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }

    public Set<Korisnik> getKorisnici() { return korisnici; }
    public void setKorisnici(Set<Korisnik> korisnici) { this.korisnici = korisnici; }
}
