package ar.edu.unq.epers.bichomon.backend.model.duelo;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import java.util.ArrayList;
import java.util.List;


public class Duelo {
    public Entrenador retador;
    public Entrenador campeonActual;
    private List<Ataque> ataques;
    public Bicho atacante;
    public Bicho atacado;
    private Ubicacion gym;

    public Duelo(Entrenador retador, Ubicacion gym){
        this.retador = retador;
        this.ataques = new ArrayList<Ataque>();
        this.campeonActual = gym.getEntrenadorCampeon();
        this.gym = gym;

    }
    private void cargarAtaque(Bicho atacante, Bicho rival, Double energiaAtacante) {
        ataques.add((new Ataque(atacante, rival, energiaAtacante)));
    }
    private boolean hayTimeout() {return cantidadDeAtaques().equals(cantidadMaximaDeAtaques());}

    public Integer cantidadDeAtaques() {return ataques.size();}

    private Integer cantidadMaximaDeAtaques() {return 10;}

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
            cargarAtaque(atacante, atacado, energiaDeAtaque);
            intercambiarAtacanteYAtacado();
        }
        Entrenador ganador = obtenerGanador();
        gym.declararCampeones(ganador, ganador.getBichoParaDuelo());
        aumentarEnergiaDeBichosPorDuelo();
        return new ResultadoCombate(ganador, ganador.getBichoParaDuelo(), ataques);
    }
    private void aumentarEnergiaDeBichosPorDuelo() {
        atacado.aumentarEnergiaDeBichoPorDuelo();
        atacante.aumentarEnergiaDeBichoPorDuelo();
    }


}
