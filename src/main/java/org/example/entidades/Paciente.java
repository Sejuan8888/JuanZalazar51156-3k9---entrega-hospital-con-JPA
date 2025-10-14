package org.example.entidades;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Paciente extends Persona {

    private String telefono;
    private String direccion;

    @ManyToOne(fetch = FetchType.LAZY)
    private Hospital hospital;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cita> citas;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
    private HistoriaClinica historiaClinica;

    protected Paciente(PacienteBuilder<?, ?> builder) {
        super(builder);
        this.telefono = Objects.requireNonNull(builder.telefono);
        this.direccion = Objects.requireNonNull(builder.direccion);
        this.citas = new ArrayList<>();
        this.historiaClinica = HistoriaClinica.builder().paciente(this).build();
    }
}