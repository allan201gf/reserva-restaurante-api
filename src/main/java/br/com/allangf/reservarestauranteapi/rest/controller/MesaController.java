package br.com.allangf.reservarestauranteapi.rest.controller;

import br.com.allangf.reservarestauranteapi.domain.entity.Mesa;
import br.com.allangf.reservarestauranteapi.domain.repository.MesaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/restaurante/api/mesas")
public class MesaController {

    private MesaRepository mesaRepository;

    public MesaController(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    @GetMapping("{idMesa}")
    public Mesa getMesaById(@PathVariable int idMesa) {
        return mesaRepository
                .findById(idMesa)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "A mesa de id: " + idMesa + " não foi encontrado"
                        )
                );
    }

    @GetMapping
    public List<Mesa> allMesas() {
        return mesaRepository.findAll();
    }

    @GetMapping("/nome/{nomeMesa}")
    public List<Mesa> findByNomeMesaContaining(@PathVariable String nomeMesa) {
        List<Mesa> mesasEncontradas = mesaRepository.findByNomeMesaContaining(nomeMesa);
        return mesasEncontradas;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mesa createMesa(@RequestBody Mesa mesa) {
        return mesaRepository.save(mesa);
    }

    @DeleteMapping("{idMesa}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMesa(@PathVariable int idMesa) {
        mesaRepository.findById(idMesa)
                .map(mesaEncontrada -> {
                    mesaRepository.delete(mesaEncontrada);
                    return mesaEncontrada;
                })
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "A mesa de id: " + idMesa + " não foi encontrada"
                        )
                );
    }

    @PutMapping("{idMesa}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMesa(@PathVariable int idMesa,
                           @RequestBody Mesa mesa) {
        mesaRepository.findById(idMesa)
                .map(mesaExistente -> {
                    mesa.setIdMesa(mesaExistente.getIdMesa());
                    mesaRepository.save(mesa);
                    return mesaExistente;
                }).orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "A mesa de id: " + idMesa + " não foi encontrada"
                        )
                );
    }


}
