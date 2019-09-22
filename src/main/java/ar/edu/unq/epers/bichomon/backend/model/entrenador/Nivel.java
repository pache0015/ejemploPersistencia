package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import javax.persistence.*;

@Entity
public class Nivel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column
    public Integer numeroNivel;

    @Column
    public Integer experienciaMinima;

    @Column
    public Integer experienciaMaxima;

    public Nivel() {
    }

    public Nivel(Integer numeroNivel, Integer experienciaMinima, Integer experienciaMaxima) {
        this.numeroNivel = numeroNivel;
        this.experienciaMinima = experienciaMinima;
        this.experienciaMaxima = experienciaMaxima;
    }
}
