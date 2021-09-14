package br.com.allangf.reservarestauranteapi.rest.controller;

import br.com.allangf.reservarestauranteapi.domain.entity.Cliente;
import br.com.allangf.reservarestauranteapi.domain.entity.Mesa;
import br.com.allangf.reservarestauranteapi.domain.entity.Reserva;
import br.com.allangf.reservarestauranteapi.service.ReservaService;
import br.com.allangf.reservarestauranteapi.rest.dto.ReservaDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/restaurante/api/reservas")
public class ReservaController {

    private ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }


    @ApiOperation("Cria uma reserva")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public int saveReserva(@RequestBody ReservaDTO reservaDTO) {
        Reserva reserva = reservaService.salvar(reservaDTO);
        return reserva.getIdReserva();
    }

    @ApiOperation("Exibe reserva por id")
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

    @ApiOperation("Lista de todas as reservas")
    @GetMapping
    public List<Reserva> listAllReservas() {
        return reservaService.obterTodasAsReservas();
    }

    @ApiOperation("Deleta uma reserva por id")
    @DeleteMapping("{idReserva}")
    public void deleteReservaById(@PathVariable int idReserva) {
        reservaService.deleteReserva(idReserva);
    }


    @ApiOperation("Busca de reservas por data")
    @GetMapping("/reservasPorData")
    public List<Reserva> reservasPorData(@RequestParam(value = "dataInicio", defaultValue = "hoje") String dataInicio, @RequestParam(value = "dataFim", defaultValue = "hoje") String dataFim) {
        return reservaService.reservasPorData(dataInicio, dataFim);
    }

    @ApiOperation("Lista mesas disponíveis por data")
    @GetMapping("/mesasDisponiveis")
    public List<Mesa> mesasDisponiveis(@RequestParam(value = "dia", defaultValue = "hoje") String data) {
        return reservaService.mesasDisponiveis(data);
    }


}
