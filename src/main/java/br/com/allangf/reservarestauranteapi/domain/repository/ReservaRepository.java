package br.com.allangf.reservarestauranteapi.domain.repository;

import br.com.allangf.reservarestauranteapi.domain.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    @Query("select p from Reserva p where p.periodoDaReserva.diaReservado like :hoje")
    List<Reserva> reservasPorData(LocalDate hoje);

    @Query("select p from Reserva p where p.periodoDaReserva.diaReservado >=:dataInicio and p.periodoDaReserva.diaReservado <=:dataFim")
    List<Reserva> reservasPorData(LocalDate dataInicio, LocalDate dataFim);

}
