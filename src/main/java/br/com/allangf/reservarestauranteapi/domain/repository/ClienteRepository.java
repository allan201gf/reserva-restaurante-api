package br.com.allangf.reservarestauranteapi.domain.repository;

import br.com.allangf.reservarestauranteapi.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByNomeClienteContaining(String nomeCliente);

    // Fazendo consultas com @Query

    @Query("select c from Cliente c where c.nomeCliente = :nomeCliente")
    List<Cliente> buscaPorNomeCliente(String nomeCliente);


}
