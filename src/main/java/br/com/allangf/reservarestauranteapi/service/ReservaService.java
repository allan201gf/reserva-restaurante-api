package br.com.allangf.reservarestauranteapi.service;

import br.com.allangf.reservarestauranteapi.domain.entity.Cliente;
import br.com.allangf.reservarestauranteapi.domain.entity.Mesa;
import br.com.allangf.reservarestauranteapi.domain.entity.Reserva;
import br.com.allangf.reservarestauranteapi.rest.dto.ReservaDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservaService {

    Reserva salvar(ReservaDTO dto);

    Optional<Reserva> obterReservaCompleta(int id);

    List<Reserva> obterTodasAsReservas();

    void deleteReserva(int id);

    List<Reserva> reservasPorData();

    List<Reserva> reservasPorData(String dataInicio, String dataFim);

    List<Mesa> mesasDisponiveis(String data);

}
