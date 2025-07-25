package com.example.gestionderecursos.excepciones;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Clase DTO inmutable y estandarizada para las respuestas de error de la API REST.
 */

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private final LocalDateTime fechaHora;

    private final int estado;
    private final String mensaje;
    private final List<String> errores;

    /**
     * Constructor canónico para la deserialización de JSON y uso interno.
     * @param fechaHora La fecha y hora del error.
     * @param estado El código de estado HTTP.
     * @param mensaje Un mensaje general sobre el error.
     * @param errores Una lista de mensajes de error específicos.
     */
    @JsonCreator
    public ApiError(@JsonProperty("fechaHora") LocalDateTime fechaHora,
                    @JsonProperty("estado") int estado,
                    @JsonProperty("mensaje") String mensaje,
                    @JsonProperty("errores") List<String> errores) {
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.mensaje = mensaje;
        this.errores = errores;
    }

    public ApiError(HttpStatus estado, String mensaje, List<String> errores) {
        this.fechaHora = LocalDateTime.now();
        this.estado = estado.value();
        this.mensaje = mensaje;
        this.errores = errores;
    }

    public ApiError(HttpStatus estado, String mensaje, String error) {
        this.fechaHora = LocalDateTime.now();
        this.estado = estado.value();
        this.mensaje = mensaje;
        this.errores = List.of(error);
    }
}
