package br.com.allangf.reservarestauranteapi.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idCliente;

    @Column(name="nomeCliente", length = 100)
    private String nomeCliente;

    @Column(name="sobrenomeCliente", length = 100)
    private String sobrenomeCliente;

    @Column(name="CPFCliente", unique = true)
    @CPF
    private String cpfCliente;

    @Column(name="telefoneCliente", length = 12)
    private String telefoneCliente;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private Set<Reserva> reservas;


}
