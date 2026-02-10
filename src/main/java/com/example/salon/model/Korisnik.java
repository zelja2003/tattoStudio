package com.example.salon.model;

import jakarta.persistence.*;

@Entity
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ime;

    @Column(unique = true)
    private String email;

    private String lozinka;


    @ManyToOne
    @JoinColumn(name = "rola_id")
    private Rola rola;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getLozinka() { return lozinka; }
    public void setLozinka(String lozinka) { this.lozinka = lozinka; }

    public Rola getRola() { return rola; }
    public void setRola(Rola rola) { this.rola = rola; }
}
