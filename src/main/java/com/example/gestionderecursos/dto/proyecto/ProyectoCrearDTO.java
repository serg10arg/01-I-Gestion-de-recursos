package com.example.gestionderecursos.dto.proyecto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para la creación de un nuevo Proyecto (Entrada de datos)
 * Define la estructura y validaciones para peticiones POST.
 * No incluye el ID, ya que es generado por el sistema
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoCrearDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 150, message = "El nombre no puede tener más de 150 caracteres")
    private String nombre;

    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres")
    private String descripcion;
}
