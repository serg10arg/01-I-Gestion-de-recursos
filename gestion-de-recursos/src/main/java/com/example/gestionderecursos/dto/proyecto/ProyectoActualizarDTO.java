package com.example.gestionderecursos.dto.proyecto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para la actualización de un Proyecto existente (Entrada de datos)
 * Define la estructura y validaciones para las peticiones PUT
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoActualizarDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 150, message = "El nombre no puede tener más de 150 caracteres")
    private String nombre;

    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres")
    private String descripcion;
}
