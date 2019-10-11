package ar.edu.unq.epers.bichomon.backend.model.condicion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class CondicionBasadaEnNivelEntrenador extends Condicion {
    @Column
    private int nivelNecesarioDeEntrenador;

    public CondicionBasadaEnNivelEntrenador(Integer nivelNecesarioParaEvolucionar) {
        this.nivelNecesarioDeEntrenador = nivelNecesarioParaEvolucionar;
    }

    public CondicionBasadaEnNivelEntrenador() {
    }

    @Override
    public Boolean evaluar(Bicho bicho) {
        return bicho.nivelEntrenador() >= this.nivelNecesarioDeEntrenador;
    }
}
