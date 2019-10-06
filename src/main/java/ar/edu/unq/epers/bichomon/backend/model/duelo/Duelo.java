package ar.edu.unq.epers.bichomon.backend.model.duelo;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import java.util.ArrayList;
import java.util.List;


public class Duelo {
    private Bicho retador;
    private Bicho bichoCampeonActual;
    private Entrenador entrenadorCampeonActual;
    private Entrenador entrenadorRetador;
    private List<Ataque> ataques;
    private Bicho atacante;
    private Bicho atacado;
    private Ubicacion gym;

    public Duelo(Bicho retador, Ubicacion gym){
        this.retador = retador;
        this.ataques = new ArrayList<>();
        this.entrenadorRetador = retador.getEntrenadorDueño();
        this.gym = gym;
        this.bichoCampeonActual = gym.getBichoCampeon();
        this.entrenadorCampeonActual = bichoCampeonActual.getEntrenadorDueño();

    }
    private void cargarAtaque(Bicho atacante, Bicho rival, Double energiaAtacante) {
        ataques.add((new Ataque(atacante, rival, energiaAtacante)));
    }
    private boolean hayTimeout() {return cantidadDeAtaques().equals(cantidadMaximaDeAtaques());}

    public Integer cantidadDeAtaques() {return ataques.size();}

    private Integer cantidadMaximaDeAtaques() {return 10;}

    private Boolean puedenSeguir(Bicho bicho1, Bicho bicho2) {
        return bicho1.puedeSeguir() && bicho2.puedeSeguir();
    }

    private Bicho obtenerGanador() {
        if (bichoCampeonActual.puedeSeguir() || hayTimeout()) return bichoCampeonActual;
        return retador;
    }
    private void intercambiarAtacanteYAtacado(){
        Bicho auxiliar = atacante;
        atacante = atacado;
        atacado = auxiliar;
    }
    public ResultadoCombate pelear(){

        atacante = retador;
        atacado = bichoCampeonActual;

        while (puedenSeguir(atacante, atacado) && !hayTimeout()){
            Double energiaDeAtaque = atacante.atacar(atacado);
            cargarAtaque(atacante, atacado, energiaDeAtaque);
            intercambiarAtacanteYAtacado();
        }

        Bicho ganador = obtenerGanador();

        gym.declararCampeones(ganador.getEntrenadorDueño(), ganador);

        aumentarEnergiaDeBichosPorDuelo();
        return new ResultadoCombate(ganador.getEntrenadorDueño(), ganador, ataques);
    }
    private void aumentarEnergiaDeBichosPorDuelo() {
        atacado.aumentarEnergiaDeBichoPorDuelo();
        atacante.aumentarEnergiaDeBichoPorDuelo();
    }


}
