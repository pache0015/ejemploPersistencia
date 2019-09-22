package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.Duelo;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;


import java.util.ArrayList;
import java.util.List;

public class Entrenador {
    private String nombre;
    private Integer puntosDeExperiencia;
    private Integer nivel;
    private List<Bicho> bichos;
    private Boolean esCampeon;
    private Ubicacion ubicacionActual;
    private Bicho bichoParaDuelo;


    public Entrenador(String nombre) {
        this.nombre = nombre;
        this.bichos = new ArrayList<>();
        this.esCampeon = false;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Integer getPuntosDeExperiencia() {
        return this.puntosDeExperiencia;
    }

    public Integer getNivel() {
        return this.nivel;
    }

    public List<Bicho> getBichos() {
        return this.bichos;
    }

    public Boolean getEsCampeon() {
        return this.esCampeon;
    }

    public Duelo retarACampeon(Entrenador this, Entrenador campeon) {
        this.setBichoParaDuelo(bichos.get(0));
        return new Duelo(this, campeon);

    }

    public Bicho getBichoParaDuelo() {
        return bichoParaDuelo;
    }

    public void capturarBicho(Bicho bicho) {
        this.bichos.add(bicho);
    }

    public boolean tieneMasDeUnBicho() {
        return bichos.size() > 1;
    }

    public void ubicarseEn(Ubicacion ubicacion) {
        this.ubicacionActual = ubicacion;
    }

    private void setBichoParaDuelo(Bicho bicho) {
        this.bichoParaDuelo = bicho;
    }

    public void abandonar(Bicho bichoAAbandonar) {
        ubicacionActual.recibirAbandonado(this, bichoAAbandonar);
    }
}
