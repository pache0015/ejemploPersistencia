package ar.edu.unq.epers.bichomon.backend.model.duelo;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public class Ataque {

    private Bicho atacante;
    private Bicho rival;
    private Double energia;
    public Ataque(Bicho atacante, Bicho rival, Double energiaAtacante) {
        this.atacante = atacante;
        this.rival = rival;
        this.energia = energiaAtacante;
    }

    public String mensaje(){
        return "El bicho "+atacante.getNombre()+" ataco al bicho "+ rival.getNombre()+ " con la energia "+ energia +"." ;
    }
}