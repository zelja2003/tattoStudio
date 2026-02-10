package com.example.salon.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
public class Termin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate datum;

    private LocalTime vreme;

    @ManyToOne
    @JoinColumn(name = "korisnik_id")
    private Korisnik korisnik;

    @ManyToOne
    @JoinColumn(name = "zaposleni_id")
    private Zaposleni zaposleni;

    @ManyToMany
    @JoinTable(
            name = "termin_usluga",
            joinColumns = @JoinColumn(name = "termin_id"),
            inverseJoinColumns = @JoinColumn(name = "usluga_id")
    )
    private Set<Usluga> usluge;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDatum() { return datum; }
    public void setDatum(LocalDate datum) { this.datum = datum; }

    public LocalTime getVreme() { return vreme; }
    public void setVreme(LocalTime vreme) { this.vreme = vreme; }

    public Korisnik getKorisnik() { return korisnik; }
    public void setKorisnik(Korisnik korisnik) { this.korisnik = korisnik; }

    public Zaposleni getZaposleni() { return zaposleni; }
    public void setZaposleni(Zaposleni zaposleni) { this.zaposleni = zaposleni; }

    public Set<Usluga> getUsluge() { return usluge; }
    public void setUsluge(Set<Usluga> usluge) { this.usluge = usluge; }
}
