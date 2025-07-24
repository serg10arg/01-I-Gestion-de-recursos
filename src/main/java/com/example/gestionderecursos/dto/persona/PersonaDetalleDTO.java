package com.example.gestionderecursos.dto.persona;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para mostrar la información detallada de una Persona (salida de datos)
 * Se utiliza como respuesta en peticiones GET para un recurso específico
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDetalleDTO {

    private Long id;
    private String nombre;
    private int edad;
    private String correoElectronico;
}
