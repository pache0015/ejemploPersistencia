package ar.edu.unq.epers.bichomon.backend.model.duelo;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import java.util.ArrayList;
import java.util.List;


public class Duelo {
    private Entrenador retador;
    private Entrenador campeon;
    private Integer cantAtaques;
    private Entrenador nuevoCampeon;
    private List<Ataque> ataques;
    private Bicho atacante;
    private Bicho atacado;
    private Bicho auxiliar;


    public Duelo(Entrenador retador, Entrenador campeon){
        this.retador = retador;
        this.campeon = campeon;
        this.nuevoCampeon = null;
        this.ataques = new ArrayList<>();
        this.atacante = this.retador.getBichoParaDuelo();
        this.atacado = this.campeon.getBichoParaDuelo();
    }

    private void atacar(Bicho atacante, Bicho rival){


        Double energiaAtacante = atacante.getEnergia();

        rival.reducirEnergia(this.energiaAQuitar(energiaAtacante));


        this.cargarAtaque(atacante, rival, energiaAtacante);
    }

    private void cargarAtaque(Bicho atacante, Bicho rival, Double energiaAtacante) {
        this.ataques.add((new Ataque(atacante, rival, energiaAtacante)));
    }

    private Double energiaAQuitar(Double valor){
        return valor * Math.random();
        //Corregir random
    }

    public void atacarSiValida(Entrenador atacante, Entrenador rival){

        if (atacante.getBichoParaDuelo().puedeSeguir() && (ataques.size() < 10)){
            this.atacar(atacante.getBichoParaDuelo(), rival.getBichoParaDuelo());
        }
        else{
            this.finalizar();
        }
    }

    private ResultadoCombate finalizar() {
        return new ResultadoCombate(nuevoCampeon, nuevoCampeon.getBichoParaDuelo(), ataques);
    }


    private void setearAtacanteYAtacado(Bicho bicho1, Bicho bicho2){


    }
}
