package org.example.servicio;

import org.example.entidades.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CitaManager {

    public Cita programarCita(EntityManager em, Paciente p, Medico m, Sala s, LocalDateTime fecha, BigDecimal costo) throws CitaException {
        if (fecha.isBefore(LocalDateTime.now())) throw new CitaException("La cita no puede ser en el pasado.");
        if (costo.compareTo(BigDecimal.ZERO) <= 0) throw new CitaException("El costo debe ser positivo.");
        if (m.getEspecialidad() != s.getDepartamento().getEspecialidad()) throw new CitaException("Especialidad no compatible.");
        if (!esHorarioDisponible(em, m, s, fecha)) throw new CitaException("Horario no disponible.");

        return Cita.builder().paciente(p).medico(m).sala(s).fechaHora(fecha).costo(costo).estado(EstadoCita.PROGRAMADA).build();
    }

    private boolean esHorarioDisponible(EntityManager em, Medico medico, Sala sala, LocalDateTime fecha) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(c) FROM Cita c WHERE (c.medico = :medico OR c.sala = :sala) AND c.fechaHora BETWEEN :start AND :end", Long.class);
        query.setParameter("medico", medico);
        query.setParameter("sala", sala);
        query.setParameter("start", fecha.minusHours(2));
        query.setParameter("end", fecha.plusHours(2));
        return query.getSingleResult() == 0;
    }
}