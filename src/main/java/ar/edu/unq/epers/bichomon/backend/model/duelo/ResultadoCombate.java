package ar.edu.unq.epers.bichomon.backend.model.duelo;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import java.util.ArrayList;
import java.util.List;

public class ResultadoCombate {

    private Entrenador entrenadorCampeon;
    private Bicho bichoCampeon;
    private List<Ataque> ataques;
    public ResultadoCombate(Entrenador entrenador, Bicho bicho , List ataques){

        this.ataques = ataques;
        this.bichoCampeon = bicho;
        this.entrenadorCampeon = entrenador;
    }

    public String getBichoCampeon(){
        return bichoCampeon.getNombre();
    }
    public String getEntrenadorCampeon(){
        return entrenadorCampeon.getNombre();
    }
    public List getAtaques(){
        return ataques;
    }
}
