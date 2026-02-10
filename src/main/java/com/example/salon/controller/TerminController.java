package com.example.salon.controller;

import com.example.salon.model.Termin;
import com.example.salon.model.Usluga;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import com.example.salon.model.Zaposleni;
import com.example.salon.model.Korisnik;
import com.example.salon.service.TerminService;
import com.example.salon.service.UslugaService;
import com.example.salon.service.ZaposleniService;
import com.example.salon.service.KorisnikService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/termini")
public class TerminController {
    private final TerminService terminService;
    private final ZaposleniService zaposleniService;
    private final UslugaService uslugaService;
    private final KorisnikService korisnikService;
    public TerminController(
            TerminService terminService,
            ZaposleniService zaposleniService,
            UslugaService uslugaService,
            KorisnikService korisnikService
    ) {
        this.terminService = terminService;
        this.zaposleniService = zaposleniService;
        this.uslugaService = uslugaService;
        this.korisnikService = korisnikService;
    }
    @GetMapping
    public String prikaziSve(Model model) {
        model.addAttribute("termini", terminService.findAll());
        return "termini/lista";
    }
    @GetMapping("/novi")
    public String formaNoviTermin(
            @RequestParam(required = false) Long uslugaId,
            @RequestParam(required = false) Long zaposleniId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datum,
            Model model,
            Principal principal
    ) {
        model.addAttribute("termin", new Termin());
        model.addAttribute("zaposleniLista", zaposleniService.findAll());
        model.addAttribute("uslugeLista", uslugaService.findAll());
        model.addAttribute("korisniciLista", korisnikService.findAll());
        if (principal != null) {
            Korisnik ulogovani = korisnikService.findByEmail(principal.getName());
            model.addAttribute("ulogovaniKorisnik", ulogovani);
        }
        if (uslugaId != null) {
            Optional<Usluga> usluga = uslugaService.findById(uslugaId);
            if (usluga.isPresent()) {
                int trajanje = usluga.get().getTrajanjeMinuta();
                List<LocalTime> sviSlotovi = generisiSlotove(trajanje);

                if (zaposleniId != null && datum != null) {
                    List<Termin> zauzeti = terminService.findByZaposleniIdAndDatum(zaposleniId, datum);

                    List<LocalTime> dostupni = sviSlotovi.stream()
                            .filter(slot -> zauzeti.stream().noneMatch(t -> t.getVreme().equals(slot)))
                            .toList();

                    model.addAttribute("vremena", dostupni);
                } else {
                    model.addAttribute("vremena", sviSlotovi);
                }

                model.addAttribute("odabranaUslugaId", uslugaId);
            }
        }
        return "termini/forma";
    }
    private List<LocalTime> generisiSlotove(int trajanjeMinuta) {
        List<LocalTime> slotovi = new ArrayList<>();
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(20, 0).minusMinutes(trajanjeMinuta);

        while (!start.isAfter(end)) {
            slotovi.add(start);
            start = start.plusMinutes(trajanjeMinuta);
        }

        return slotovi;
    }
    @GetMapping("/moji")
    public String mojiTermini(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";

        String username = principal.getName();
        List<Termin> termini = terminService.findByKorisnickoIme(username);

        model.addAttribute("termini", termini);
        model.addAttribute("praznaLista", termini.isEmpty());

        return "termini/moji";
    }
    @PostMapping("/sacuvaj")
    public String sacuvajTermin(@ModelAttribute Termin termin, Model model, Principal principal) {
        Korisnik ulogovani = korisnikService.findByEmail(principal.getName());

        if (ulogovani.getRola().getNaziv().equals("KORISNIK")) {
            termin.setKorisnik(ulogovani);
        }

        if (terminService.postojiPreklapanje(termin)) {
            model.addAttribute("termin", termin);
            model.addAttribute("zaposleniLista", zaposleniService.findAll());
            model.addAttribute("uslugeLista", uslugaService.findAll());
            model.addAttribute("korisniciLista", korisnikService.findAll());
            model.addAttribute("vremena", List.of());
            model.addAttribute("greska", "Termin se preklapa sa postojećim!");
            return "termini/moji";
        }

        terminService.save(termin);
        return "redirect:/termini/moji";
    }
   @GetMapping("/izmeni/{id}")
    public String formaIzmeni(@PathVariable Long id, Model model) {
        Termin termin = terminService.findById(id).orElseThrow();
        model.addAttribute("termin", termin);
        model.addAttribute("zaposleniLista", zaposleniService.findAll());
        model.addAttribute("uslugeLista", uslugaService.findAll());
        model.addAttribute("korisniciLista", korisnikService.findAll());
        return "termini/forma";
    }
    @GetMapping("/obrisi/{id}")
    public String obrisi(@PathVariable Long id) {
        terminService.deleteById(id);
        return "redirect:/termini";
    }
}
