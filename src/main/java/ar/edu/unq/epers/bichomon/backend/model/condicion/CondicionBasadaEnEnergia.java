package ar.edu.unq.epers.bichomon.backend.model.condicion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class CondicionBasadaEnEnergia extends Condicion {
    @Column
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
