package com.example.salon.repository;

import com.example.salon.model.Rola;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolaRepository extends JpaRepository<Rola, Long> {
    Rola findByNaziv(String naziv);
}
