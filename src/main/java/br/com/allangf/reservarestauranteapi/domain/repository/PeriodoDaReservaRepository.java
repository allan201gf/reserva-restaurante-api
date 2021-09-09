package br.com.allangf.reservarestauranteapi.domain.repository;

import br.com.allangf.reservarestauranteapi.domain.entity.PeriodoDaReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PeriodoDaReservaRepository extends JpaRepository<PeriodoDaReserva, Integer> {

    @Query("select p from PeriodoDaReserva p where p.diaReservado like :hoje")
    List<PeriodoDaReserva> reservasDeHoje(LocalDate hoje);

}
