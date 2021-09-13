package br.com.allangf.reservarestauranteapi.domain.entity;

import br.com.allangf.reservarestauranteapi.domain.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="reserva")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idReserva;

    @ManyToOne
    @JoinColumn(name="cliente_id")
    private Cliente cliente;

    @Column(name="dataReservaCriada")
    private LocalDate dataReservaCriada;

    @Column(name = "dia_reservado")
    private LocalDate diaReservado;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private StatusPedido stauts;

    @OneToOne
    @JoinColumn(name="mesa_id")
    private Mesa mesa;

}
