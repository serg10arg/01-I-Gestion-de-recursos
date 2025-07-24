package com.example.gestionderecursos.dto.persona;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para la actualización de una Persona existente (entrada de datos)
 * Define la estructura y validaciones para las peticiones PUT
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonaActualizarDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 150, message = "El nombre no puede tener más de 150 caracteres")
    private String nombre;

    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 120, message = "La edad no puede ser superior a los 120 años")
    private int edad;

    @NotBlank(message = "El correo electronico es obligatorio")
    @Email(message = "El correo electronico no es valido")
    private String correoElectronico;
}
