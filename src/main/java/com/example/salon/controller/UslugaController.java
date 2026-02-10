package com.example.salon.controller;

import com.example.salon.model.Usluga;
import com.example.salon.service.UslugaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usluge")
public class UslugaController {
    private final UslugaService uslugaService;
    public UslugaController(UslugaService uslugaService) {
        this.uslugaService = uslugaService;
    }
    @GetMapping
    public String prikaziSve(Model model) {
        model.addAttribute("usluge", uslugaService.findAll());
        return "usluge/lista";
    }
    @GetMapping("/nova")
    public String novaUsluga(Model model) {
        model.addAttribute("usluga", new Usluga());
        return "usluge/forma";
    }
    @PostMapping("/sacuvaj")
    public String sacuvajUslugu(@ModelAttribute Usluga usluga) {
        uslugaService.save(usluga);
        return "redirect:/usluge";
    }
    @GetMapping("/izmeni/{id}")
    public String izmeniUslugu(@PathVariable Long id, Model model) {
        Usluga usluga = uslugaService.findById(id).orElseThrow();
        model.addAttribute("usluga", usluga);
        return "usluge/forma";
    }
    @GetMapping("/obrisi/{id}")
    public String obrisiUslugu(@PathVariable Long id) {
        uslugaService.deleteById(id);
        return "redirect:/usluge";
    }
}
