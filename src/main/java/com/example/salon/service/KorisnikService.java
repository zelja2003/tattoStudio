package com.example.salon.service;

import com.example.salon.model.Korisnik;
import com.example.salon.repository.KorisnikRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KorisnikService {

    private final KorisnikRepository korisnikRepository;
    private final PasswordEncoder passwordEncoder;

    public KorisnikService(KorisnikRepository korisnikRepository, PasswordEncoder passwordEncoder) {
        this.korisnikRepository = korisnikRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Korisnik> findAll() {
        return korisnikRepository.findAll();
    }
    public Korisnik findByEmail(String email) {
        return korisnikRepository.findByEmail(email);
    }

    public void registrujNovogKorisnika(Korisnik korisnik) {
        String sifrovanaLozinka = passwordEncoder.encode(korisnik.getLozinka());
        korisnik.setLozinka(sifrovanaLozinka);
        korisnikRepository.save(korisnik);
    }
}
