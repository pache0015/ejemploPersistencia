package ar.edu.unq.epers.bichomon.backend.model.condicion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public class CondicionBasadaEnEnergia extends Condicion {

    private Integer energia;

    public CondicionBasadaEnEnergia(Integer cantidadDeEnergia) {
        this.energia = cantidadDeEnergia;;
    }

    public Integer getEnergia() {
        return energia;
    }

    @Override
    public Boolean evaluar(Bicho bicho) {
        return bicho.getEnergia() > energia;
    }
}
