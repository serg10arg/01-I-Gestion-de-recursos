package com.example.gestionderecursos.servicios.impl;

import com.example.gestionderecursos.dto.proyecto.ProyectoActualizarDTO;
import com.example.gestionderecursos.dto.proyecto.ProyectoCrearDTO;
import com.example.gestionderecursos.dto.proyecto.ProyectoDetalleDTO;
import com.example.gestionderecursos.dto.proyecto.ProyectoResumenDTO;
import com.example.gestionderecursos.excepciones.RecursoNoEncontradoException;
import com.example.gestionderecursos.excepciones.RecursoYaExisteException;
import com.example.gestionderecursos.modelos.Proyecto;
import com.example.gestionderecursos.repositorios.RepositorioProyecto;
import com.example.gestionderecursos.servicios.ServicioProyecto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioProyectoImpl implements ServicioProyecto {

    private final RepositorioProyecto repositorioProyecto;
    private final ModelMapper modelMapper;

    public ServicioProyectoImpl(RepositorioProyecto repositorioProyecto, ModelMapper modelMapper) {
        this.repositorioProyecto = repositorioProyecto;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProyectoResumenDTO> listarTodosLosProyectos(Pageable pageable) {
        Page<Proyecto> proyectoPage = repositorioProyecto.findAll(pageable);
        return proyectoPage.map(proyecto -> modelMapper.map(proyecto, ProyectoResumenDTO.class));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProyectoResumenDTO> listarTodosLosProyectos() {
        List<Proyecto> proyectos = repositorioProyecto.findAll();
        return proyectos.stream()
                .map(proyecto -> modelMapper.map(proyecto, ProyectoResumenDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProyectoDetalleDTO obtenerProyectoPorId(Long id) {
        Proyecto proyecto = repositorioProyecto.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Proyecto no encontrado con id : " + id));
        // Usamos el mapper para convertir la entidad a DTO
        return modelMapper.map(proyecto, ProyectoDetalleDTO.class);
    }

    @Override
    @Transactional
    public ProyectoDetalleDTO crearProyecto (ProyectoCrearDTO proyectoCrearDTO) {
        if (repositorioProyecto.existsByNombre(proyectoCrearDTO.getNombre())) {
            throw new RecursoYaExisteException("Ya existe un proyecto con el nombre: " + proyectoCrearDTO.getNombre());
        }

        // Usamos el mapper para convertir el DTO de creación a una entidad
        Proyecto proyecto = modelMapper.map(proyectoCrearDTO, Proyecto.class);

        // La lógica de negocio sigue perteneciendo al servicio
        proyecto.setFechaCreacion(LocalDate.now());

        Proyecto proyectoGuardado = repositorioProyecto.save(proyecto);

        // Mapeamos la entidad guardada (con ID y fecha) al DTO de detalle para la respuesta
        return modelMapper.map(proyectoGuardado, ProyectoDetalleDTO.class);
    }

    @Override
    @Transactional
    public ProyectoDetalleDTO actualizarProyecto(Long id, ProyectoActualizarDTO proyectoActualizarDTO) {
        Proyecto proyectoExistente = repositorioProyecto.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Proyecto no encontrado con id: " + id));

        // Lógica de negocio: si el nombre cambia, verificar que el nuevo no esté en uso por OTRO proyecto.
        repositorioProyecto.findByNombre(proyectoActualizarDTO.getNombre())
                .ifPresent(proyectoConNuevoNombre -> {
                    if (!proyectoConNuevoNombre.getId().equals(id)) {
                        throw new RecursoYaExisteException("El nombre '" + proyectoActualizarDTO.getNombre() + "' ya esta en uso por otro proyecto");
                    }
                });

        // Aplicar los cambios del DTO a la entidad existente.
        modelMapper.map(proyectoActualizarDTO, proyectoExistente);

        // JPA detectará los cambios en 'proyectoExistente' y los persistirá.
        // La llamada a save() es explícita y no daña el rendimiento.
        Proyecto proyectoActualizado = repositorioProyecto.save(proyectoExistente);

        return modelMapper.map(proyectoActualizado, ProyectoDetalleDTO.class);
    }

    @Override
    @Transactional
    public void eliminarProyecto(Long id) {
        if (!repositorioProyecto.existsById(id)) {
            throw new RuntimeException("Proyecto no encontrado con id: " + id);
        }
        repositorioProyecto.deleteById(id);
    }

}
