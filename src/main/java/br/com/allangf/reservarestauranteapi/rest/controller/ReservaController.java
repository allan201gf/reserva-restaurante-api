package br.com.allangf.reservarestauranteapi.rest.controller;

import br.com.allangf.reservarestauranteapi.domain.entity.Reserva;
import br.com.allangf.reservarestauranteapi.service.ReservaService;
import br.com.allangf.reservarestauranteapi.rest.dto.ReservaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/restaurante/api/reservas")
public class ReservaController {

    private ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public int saveReserva(@RequestBody ReservaDTO reservaDTO) {
        Reserva reserva = reservaService.salvar(reservaDTO);
        return reserva.getIdReserva();
    }

    @GetMapping("{idReserva}")
    public Reserva getReservaById(@PathVariable int idReserva) {
        return reservaService
                .obterReservaCompleta(idReserva)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "A reserva de id " + idReserva +" não foi encontrado"
                        )
                );
    }

    @GetMapping
    public List<Reserva> listAllReservas() {
        return reservaService.obterTodasAsReservas();
    }

    @DeleteMapping("{idReserva}")
    public void deleteReservaById(@PathVariable int idReserva) {
        reservaService.deleteReserva(idReserva);
    }




}
