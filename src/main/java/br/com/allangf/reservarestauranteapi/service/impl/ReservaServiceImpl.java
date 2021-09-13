package br.com.allangf.reservarestauranteapi.service.impl;

import br.com.allangf.reservarestauranteapi.domain.entity.Cliente;
import br.com.allangf.reservarestauranteapi.domain.entity.Mesa;
import br.com.allangf.reservarestauranteapi.domain.entity.Reserva;
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
import java.time.format.DateTimeFormatter;
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
                                "O cliente de id " + idCliente + " não foi encontrado"));

        int idMesa = reservaDTO.getMesa();

        Mesa mesa = mesaRepository
                .findById(idMesa)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "A mesa de id " + idMesa + " não foi encontrada"));


        LocalDate dataformatada = converterData(reservaDTO.getDiaReservado());

        // validação dentro do método salvar para verificar se a mesa já está reservada no dia e horá desejado

        //TODO

        List<Reserva> todasAsReservas = reservaRrepository.findAll();

        for (Reserva reserva: todasAsReservas) {

            if (reserva.getMesa().equals(mesa) && reserva.getDiaReservado().equals(dataformatada)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "A mesa " + reserva.getMesa().getNomeMesa() + " de id " + reserva.getMesa().getIdMesa() + " já está reservada para o dia " + reserva.getDiaReservado());
            }

        }

        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setMesa(mesa);
        reserva.setDiaReservado(dataformatada);
        reserva.setDataReservaCriada(LocalDate.now());
        reserva.setStauts(StatusPedido.RESERVADO);
        reservaRrepository.save(reserva);
        return reserva;

    }

    // Converte data no formato com barras para o formato LocalDate

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

    // Método para deletar uma reserva e chama o método para deletar o periodo da reserva acima

    @Override
    public void deleteReserva(int idReserva) {

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

    @Override
    public List<Reserva> reservasPorData() {
        LocalDate hoje = LocalDate.now();
        return reservaRrepository.reservasPorData(hoje);
    }

    @Override
    public List<Reserva> reservasPorData(String dataInicio, String dataFim) {

        LocalDate hoje = LocalDate.now();

        if (dataInicio.equals("hoje") && dataFim.equals("hoje") ) {
            return reservaRrepository.reservasPorData(hoje);
        } else {

            LocalDate dataInicioFormatada = converterDataComTraco(dataInicio);
            LocalDate dataFimFormatada = converterDataComTraco(dataFim);

            return reservaRrepository.reservasPorData(dataInicioFormatada, dataFimFormatada);

        }
    }

    // Converte data no formato com tracos para o formato LocalDate

    public LocalDate converterDataComTraco (String data) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(data, formato);
    }

}
