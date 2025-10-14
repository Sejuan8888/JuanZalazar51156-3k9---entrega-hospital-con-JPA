package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.example.entidades.*;
import org.example.servicio.CitaException;
import org.example.servicio.CitaManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hospital-persistence-unit");
        EntityManager em = emf.createEntityManager();

        try {
            // --- INICIALIZACIÓN DE DATOS ---
            em.getTransaction().begin();

            Hospital hospital = Hospital.builder().nombre("Hospital Privado de Mendoza").direccion("Av. San Martín 123").telefono("261-1234567").build();

            Departamento cardio = Departamento.builder().nombre("Cardiología").especialidad(EspecialidadMedica.CARDIOLOGIA).build();
            Departamento pedia = Departamento.builder().nombre("Pediatría").especialidad(EspecialidadMedica.PEDIATRIA).build();
            hospital.agregarDepartamento(cardio);
            hospital.agregarDepartamento(pedia);

            Sala salaC1 = Sala.builder().numero("C-101").tipo("Consultorio").build();
            cardio.agregarSala(salaC1);
            Sala salaP1 = Sala.builder().numero("P-201").tipo("Consultorio").build();
            pedia.agregarSala(salaP1);

            Medico drPerez = Medico.builder().nombre("Juan").apellido("Perez").dni("25123456").fechaNacimiento(LocalDate.of(1980, 1, 1)).tipoSangre(TipoSangre.A_POSITIVO).numeroMatricula(new Matricula("MP-12345")).especialidad(EspecialidadMedica.CARDIOLOGIA).build();
            cardio.agregarMedico(drPerez);

            Paciente p1 = Paciente.builder().nombre("Carlos").apellido("Gomez").dni("30123456").fechaNacimiento(LocalDate.of(1985, 5, 5)).tipoSangre(TipoSangre.O_POSITIVO).telefono("261-7654321").direccion("Calle Falsa 123").build();
            hospital.agregarPaciente(p1);
            p1.getHistoriaClinica().agregarAlergia("Penicilina");

            em.persist(hospital);
            em.getTransaction().commit();
            System.out.println("✅ Hospital y personal inicial creados.");

            // --- PROGRAMACIÓN DE CITAS ---
            em.getTransaction().begin();
            CitaManager citaManager = new CitaManager();
            try {
                Cita cita = citaManager.programarCita(em, p1, drPerez, salaC1, LocalDateTime.now().plusDays(10), new BigDecimal("10000.00"));
                em.persist(cita);
                System.out.println("✅ Cita programada exitosamente.");
            } catch (CitaException e) {
                System.err.println("Error: " + e.getMessage());
            }
            em.getTransaction().commit();

            // --- CONSULTAS JPQL ---
            System.out.println("\n--- Realizando consultas ---");
            TypedQuery<Medico> q1 = em.createQuery("SELECT m FROM Medico m WHERE m.especialidad = :esp", Medico.class);
            q1.setParameter("esp", EspecialidadMedica.CARDIOLOGIA);
            List<Medico> medicos = q1.getResultList();
            System.out.println("🩺 Médicos de cardiología: " + medicos.size());

            TypedQuery<Long> q2 = em.createQuery("SELECT COUNT(p) FROM Paciente p", Long.class);
            System.out.println("👥 Total de pacientes: " + q2.getSingleResult());

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
        System.out.println("\nSISTEMA EJECUTADO EXITOSAMENTE");
    }
}