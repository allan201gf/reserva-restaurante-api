package br.com.allangf.reservarestauranteapi.rest.controller;

import br.com.allangf.reservarestauranteapi.domain.entity.Cliente;
import br.com.allangf.reservarestauranteapi.domain.repository.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/restaurante/api/clientes")
public class ClienteController {

    private ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("{idCliente}")
    public Cliente getClienteById(@PathVariable int idCliente) {
        return clienteRepository
                .findById(idCliente)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "O cliente de id " + idCliente + " não foi encontrado"
                        )
                );
    }

    @GetMapping
    public List<Cliente> allClientes() {
        return clienteRepository.findAll();
    }

    @GetMapping("/nome/{nomeCliente}")
    public List<Cliente> findByNomeContaining(@PathVariable String nomeCliente) {
        List<Cliente> byNomeContaining = clienteRepository.findByNomeClienteContaining(nomeCliente);
        return byNomeContaining;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente createCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCliente(@PathVariable int id) {

//        clienteRepository.deleteById(id);

        clienteRepository.findById(id)
                .map(cliente -> {
                    clienteRepository.delete(cliente);
                    return cliente;
                })
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "O cliente de id " + id + " não foi encontrado"
                        )
                );
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCliente(@PathVariable int id,
                              @RequestBody Cliente cliente) {

/*      TODO
        Outra forma de fazer o código abaixo
        cliente.setIdCliente(id);
        clienteRepository.save(cliente);
*/

/*      TODO
        Utilizar os sets para alterar apenas o que foi enviado na requisição, utilizar if ou condição ternaria
*/
        clienteRepository.findById(id)
                .map(clienteExistente -> {
                    cliente.setIdCliente(clienteExistente.getIdCliente());
                    clienteRepository.save(cliente);
                    return clienteExistente;
                }).orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "O cliente de id " + id + " não foi encontrado"
                        )
                );
    }


}
