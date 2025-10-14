package org.example.entidades;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Paciente paciente;
    @ManyToOne(fetch = FetchType.LAZY)
    private Medico medico;
    @ManyToOne(fetch = FetchType.LAZY)
    private Sala sala;

    private LocalDateTime fechaHora;
    private BigDecimal costo;
    @Enumerated(EnumType.STRING)
    private EstadoCita estado;
    private String observaciones;
}