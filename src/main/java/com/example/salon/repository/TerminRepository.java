package com.example.salon.repository;

import com.example.salon.model.Termin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TerminRepository extends JpaRepository<Termin, Long> {
    List<Termin> findByZaposleniId(Long zaposleniId); // <--- dodaj ovo
    List<Termin> findByZaposleniIdAndDatum(Long zaposleniId, LocalDate datum);
    List<Termin> findByKorisnik_Email(String email);

}
