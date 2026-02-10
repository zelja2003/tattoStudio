package com.example.salon.service;

import com.example.salon.model.Usluga;
import com.example.salon.repository.UslugaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UslugaService {

    private final UslugaRepository uslugaRepository;

    public UslugaService(UslugaRepository uslugaRepository) {
        this.uslugaRepository = uslugaRepository;
    }

    public List<Usluga> findAll() {
        return uslugaRepository.findAll();
    }

    public Optional<Usluga> findById(Long id) {
        return uslugaRepository.findById(id);
    }

    public void save(Usluga usluga) {
        uslugaRepository.save(usluga);
    }

    public void deleteById(Long id) {
        uslugaRepository.deleteById(id);
    }
}
