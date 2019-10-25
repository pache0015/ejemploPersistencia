package ar.edu.unq.epers.bichomon.backend.model.duelo;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;

import java.util.ArrayList;
import java.util.List;


public class Duelo {
    private Bicho bichoRetador;
    private Bicho bichoCampeonActual;
    private Entrenador entrenadorCampeonActual;
    private Entrenador entrenadorRetador;
    private List<Ataque> ataques;
    private Bicho atacante;
    private Bicho atacado;
    private Dojo gym;

    public Duelo(Bicho retador, Dojo gym){
        this.bichoRetador = retador;
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
        return bichoRetador;
    }
    private void intercambiarAtacanteYAtacado(){
        Bicho auxiliar = atacante;
        atacante = atacado;
        atacado = auxiliar;
    }
    public ResultadoCombate pelear(){

        atacante = bichoRetador;
        atacado = bichoCampeonActual;

        while (puedenSeguir(atacante, atacado) && !hayTimeout()){
            Double energiaDeAtaque = atacante.atacar(atacado, valorRandomPorAtaque());
            cargarAtaque(atacante, atacado, energiaDeAtaque);
            intercambiarAtacanteYAtacado();
        }

        Bicho ganador = obtenerGanador();
        ganador.aumentarVictorias();

        gym.declararCampeones(ganador.getEntrenadorDueño(), ganador);


        aumentarEnergiaDeBichosPorDuelo();

        restaurarEnergiaDeBichos();
        return new ResultadoCombate(ganador.getEntrenadorDueño(), ganador, ataques);
    }

    private Double valorRandomPorAtaque() {
        return (Math.random() * 1.0) + 0.5;
    }

    private void restaurarEnergiaDeBichos() {
        bichoCampeonActual.restaurarEnergia();
        bichoRetador.restaurarEnergia();
    }

    private void aumentarEnergiaDeBichosPorDuelo() {
        atacado.aumentarEnergiaDeBichoPorDuelo();
        atacante.aumentarEnergiaDeBichoPorDuelo();
    }


}
