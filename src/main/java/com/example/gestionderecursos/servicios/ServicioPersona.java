package com.example.gestionderecursos.servicios;

import com.example.gestionderecursos.dto.persona.PersonaActualizarDTO;
import com.example.gestionderecursos.dto.persona.PersonaCrearDTO;
import com.example.gestionderecursos.dto.persona.PersonaDetalleDTO;
import com.example.gestionderecursos.dto.persona.PersonaResumenDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Define el contrato para las operaciones de negocio relacionadas con Personas.
 * Esta capa se comunica utilizando DTOs y encapsula toda la lógica de negocio,
 * como la validación de unicidad de correos electrónicos.
 */
public interface ServicioPersona {

    /**
     * Obtiene una lista paginada y resumida de todas las personas
     * Permite a los clientes solicitar "paginas" de datos (ej. página 1, 20 items por página)
     * @param pageable Objeto que contiene la información de paginación y ordenamiento
     * @return Una página (Page) de PersonaResumenDTO.
     */
    Page<PersonaResumenDTO> listarTodasLasPersonas(Pageable pageable);

    /**
     * Obtiene una lista resumida de todas las personas.
     * Utiliza una proyección optimizada para mejorar el rendimiento.
     * @return Una lista de PersonaResumenDTO.
     */
    List<PersonaResumenDTO> listarTodasLasPersonas();

    /**
     * Busca una persona por su ID y devuelve una vista detallada.
     * @param id El ID de la persona a buscar.
     * @return Un PersonaDetalleDTO con la información completa.
     * @throws com.example.gestionderecursos.excepciones.RecursoNoEncontradoException si la persona no existe.
     */
    PersonaDetalleDTO obtenerPersonaPorId(Long id);

    /**
     * Crea una nueva persona a partir de los datos proporcionados.
     * Valida que el correo electrónico no esté ya en uso.
     * @param personaCrearDTO DTO con los datos para la creación.
     * @return Un PersonaDetalleDTO de la persona recién creada.
     * @throws com.example.gestionderecursos.excepciones.RecursoYaExisteException si ya existe una persona con ese correo.
     */
    PersonaDetalleDTO crearPersona(PersonaCrearDTO personaCrearDTO);

    /**
     * Actualiza una persona existente.
     * @param id El ID de la persona a actualizar.
     * @param personaActualizarDTO DTO con los nuevos datos.
     * @return Un PersonaDetalleDTO de la persona actualizada.
     * @throws com.example.gestionderecursos.excepciones.RecursoNoEncontradoException si la persona no existe.
     */
    PersonaDetalleDTO actualizarPersona(Long id, PersonaActualizarDTO personaActualizarDTO);

    /**
     * Elimina una persona por su ID.
     * @param id El ID de la persona a eliminar.
     * @throws com.example.gestionderecursos.excepciones.RecursoNoEncontradoException si la persona no existe.
     */
    void eliminarPersona(Long id);
}
