package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

public abstract class Ubicacion {
    protected String nombre;
    public static String ERROR_ABANDONO = "No se puede abandonar a ese bichomon en esta ubicacion";

    public Ubicacion(String nombre) {
        this.nombre = nombre;
    }

    public abstract boolean puedeDejarAbandonar(Entrenador entrenador);

    public abstract void recibirAbandonado(Entrenador entrenador, Bicho bichoAAbandonar);
}
