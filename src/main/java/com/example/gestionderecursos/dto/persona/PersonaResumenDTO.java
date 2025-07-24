package com.example.gestionderecursos.dto.persona;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para mostrar una vista resumida de una Persona (Salida de datos)
 * Ideal para ser usados en listas, manteniendo los payloads ligeros y eficientes
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonaResumenDTO {

    private Long id;
    private String nombre;
    private String correoElectronico;
}
