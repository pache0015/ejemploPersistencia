package ar.edu.unq.epers.bichomon.backend.ubicaciones;

import ar.edu.unq.epers.bichomon.backend.dao.impl.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

public class Gimnasio extends Ubicacion {

    private String nombre;
    private Entrenador campeon;

    public Gimnasio(String nombre){
        this.nombre = nombre;
    }

    public Entrenador getCampeon() {
        return this.campeon;
    }
}
