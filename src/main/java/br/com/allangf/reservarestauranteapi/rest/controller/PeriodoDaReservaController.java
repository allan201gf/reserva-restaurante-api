package br.com.allangf.reservarestauranteapi.rest.controller;


import br.com.allangf.reservarestauranteapi.domain.entity.PeriodoDaReserva;
import br.com.allangf.reservarestauranteapi.domain.repository.PeriodoDaReservaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/restaurante/api/periodo")
public class PeriodoDaReservaController {

    private PeriodoDaReservaRepository periodoDaReservaRepository;

    public PeriodoDaReservaController(PeriodoDaReservaRepository periodoDaReservaRepository) {
        this.periodoDaReservaRepository = periodoDaReservaRepository;
    }

    @GetMapping
    public List<PeriodoDaReserva> allPeriodoDaReserva() {
        return periodoDaReservaRepository.findAll();
    }

    @GetMapping("/hoje")
    public List<PeriodoDaReserva> reservasDeHoje() {
        LocalDate hoje = LocalDate.now();
        return periodoDaReservaRepository.reservasDeHoje(hoje);
    }

}
