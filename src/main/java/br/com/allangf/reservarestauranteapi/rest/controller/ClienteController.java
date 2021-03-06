package br.com.allangf.reservarestauranteapi.rest.controller;

import br.com.allangf.reservarestauranteapi.domain.entity.Cliente;
import br.com.allangf.reservarestauranteapi.domain.repository.ClienteRepository;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("Listagem de um cliente por id")
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

    @ApiOperation("Listagem de todos os clientes")
    @GetMapping
    public List<Cliente> allClientes() {
        return clienteRepository.findAll();
    }

    @ApiOperation("Listagem de um cliente por nome")
    @GetMapping("/nome/{nomeCliente}")
    public List<Cliente> findByNomeContaining(@PathVariable String nomeCliente) {
        List<Cliente> byNomeContaining = clienteRepository.findByNomeClienteContaining(nomeCliente);
        return byNomeContaining;
    }

    @ApiOperation("Criar novo cliente")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente createCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @ApiOperation("Deletar cliente por id")
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

    @ApiOperation("Atualizar cliente por id")
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCliente(@PathVariable int id,
                              @RequestBody Cliente cliente) {
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

    // Buscas com Query personalizados

    @ApiOperation("Listagem de clientes com o mesmo nome")
    @GetMapping("/query/{nomeCliente}")
    public List<Cliente> buscaPorNomeCliente(@PathVariable String nomeCliente) {
        List<Cliente> clientesEncontrados = clienteRepository.buscaPorNomeCliente(nomeCliente);
        return clientesEncontrados;
    }



}
