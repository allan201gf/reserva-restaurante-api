package br.com.allangf.reservarestauranteapi.domain.entity;

import br.com.allangf.reservarestauranteapi.domain.enums.StatusMesa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name="mesa")
@AllArgsConstructor
@NoArgsConstructor
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idMesa;

    private String nomeMesa;

    @Enumerated(EnumType.STRING)
    private StatusMesa stautsMesa;

    /*
    * TODO
    * Muda o tipo de relacionamento da reserva com a mesa, sendo necessário
    * criar uma nova tabela para registrar os horários em que a mesa estará
    * reservada.
    *
    * Nessa nova tabela de reserva de mesas, teremos os campos:
    * | id | idMesa | inicioPeriodoReserva | fimPeriodoReserva |
    * idMesa = 1 AND periodo BETWEEN inicioPeriodoReserva AND fimPeriodoReserva
    * findByMesaAndPeriodo:
    * */

}
