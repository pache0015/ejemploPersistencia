package ar.edu.unq.epers.bichomon.backend.ubicaciones;

import ar.edu.unq.epers.bichomon.backend.interfaces.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

public class Gimnasio implements Ubicacion {

    private String nombre;
    private Entrenador campeon;

    public Gimnasio(String nombre){
        this.nombre = nombre;
    }


}
