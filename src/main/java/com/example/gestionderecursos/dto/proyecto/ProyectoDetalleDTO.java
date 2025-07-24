package com.example.gestionderecursos.dto.proyecto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO para la información detallada de un Proyecto (Salida de datos).
 * Se utiliza como respuesta en peticiones GET para un recurso específico.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoDetalleDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaCreacion;
}
