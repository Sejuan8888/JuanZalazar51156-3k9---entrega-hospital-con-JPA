package org.example.entidades;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;
import java.time.Period;

@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public abstract class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected String nombre;
    protected String apellido;
    @Column(unique = true)
    protected String dni;
    protected LocalDate fechaNacimiento;
    @Enumerated(EnumType.STRING)
    protected TipoSangre tipoSangre;

    public int getEdad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
}