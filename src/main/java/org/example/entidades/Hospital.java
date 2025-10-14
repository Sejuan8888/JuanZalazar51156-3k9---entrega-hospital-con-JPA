package org.example.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String direccion;
    private String telefono;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Departamento> departamentos = new ArrayList<>();

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Paciente> pacientes = new ArrayList<>();

    public void agregarDepartamento(Departamento d) {
        this.departamentos.add(d);
        d.setHospital(this);
    }
    public void agregarPaciente(Paciente p) {
        this.pacientes.add(p);
        p.setHospital(this);
    }
}