package com.example.gestionderecursos.servicios;

import com.example.gestionderecursos.dto.proyecto.ProyectoActualizarDTO;
import com.example.gestionderecursos.dto.proyecto.ProyectoCrearDTO;
import com.example.gestionderecursos.dto.proyecto.ProyectoDetalleDTO;
import com.example.gestionderecursos.dto.proyecto.ProyectoResumenDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Define el contrato para las operaciones de negocio relacionadas con Proyectos.
 * Esta capa se comunica utilizando DTOs y encapsula toda la lógica de negocio.
 */
public interface ServicioProyecto {

    /**
     * Obtiene una lista paginada y resumida de todos los proyectos.
     * Permite a los clientes solicitar "páginas" de datos (ej. página 1, 20 items por página).
     * @param pageable Objeto que contiene la información de paginación y ordenamiento.
     * @return Una página (Page) de ProyectoResumenDTO.
     */
    Page<ProyectoResumenDTO> listarTodosLosProyectos(Pageable pageable);

    /**
     * Obtiene una lista resumida de todos los proyectos.
     * Ideal para vistas de lista, optimizando el rendimiento.
     * @return Una lista de ProyectoResumenDTO.
     */
    List<ProyectoResumenDTO> listarTodosLosProyectos();

    /**
     * Busca un proyecto por su ID y devuelve una vista detallada.
     * @param id El ID del proyecto a buscar.
     * @return Un ProyectoDetalleDTO con la información completa.
     * @throws com.example.gestionderecursos.excepciones.RecursoNoEncontradoException si el proyecto no existe.
     */
    ProyectoDetalleDTO obtenerProyectoPorId(Long id);

    /**
     * Crea un nuevo proyecto a partir de los datos proporcionados.
     * Valida que no exista otro proyecto con el mismo nombre.
     * @param proyectoCrearDTO DTO con los datos para la creación.
     * @return Un ProyectoDetalleDTO del proyecto recién creado.
     * @throws com.example.gestionderecursos.excepciones.RecursoYaExisteException si ya existe un proyecto con ese nombre.
     */
    ProyectoDetalleDTO crearProyecto(ProyectoCrearDTO proyectoCrearDTO);

    /**
     * Actualiza un proyecto existente.
     * @param id El ID del proyecto a actualizar.
     * @param proyectoActualizarDTO DTO con los nuevos datos.
     * @return Un ProyectoDetalleDTO del proyecto actualizado.
     * @throws com.example.gestionderecursos.excepciones.RecursoNoEncontradoException si el proyecto no existe.
     */
    ProyectoDetalleDTO actualizarProyecto(Long id, ProyectoActualizarDTO proyectoActualizarDTO);

    /**
     * Elimina un proyecto por su ID.
     * @param id El ID del proyecto a eliminar.
     * @throws com.example.gestionderecursos.excepciones.RecursoNoEncontradoException si el proyecto no existe.
     */
    void eliminarProyecto(Long id);
}
