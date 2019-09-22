package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

public class Dojo extends Ubicacion {

    private Entrenador campeon;


    public Dojo(String nombre) {
        super(nombre);
    }

    @Override
    public boolean puedeDejarAbandonar(Entrenador entrenador) {
        return false;
    }

    @Override
    public void recibirAbandonado(Entrenador entrenador, Bicho bichoAAbandonar) {

    }
}
