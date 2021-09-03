package br.com.allangf.reservarestauranteapi.service.impl;

import br.com.allangf.reservarestauranteapi.domain.entity.Cliente;
import br.com.allangf.reservarestauranteapi.domain.entity.Mesa;
import br.com.allangf.reservarestauranteapi.domain.entity.Reserva;
import br.com.allangf.reservarestauranteapi.domain.enums.StatusMesa;
import br.com.allangf.reservarestauranteapi.domain.enums.StatusPedido;
import br.com.allangf.reservarestauranteapi.domain.repository.ClienteRepository;
import br.com.allangf.reservarestauranteapi.domain.repository.MesaRepository;
import br.com.allangf.reservarestauranteapi.domain.repository.ReservaRepository;
import br.com.allangf.reservarestauranteapi.rest.dto.ReservaDTO;
import br.com.allangf.reservarestauranteapi.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRrepository;
    private final ClienteRepository clienteRepository;
    private final MesaRepository mesaRepository;


    @Override
    @Transactional
    public Reserva salvar(ReservaDTO reservaDTO) {
        int idCliente = reservaDTO.getCliente();

        Cliente cliente = clienteRepository
                .findById(idCliente)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "O cliente de id: " + idCliente + " não foi encontrado"));

        int idMesa = reservaDTO.getMesa();

        Mesa mesa = mesaRepository
                .findById(idMesa)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "A mesa de id: " + idMesa + " não foi encontrada"));

        if (mesa.getStautsMesa() != StatusMesa.DISPONIVEL) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Esta mesa ja está reservada para outro cliente");

        }

        mesa.setStautsMesa(StatusMesa.RESERVADA);

        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setMesa(mesa);
        reserva.setDiaReservado(reservaDTO.getDiaReservado());
        reserva.setDataReservaCriada(LocalDate.now());
        reserva.setStauts(StatusPedido.RESERVADO);

        reservaRrepository.save(reserva);

        return reserva;

    }

    @Override
    public Optional<Reserva> obterReservaCompleta(int id) {
        return reservaRrepository.findById(id);
    }

    @Override
    public List<Reserva> obterTodasAsReservas() {
        return reservaRrepository.findAll();
    }

    @Override
    public void deleteReserva(int idReserva) {

        reservaRrepository.findById(idReserva).map(reserva -> {
            reserva.getMesa().setStautsMesa(StatusMesa.DISPONIVEL);
            return reserva;
        });

        reservaRrepository.findById(idReserva)
                .map(reserva -> {
                    reservaRrepository.delete(reserva);
                    return reserva;
                })
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "A reserva de id " + idReserva + " não foi encontrada"
                        )
                );

    }
}
