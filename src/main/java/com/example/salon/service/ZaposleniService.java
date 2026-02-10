package com.example.salon.service;

import com.example.salon.model.Zaposleni;
import com.example.salon.repository.ZaposleniRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ZaposleniService {

    private final ZaposleniRepository zaposleniRepository;

    public ZaposleniService(ZaposleniRepository zaposleniRepository) {
        this.zaposleniRepository = zaposleniRepository;
    }

    public List<Zaposleni> findAll() {
        return zaposleniRepository.findAll();
    }

    public Optional<Zaposleni> findById(Long id) {
        return zaposleniRepository.findById(id);
    }

    public void save(Zaposleni zaposleni) {
        zaposleniRepository.save(zaposleni);
    }

    public void deleteById(Long id) {
        zaposleniRepository.deleteById(id);
    }
}
