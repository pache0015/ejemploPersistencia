package ar.edu.unq.epers.bichomon.backend.model.condicion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class CondicionBasadaEnNivelEntrenador extends Condicion {
    @Column
    private Integer nivelNecesarioDeEntrenador;
    @OneToOne
    private Entrenador entrenador;

    public CondicionBasadaEnNivelEntrenador(Entrenador entrenador, Integer nivelNcesarioParaEvolucionar) {
        this.entrenador = entrenador;
        this.nivelNecesarioDeEntrenador = nivelNcesarioParaEvolucionar;
    }

    @Override
    public Boolean evaluar(Bicho bicho) {
        return this.entrenador.tieneNivelNecesario(this.nivelNecesarioDeEntrenador);
    }
}
