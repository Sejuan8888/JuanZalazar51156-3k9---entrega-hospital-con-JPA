package org.example.entidades;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
public class Matricula implements Serializable {
    private String numero;

    public Matricula(String numero) {
        if (numero == null || !numero.matches("MP-\\d{4,6}")) {
            throw new IllegalArgumentException("Formato de matrícula inválido. Debe ser 'MP-' seguido de 4 a 6 dígitos.");
        }
        this.numero = numero;
    }
    @Override
    public String toString() { return numero; }
}