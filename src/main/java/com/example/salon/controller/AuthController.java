package com.example.salon.controller;

import com.example.salon.model.Korisnik;
import com.example.salon.model.Rola;
import com.example.salon.service.KorisnikService;
import com.example.salon.repository.RolaRepository;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class AuthController {

    private final KorisnikService korisnikService;
    private final RolaRepository rolaRepository;

    public AuthController(KorisnikService korisnikService, RolaRepository rolaRepository) {
        this.korisnikService = korisnikService;
        this.rolaRepository = rolaRepository;
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("korisnik", principal.getName());
        } else {
            model.addAttribute("korisnik", "Gost");
        }
        return "home";
    }
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminPanel() {
        return "admin";
    }
    @GetMapping("/register")
    public String prikaziRegistracionuFormu(Model model) {
        model.addAttribute("korisnik", new Korisnik());
        return "register";
    }
    @PostMapping("/register")
    public String registrujKorisnika(@ModelAttribute Korisnik korisnik) {
        Rola korisnikRola = rolaRepository.findByNaziv("KORISNIK");
        korisnik.setRola(korisnikRola);
        korisnikService.registrujNovogKorisnika(korisnik);
        return "redirect:/login";
    }
}
