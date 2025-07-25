package com.example.gestionderecursos.controladores;

import com.example.gestionderecursos.dto.proyecto.ProyectoActualizarDTO;
import com.example.gestionderecursos.dto.proyecto.ProyectoCrearDTO;
import com.example.gestionderecursos.dto.proyecto.ProyectoDetalleDTO;
import com.example.gestionderecursos.dto.proyecto.ProyectoResumenDTO;
import com.example.gestionderecursos.servicios.ServicioProyecto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


/**
 * Controlador REST para la gestión de proyectos.
 * Expone endpoints para operaciones de negocio, comunicándose exclusivamente
 * a través de DTOs y delegando toda la lógica a la capa de servicio.
 */
@RestController
@RequestMapping("/api/proyectos")
public class ControladorProyecto {

    private final ServicioProyecto servicioProyecto;

    public ControladorProyecto(ServicioProyecto servicioProyecto) {
        this.servicioProyecto = servicioProyecto;
    }

    /**
     * GET /api/proyectos : Obtiene una lista paginada y resumida de todos los proyectos.
     * Acepta parámetros como ?page=0&size=10&sort=nombre,asc
     * @param pageable Objeto que contiene la información de paginación y ordenamiento.
     * @return ResponseEntity con una página de ProyectoResumenDTO y estado 200 OK.
    */
     @GetMapping
    public ResponseEntity<Page<ProyectoResumenDTO>> listarTodosLosProyectos(@PageableDefault(size = 10, sort = "nombre")Pageable pageable) {
        Page<ProyectoResumenDTO> proyectosPage = servicioProyecto.listarTodosLosProyectos(pageable);
        return ResponseEntity.ok(proyectosPage);
    }

    /**
     * GET /api/proyectos/{id} : Obtiene los detalles completos de un proyecto.
     * @param id El ID del proyecto.
     * @return ResponseEntity con el ProyectoDetalleDTO y estado 200 OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProyectoDetalleDTO> obtenerProyectoPorId(@PathVariable Long id) {
        ProyectoDetalleDTO proyecto = servicioProyecto.obtenerProyectoPorId(id);
        return ResponseEntity.ok(proyecto);
    }

    /**
     * POST /api/proyectos : Crea un nuevo proyecto.
     * @param proyectoCrearDTO El DTO con los datos para la creación.
     * @return ResponseEntity con el ProyectoDetalleDTO creado, la URI del nuevo recurso y estado 201 Created.
     */
    @PostMapping
    public ResponseEntity<ProyectoDetalleDTO> crearProyecto(@Valid @RequestBody ProyectoCrearDTO proyectoCrearDTO) {
        ProyectoDetalleDTO proyectoCreado = servicioProyecto.crearProyecto(proyectoCrearDTO);

        // La URI del nuevo recurso creado para el encabezado 'Location'.
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(proyectoCreado.getId()).toUri();

        return ResponseEntity.created(ubicacion).body(proyectoCreado);
    }

    /**
     * PUT /api/proyectos/{id} : Actualiza un proyecto existente.
     * @param id El ID del proyecto a actualizar.
     * @param proyectoActualizarDTO El DTO con los datos actualizados.
     * @return ResponseEntity con el ProyectoDetalleDTO actualizado y estado 200 OK.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProyectoDetalleDTO> actualizarProyecto(@PathVariable Long id, @Valid @RequestBody ProyectoActualizarDTO proyectoActualizarDTO) {
        ProyectoDetalleDTO proyectoActualizado = servicioProyecto.actualizarProyecto(id, proyectoActualizarDTO);
        return ResponseEntity.ok(proyectoActualizado);
    }

    /**
     * DELETE /api/proyectos/{id} : Elimina un proyecto por su ID.
     * @param id El ID del proyecto a eliminar.
     * @return ResponseEntity con estado 204 No Content.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarProyecto(@PathVariable Long id) {
        servicioProyecto.eliminarProyecto(id);
    }
}
