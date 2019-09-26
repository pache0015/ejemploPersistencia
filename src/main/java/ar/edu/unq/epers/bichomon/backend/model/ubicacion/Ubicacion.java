package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import java.util.List;

public abstract class Ubicacion {
    protected String nombre;

    private Entrenador entrenadorCampeon;

    public Ubicacion(String nombre) {
        this.nombre = nombre;
    }

    public abstract boolean puedeDejarAbandonar(Entrenador entrenador);

    public abstract void recibirAbandonado(Entrenador entrenador, Bicho bichoAAbandonar);

    public abstract List<Bicho> bichomonesPara(Entrenador entrenador);

    public abstract Entrenador getEntrenadorCampeon();
}
