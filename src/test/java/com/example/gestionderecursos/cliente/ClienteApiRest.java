package com.example.gestionderecursos.cliente;

import com.example.gestionderecursos.dto.proyecto.ProyectoCrearDTO;
import com.example.gestionderecursos.dto.proyecto.ProyectoDetalleDTO;
import com.example.gestionderecursos.dto.proyecto.ProyectoResumenDTO;
import com.example.gestionderecursos.excepciones.ApiError;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Prueba de integración automatizada para el API REST de Proyectos.
 * Inicia un servidor web en un puerto aleatorio y realiza peticiones HTTP reales.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Para ejecutar pruebas en un orden específico
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Para compartir estado entre pruebas
public class ClienteApiRest {

    private static final Logger log = LoggerFactory.getLogger(ClienteApiRest.class);

    @Autowired
    private TestRestTemplate restTemplate; // Inyectado por Spring, conoce el puerto aleatorio

    private Long proyectoCreadoId; // Para compartir el ID entre pruebas ordenadas

    @Test
    @Order(1)
    void deberiaCrearNuevoProyecto() {
        log.info("--- PRUEBA 1: Creando un nuevo proyecto ---");
        ProyectoCrearDTO nuevoProyecto = new ProyectoCrearDTO("Proyecto de Integración", "Prueba E2E");

        ResponseEntity<ProyectoDetalleDTO> respuesta = restTemplate.postForEntity(
                "/api/proyectos", nuevoProyecto, ProyectoDetalleDTO.class);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertNotNull(respuesta.getHeaders().getLocation());

        ProyectoDetalleDTO proyectoCreado = respuesta.getBody();
        assertNotNull(proyectoCreado);
        assertNotNull(proyectoCreado.getId());
        assertEquals("Proyecto de Integración", proyectoCreado.getNombre());

        this.proyectoCreadoId = proyectoCreado.getId(); // Guardamos el ID para la siguiente prueba
        log.info("Proyecto creado con éxito. ID: {}", proyectoCreadoId);
    }

    @Test
    @Order(2)
    void deberiaObtenerProyectoPorId() {
        assertNotNull(proyectoCreadoId, "El ID del proyecto no debería ser nulo. La prueba de creación debe ejecutarse primero.");
        log.info("--- PRUEBA 2: Obteniendo proyecto con ID: {} ---", proyectoCreadoId);

        ResponseEntity<ProyectoDetalleDTO> respuesta = restTemplate.getForEntity(
                "/api/proyectos/" + proyectoCreadoId, ProyectoDetalleDTO.class);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        ProyectoDetalleDTO proyectoObtenido = respuesta.getBody();
        assertNotNull(proyectoObtenido);
        assertEquals(proyectoCreadoId, proyectoObtenido.getId());
        log.info("Proyecto obtenido: {}", proyectoObtenido.getNombre());
    }

    @Test
    @Order(3)
    void deberiaListarProyectosPaginados() {
        log.info("--- PRUEBA 3: Obteniendo la lista paginada de proyectos ---");

        // Definimos el tipo de respuesta esperado, incluyendo nuestra clase de ayuda
        ParameterizedTypeReference<RestResponsePage<ProyectoResumenDTO>> tipoRespuesta =
                new ParameterizedTypeReference<>() {};

        ResponseEntity<RestResponsePage<ProyectoResumenDTO>> respuesta = restTemplate.exchange(
                "/api/proyectos?size=5&sort=nombre,asc",
                HttpMethod.GET,
                null,
                tipoRespuesta);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        RestResponsePage<ProyectoResumenDTO> paginaProyectos = respuesta.getBody();
        assertNotNull(paginaProyectos);
        assertFalse(paginaProyectos.getContent().isEmpty(), "La lista de proyectos no debería estar vacía");
        assertTrue(paginaProyectos.getTotalElements() >= 1);
        log.info("Proyectos obtenidos en la página: {}. Total de elementos: {}", paginaProyectos.getNumberOfElements(), paginaProyectos.getTotalElements());
    }

    @Test
    @Order(4)
    void deberiaDevolverNotFoundParaIdInexistente() {
        long idInexistente = 9999L;
        log.info("--- PRUEBA 4: Intentando obtener un proyecto con ID inexistente: {} ---", idInexistente);

        ResponseEntity<ApiError> respuesta = restTemplate.getForEntity(
                "/api/proyectos/" + idInexistente, ApiError.class);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
        ApiError error = respuesta.getBody();
        assertNotNull(error);
        assertTrue(error.getMensaje().contains("Recurso no encontrado"));
        log.info("Recibido error 404 esperado: {}", error.getErrores());
    }
}