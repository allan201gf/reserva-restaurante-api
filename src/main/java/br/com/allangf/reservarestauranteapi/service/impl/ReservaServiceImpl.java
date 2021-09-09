package br.com.allangf.reservarestauranteapi.service.impl;

import br.com.allangf.reservarestauranteapi.domain.entity.Cliente;
import br.com.allangf.reservarestauranteapi.domain.entity.Mesa;
import br.com.allangf.reservarestauranteapi.domain.entity.PeriodoDaReserva;
import br.com.allangf.reservarestauranteapi.domain.entity.Reserva;
import br.com.allangf.reservarestauranteapi.domain.enums.StatusPedido;
import br.com.allangf.reservarestauranteapi.domain.repository.ClienteRepository;
import br.com.allangf.reservarestauranteapi.domain.repository.MesaRepository;
import br.com.allangf.reservarestauranteapi.domain.repository.PeriodoDaReservaRepository;
import br.com.allangf.reservarestauranteapi.domain.repository.ReservaRepository;
import br.com.allangf.reservarestauranteapi.rest.dto.ReservaDTO;
import br.com.allangf.reservarestauranteapi.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRrepository;
    private final ClienteRepository clienteRepository;
    private final MesaRepository mesaRepository;
    private final PeriodoDaReservaRepository periodoDaReservaRepository;


    @Override
    @Transactional
    public Reserva salvar(ReservaDTO reservaDTO) {
        int idCliente = reservaDTO.getCliente();

        Cliente cliente = clienteRepository
                .findById(idCliente)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "O cliente de id " + idCliente + " não foi encontrado"));

        int idMesa = reservaDTO.getMesa();

        Mesa mesa = mesaRepository
                .findById(idMesa)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "A mesa de id " + idMesa + " não foi encontrada"));


        LocalDate dataformatada = converterData(reservaDTO.getDiaReservado());

        //Validação por mesa e dia
        List<PeriodoDaReserva> todosOsPeriodos = periodoDaReservaRepository.findAll();

        for (PeriodoDaReserva periodo: todosOsPeriodos) {

            if (periodo.getMesa().equals(mesa) && periodo.getDiaReservado().equals(dataformatada)) {

                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "A mesa de id " + periodo.getMesa().getIdMesa() + " ja está reservada para o dia " + periodo.getDiaReservado());

            }

        }

        PeriodoDaReserva periodoDaReserva = periodoDaReservaRepository
                .findById(createPeriodoReserva(dataformatada, mesa))
                .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));

        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setMesa(mesa);
        reserva.setPeriodoDaReserva(periodoDaReserva);
        reserva.setDataReservaCriada(LocalDate.now());
        reserva.setStauts(StatusPedido.RESERVADO);
        reservaRrepository.save(reserva);
        return reserva;

    }

    public int createPeriodoReserva(LocalDate date, Mesa mesa) {

        PeriodoDaReserva periodoDaReserva = new PeriodoDaReserva();
        periodoDaReserva.setDiaReservado(date);
        periodoDaReserva.setMesa(mesa);
        periodoDaReservaRepository.save(periodoDaReserva);
        return periodoDaReserva.getIdPeriodoDaReserva();

    }

    public void deletePeriodoReserva(int id) {
        periodoDaReservaRepository.findById(id)
                .map(periodoEncontrado -> {
                    periodoDaReservaRepository.delete(periodoEncontrado);
                    return periodoEncontrado;
                })
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "A reserva de id " + id + " não foi encontrada"
                        )
                );
    }

    public LocalDate converterData (String data) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(data, formato);
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

        AtomicInteger idPeriodoReserva = new AtomicInteger();

        reservaRrepository.findById(idReserva).map(reserva -> {
            idPeriodoReserva.set(reserva.getPeriodoDaReserva().getIdPeriodoDaReserva());
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

            deletePeriodoReserva(idPeriodoReserva.get());

    }

    @Override
    public List<Reserva> reservasPorData() {
        LocalDate hoje = LocalDate.now();
        return reservaRrepository.reservasPorData(hoje);
    }

    @Override
    public List<Reserva> reservasPorData(String dataInicio, String dataFim) {

        LocalDate dataInicioFormatada = converterDataComTraco(dataInicio);
        LocalDate dataFimFormatada = converterDataComTraco(dataFim);

        return reservaRrepository.reservasPorData(dataInicioFormatada, dataFimFormatada);
    }

    public LocalDate converterDataComTraco (String data) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(data, formato);
    }

}
