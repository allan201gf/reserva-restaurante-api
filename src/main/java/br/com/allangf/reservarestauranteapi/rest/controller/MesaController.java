package br.com.allangf.reservarestauranteapi.rest.controller;

import br.com.allangf.reservarestauranteapi.domain.entity.Mesa;
import br.com.allangf.reservarestauranteapi.domain.repository.MesaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
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
                                "A mesa de id " + idMesa + " não foi encontrado"
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
                                "A mesa de id " + idMesa + " não foi encontrada"
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
                                "A mesa de id " + idMesa + " não foi encontrada"
                        )
                );
    }

    // Sobe 10 mesas no momento do start do projeto
    @PostConstruct
    @ResponseStatus(HttpStatus.CREATED)
    public void cria5mesa() {

        Mesa mesa1 = new Mesa();
        mesa1.setNomeMesa("Mesa01");
        mesaRepository.save(mesa1);

        Mesa mesa2 = new Mesa();
        mesa2.setNomeMesa("mesa02");
        mesaRepository.save(mesa2);

        Mesa mesa3 = new Mesa();
        mesa3.setNomeMesa("mesa03");
        mesaRepository.save(mesa3);

        Mesa mesa4 = new Mesa();
        mesa4.setNomeMesa("mesa04");
        mesaRepository.save(mesa4);

        Mesa mesa5 = new Mesa();
        mesa5.setNomeMesa("mesa05");
        mesaRepository.save(mesa5);

        Mesa mesa6 = new Mesa();
        mesa6.setNomeMesa("mesa06");
        mesaRepository.save(mesa6);

        Mesa mesa7 = new Mesa();
        mesa7.setNomeMesa("mesa07");
        mesaRepository.save(mesa7);

        Mesa mesa8 = new Mesa();
        mesa8.setNomeMesa("mesa08");
        mesaRepository.save(mesa8);

        Mesa mesa9 = new Mesa();
        mesa9.setNomeMesa("mesa09");
        mesaRepository.save(mesa9);

        Mesa mesa10 = new Mesa();
        mesa10.setNomeMesa("mesa10");
        mesaRepository.save(mesa10);
    }


}
