package br.com.allangf.reservarestauranteapi.domain.repository;

import br.com.allangf.reservarestauranteapi.domain.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

}
