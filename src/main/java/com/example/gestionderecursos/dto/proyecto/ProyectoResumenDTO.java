package com.example.gestionderecursos.dto.proyecto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para mostra una vista resumida de un proyecto (Salida de datos).
 * Ideal para ser usados en listas, manteniendo los payloads ligeros y eficientes.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoResumenDTO {

    private Long id;
    private String nombre;
}
