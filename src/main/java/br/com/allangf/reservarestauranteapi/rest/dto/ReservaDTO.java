package br.com.allangf.reservarestauranteapi.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {

    private int cliente;
    private int mesa;
    private String diaReservado;

}
