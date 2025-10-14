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
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String numero;
    private String tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Departamento departamento;

    @OneToMany(mappedBy = "sala")
    @Builder.Default
    private List<Cita> citas = new ArrayList<>();
}