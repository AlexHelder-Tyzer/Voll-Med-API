package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface MedicoRepository extends JpaRepository<Medico , Long > {
    Page<Medico> findByActivoTrue(Pageable paginacion); //tipo de Id, Entida con que va trabajar

    @Query("""
            SELECT m FROM Medico m
            WHERE m.activo=1 AND 
            m.especialidad=:especialidad AND
            m.id NOT IN(
                SELECT c.medico.id FROM Consulta c
                c.data=:fecha
            )
            ORDER BY rand()
            LIMIT 1
            """)
    Medico seleccionarMedicoConEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);
}
