package org.example.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoriaClinica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String numeroHistoria;

    private final LocalDate fechaCreacion = LocalDate.now();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    private Paciente paciente;

    @ElementCollection(fetch = FetchType.EAGER) @Builder.Default
    private List<String> diagnosticos = new ArrayList<>();
    @ElementCollection(fetch = FetchType.EAGER) @Builder.Default
    private List<String> tratamientos = new ArrayList<>();
    @ElementCollection(fetch = FetchType.EAGER) @Builder.Default
    private List<String> alergias = new ArrayList<>();

    @PrePersist
    private void generarNumeroHistoria() {
        if (paciente != null) {
            this.numeroHistoria = "HC-" + paciente.getDni() + "-" + System.currentTimeMillis();
        }
    }
    public void agregarDiagnostico(String d) { this.diagnosticos.add(d); }
    public void agregarTratamiento(String t) { this.tratamientos.add(t); }
    public void agregarAlergia(String a) { this.alergias.add(a); }
}