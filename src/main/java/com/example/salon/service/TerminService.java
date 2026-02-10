package com.example.salon.service;

import com.example.salon.model.Termin;
import com.example.salon.model.Usluga;
import com.example.salon.repository.TerminRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class TerminService {

    private final TerminRepository terminRepository;

    public TerminService(TerminRepository terminRepository) {
        this.terminRepository = terminRepository;
    }

    public List<Termin> findAll() {
        return terminRepository.findAll();
    }
    public List<Termin> findByKorisnickoIme(String username) {
        return terminRepository.findByKorisnik_Email(username);
    }

    public List<Termin> findByZaposleniIdAndDatum(Long id, LocalDate datum) {
        return terminRepository.findByZaposleniIdAndDatum(id, datum);
    }




    public Optional<Termin> findById(Long id) {
        return terminRepository.findById(id);
    }
    public boolean postojiPreklapanje(Termin noviTermin) {
        if (noviTermin.getUsluge() == null || noviTermin.getUsluge().isEmpty()) {
            return false; 
        }

        
        Usluga usluga = noviTermin.getUsluge().stream().findFirst().orElse(null);
        if (usluga == null) return false;

        int trajanjeNovi = usluga.getTrajanjeMinuta();
        LocalTime startNovi = noviTermin.getVreme();
        LocalTime krajNovi = startNovi.plusMinutes(trajanjeNovi);

        List<Termin> sviTermini = terminRepository.findByZaposleniId(noviTermin.getZaposleni().getId());

        for (Termin postojeci : sviTermini) {
            if (postojeci.getDatum().equals(noviTermin.getDatum())) {
                Usluga uslugaPostojeca = postojeci.getUsluge().stream().findFirst().orElse(null);
                if (uslugaPostojeca == null) continue;

                int trajanjePostojeci = uslugaPostojeca.getTrajanjeMinuta();
                LocalTime startPostojeci = postojeci.getVreme();
                LocalTime krajPostojeci = startPostojeci.plusMinutes(trajanjePostojeci);

                if (startNovi.isBefore(krajPostojeci) && startPostojeci.isBefore(krajNovi)) {
                    return true;
                }
            }
        }

        return false;
    }



    public void save(Termin termin) {
        terminRepository.save(termin);
    }

    public void deleteById(Long id) {
        terminRepository.deleteById(id);
    }
}
