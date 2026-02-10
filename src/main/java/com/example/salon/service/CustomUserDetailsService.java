package com.example.salon.service;

import com.example.salon.model.Korisnik;
import com.example.salon.repository.KorisnikRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final KorisnikRepository korisnikRepository;

    public CustomUserDetailsService(KorisnikRepository korisnikRepository) {
        this.korisnikRepository = korisnikRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Korisnik korisnik = korisnikRepository.findByEmail(email);
        if (korisnik == null) {
            throw new UsernameNotFoundException("Nepostojeći korisnik");
        }

        return new User(
                korisnik.getEmail(),
                korisnik.getLozinka(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + korisnik.getRola().getNaziv()))
        );
    }
}
