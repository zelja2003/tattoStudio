package com.example.salon.controller;

import com.example.salon.model.Zaposleni;
import com.example.salon.service.ZaposleniService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/zaposleni")
public class ZaposleniController {
    private final ZaposleniService zaposleniService;
    public ZaposleniController(ZaposleniService zaposleniService) {
        this.zaposleniService = zaposleniService;
    }
    @GetMapping
    public String prikaziSve(Model model) {
        model.addAttribute("zaposleniLista", zaposleniService.findAll());
        return "zaposleni/lista";
    }
    @GetMapping("/novi")
    public String formaNovi(Model model) {
        model.addAttribute("zaposleni", new Zaposleni());
        return "zaposleni/forma";
    }
    @PostMapping("/sacuvaj")
    public String sacuvaj(@ModelAttribute Zaposleni zaposleni) {
        zaposleniService.save(zaposleni);
        return "redirect:/zaposleni";
    }
    @GetMapping("/izmeni/{id}")
    public String formaIzmeni(@PathVariable Long id, Model model) {
        Zaposleni zaposleni = zaposleniService.findById(id).orElseThrow();
        model.addAttribute("zaposleni", zaposleni);
        return "zaposleni/forma";
    }
    @GetMapping("/obrisi/{id}")
    public String obrisi(@PathVariable Long id) {
        zaposleniService.deleteById(id);
        return "redirect:/zaposleni";
    }
}
