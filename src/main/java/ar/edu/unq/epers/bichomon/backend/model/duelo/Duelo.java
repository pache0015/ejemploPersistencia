package ar.edu.unq.epers.bichomon.backend.model.duelo;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import static java.lang.Math.random;

public class Duelo {
    private Entrenador retador;
    private Entrenador campeon;
    private Integer cantAtaques;


    public Duelo(Entrenador retador, Entrenador campeon){
        this.retador = retador;
        this.campeon = campeon;
        this.cantAtaques = 0;
    }

    private void atacar(Bicho atacante, Bicho rival){


        Double energiaAtacante = atacante.getEnergia();

        rival.reducirEnergia(this.energiaAQuitar(energiaAtacante));

        cantAtaques ++;
    }

    private Double energiaAQuitar(Double valor){
        return valor * Math.random();
        //Corregir random
    }

    public void atacarSiValida(Entrenador atacante, Entrenador rival){
        if (atacante.getBichoParaDuelo().puedeSeguir()){
            this.atacar(atacante.getBichoParaDuelo(), rival.getBichoParaDuelo());
        }
        else {
            this.finalizar();

        }
    }

    private void finalizar() {
        
    }
}
