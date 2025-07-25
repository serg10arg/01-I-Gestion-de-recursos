package com.example.gestionderecursos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.gestionderecursos.modelos.Proyecto;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@Repository
public interface RepositorioProyecto extends JpaRepository<Proyecto, Long> {

    // --- Búsquedas Básicas ----

    /**
     * Busca un proyecto por su nombre exacto.
     * Genera: "SELECT p FROM Proyecto p WHERE p.nombre = ?1"
     */
    Optional<Proyecto> findByNombre(String nombre);

    /**
     * Busca proyectos creados después de una fecha específica.
     * Genera: "SELECT p FROM Proyecto p WHERE p.fechaCreacion > ?1"
     */
    List<Proyecto> findByFechaCreacionAfter(LocalDate fecha);

    /**
     * Busca proyectos cuyo nombre contenga una palabra clave, ignorando mayusculas/minusculas
     * Genera: "SELECT p FROM Proyecto p WHERE upper(p.nombre) LIKE upper(concat('%', ?1, '%'))"
     */
    List<Proyecto> findByNombreContainingIgnoreCase(String keyword);


    // -- Búsquedas con Multiples criterios (AND / OR) ---

    /**
     * Busca proyectos que contengan una palabra clave en el nombre Y en la descripción.
     */
    List<Proyecto> findByNombreContainingIgnoreCaseAndDescripcionContainingIgnoreCase(String nombreKeyword, String descKeyword);

    /**
     * Busca proyectos que contengan una palabra clave en el nombre O en la descripción.
     */
    List<Proyecto> findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(String nombreKeyword, String descKeyword);

    // --- Búsquedas por Rango ---

    /**
     * Busca proyectos creados entre dos fechas
     * Genera: "SELECT p FROM Proyecto p WHERE p.fechaCreacion BETWEEN ?1 AND ?2"
     */
    List<Proyecto> findByFechaCreacionBetween(LocalDate fechaInicio, LocalDate fechaFin);

    // --- Ordenamiento y Limitación de Resultados

    /**
     * Busca los 5 proyectos, ordenados por fecha de creación descendente
     */
    List<Proyecto> findTop5ByOrderByFechaCreacionDesc();

    /**
     * Busca todos los proyectos, ordenados por nombre ascendente
     */
    List<Proyecto> findAllByOrderByNombreAsc();


    // --- Consultas de Existencia y Conteo

    boolean existsByNombre(String nombre);

    /**
     * Cuenta cuántos proyectos tienen una descripción.
     */
    long countByDescripcionIsNotNull();


    // --- Consulta Explícita con @Query ---

    /**
     * Ejemplo de una consulta JPQL explícita para un control total.
     */
    @Query("SELECT p FROM Proyecto p WHERE p.nombre = :nombre AND p.descripcion IS NOT NULL")
    Optional<Proyecto> findByProyectoActivoPorNombre(@Param("nombre") String nombre);
}
