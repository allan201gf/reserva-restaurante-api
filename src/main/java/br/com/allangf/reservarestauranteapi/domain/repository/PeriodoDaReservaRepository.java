package br.com.allangf.reservarestauranteapi.domain.repository;

import br.com.allangf.reservarestauranteapi.domain.entity.PeriodoDaReserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodoDaReservaRepository extends JpaRepository<PeriodoDaReserva, Integer> {
}
