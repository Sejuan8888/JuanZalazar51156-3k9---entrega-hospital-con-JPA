package org.example.entidades;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Medico extends Persona {
    @Embedded
    private Matricula numeroMatricula;

    @Enumerated(EnumType.STRING)
    private EspecialidadMedica especialidad;

    @ManyToOne(fetch = FetchType.LAZY)
    private Departamento departamento;

    @OneToMany(mappedBy = "medico")
    private List<Cita> citas;

    // Constructor protegido para inicializar colecciones con SuperBuilder
    protected Medico(MedicoBuilder<?, ?> builder) {
        super(builder);
        this.citas = new ArrayList<>();
    }
}