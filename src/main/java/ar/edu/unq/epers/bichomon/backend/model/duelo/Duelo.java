package ar.edu.unq.epers.bichomon.backend.model.duelo;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import java.util.ArrayList;
import java.util.List;


public class Duelo {
    private Entrenador retador;
    private Entrenador campeonActual;
    private List<Ataque> ataques;
    private Bicho atacante;
    private Bicho atacado;


    public Duelo(Entrenador retador, Ubicacion gym){
        this.retador = retador;
        this.ataques = new ArrayList<Ataque>();
        this.campeonActual = gym.getEntrenadorCampeon();

    }
    private void cargarAtaque(Bicho atacante, Bicho rival, Double energiaAtacante) {
        this.ataques.add((new Ataque(atacante, rival, energiaAtacante)));
    }

    public Boolean puedenSeguir(Bicho bicho1, Bicho bicho2){return bicho1.puedeSeguir() && bicho2.puedeSeguir();}

    private Entrenador obtenerGanador() {
        Bicho bichoCampeon = campeonActual.getBichoParaDuelo();

        if (bichoCampeon.puedeSeguir() || hayTimeout()) {
            return campeonActual;
        }
        return retador;
    }

    private void intercambiarAtacanteYAtacado(){
        Bicho auxiliar = atacante;
        atacante = atacado;
        atacado = auxiliar;
    }

    public ResultadoCombate pelear(){
        atacante = retador.getBichoParaDuelo();
        atacado = campeonActual.getBichoParaDuelo();

        while (puedenSeguir(atacante, atacado) && !hayTimeout()){
            Double energiaDeAtaque = atacante.atacar(atacado);
            this.cargarAtaque(atacante, atacado, energiaDeAtaque);
            this.intercambiarAtacanteYAtacado();
        }

        Entrenador ganador = this.obtenerGanador();
        return new ResultadoCombate(ganador, ganador.getBichoParaDuelo(), ataques);
    }

    private boolean hayTimeout() {
        return cantidadDeAtaques().equals(cantidadMaximaDeAtaques());
    }

    private Integer cantidadDeAtaques() {
        return ataques.size();
    }

    private Integer cantidadMaximaDeAtaques() {
        return 10;
    }
}
