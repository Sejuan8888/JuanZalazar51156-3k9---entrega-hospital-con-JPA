package org.example.entidades;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    @Enumerated(EnumType.STRING)
    private EspecialidadMedica especialidad;

    @ManyToOne(fetch = FetchType.LAZY)
    private Hospital hospital;

    @OneToMany(mappedBy = "departamento", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Builder.Default
    private List<Medico> medicos = new ArrayList<>();

    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Sala> salas = new ArrayList<>();

    public void agregarMedico(Medico m) {
        if (m.getEspecialidad() != this.especialidad) {
            throw new IllegalArgumentException("Especialidad del médico no compatible con el departamento.");
        }
        this.medicos.add(m);
        m.setDepartamento(this);
    }
    public void agregarSala(Sala s) {
        this.salas.add(s);
        s.setDepartamento(this);
    }
}