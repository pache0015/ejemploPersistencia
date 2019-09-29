package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import java.time.LocalTime;

public class BusquedaExitosa {

    private LocalTime factorTiempo;
    private int factorNivel;
    private int factorPoblacion;

    public BusquedaExitosa(LocalTime factorTiempo, int factorNivel, int factorPoblacion){
        this.factorTiempo = factorTiempo;
        this.factorNivel = factorNivel;
        this.factorPoblacion = factorPoblacion;
    }
    public Boolean busquedaExitosa(){
        return factorPoblacion*factorNivel*factorTiempo.getHour()*generarRandom() > 0.5;
    }

    private Double generarRandom(){
        return Math.random() * 1.0;
    }
}
