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
    @Column
    public Integer limiteDeBichomonsPorNivel;

    public Nivel() {
    }

    public Nivel(Integer numeroNivel, Integer experienciaMinima, Integer experienciaMaxima) {
        this.numeroNivel = numeroNivel;
        this.limiteDeBichomonsPorNivel = numeroNivel;
        this.experienciaMinima = experienciaMinima;
        this.experienciaMaxima = experienciaMaxima;
    }
    public Integer getExperienciaMinima(){
        return this.experienciaMinima;
    }
    public Integer getExperienciaMaxima(){
        return this.experienciaMaxima;
    }

    public boolean llegoAlLimite(int cantidadDeBichosAtrapadosPorEntrenador) {
        return this.limiteDeBichomonsPorNivel > cantidadDeBichosAtrapadosPorEntrenador;
    }
}
