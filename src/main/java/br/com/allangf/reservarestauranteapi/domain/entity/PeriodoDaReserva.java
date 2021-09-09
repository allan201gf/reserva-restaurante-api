package br.com.allangf.reservarestauranteapi.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name="periodoDaReserva")
@AllArgsConstructor
@NoArgsConstructor
public class PeriodoDaReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idPeriodoDaReserva;

    @Column(name="dia_reservado")
    private LocalDate diaReservado;

    @ManyToOne
    @JoinColumn(name = "mesa_id_mesa")
    private Mesa mesa;

}


/*
 * Muda o tipo de relacionamento da reserva com a mesa, sendo necessário
 * criar uma nova tabela para registrar os horários em que a mesa estará
 * reservada.
 *
 * Nessa nova tabela de reserva de mesas, teremos os campos:
 * | id | idMesa | inicioPeriodoReserva | fimPeriodoReserva |
 * idMesa = 1 AND periodo BETWEEN inicioPeriodoReserva AND fimPeriodoReserva
 * findByMesaAndPeriodo:
 * */
