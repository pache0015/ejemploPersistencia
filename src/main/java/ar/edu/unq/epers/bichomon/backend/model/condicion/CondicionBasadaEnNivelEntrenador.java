package ar.edu.unq.epers.bichomon.backend.model.condicion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

public class CondicionBasadaEnNivelEntrenador extends Condicion {
    private Integer nivelNecesarioDeEntrenador;
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
