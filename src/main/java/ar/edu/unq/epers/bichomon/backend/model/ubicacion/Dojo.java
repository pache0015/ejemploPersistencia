package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.Duelo;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.historialDeCampeones.GestorDeFichasDeCampeones;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Entity
public class Dojo extends Ubicacion {

    @OneToOne
    private Entrenador entrenadorCampeon;
    @OneToOne
    private Bicho bichoCampeon;
    @OneToMany
    private List<Bicho> bichos;
    @OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private GestorDeFichasDeCampeones gestor;

    public Dojo(String nombre) {
        super(nombre);
        bichos = new ArrayList<>();
        this.gestor = new GestorDeFichasDeCampeones();

    }

    public Dojo() {
    }

    @Override
    public Boolean puedeDejarAbandonar(Entrenador entrenador) {
        return false;
    }

    @Override
    public void recibirAbandonado(Entrenador entrenador, Bicho bichoAAbandonar) {
        throw new UbicacionIncorrectaException();
    }

    @Override
    public Bicho bichomonPara(Entrenador entrenador) {
        if (bichoCampeon == null) {
            return null;
        }
        return new Bicho(bichoCampeon.getEspecieRaiz(), "Hije del campeón");
    }

    public Boolean tieneCampeon() {
        return false;
    }

    public void declararCampeones(Entrenador entrenador, Bicho bicho) {
        gestor.finDeCampeon(entrenadorCampeon, LocalDate.now());
        gestor.addNuevoCampeon(entrenador, bicho, LocalDate.now());

        entrenadorCampeon = entrenador;
        bichoCampeon = bicho;
    }

    public Entrenador getEntrenadorCampeon(){
        return entrenadorCampeon;
    }

    @Override
    public ResultadoCombate comenzarDuelo(Entrenador entrenador) {
        Duelo duelo = new Duelo(entrenador,  this);
        return duelo.pelear();
    }

    public Bicho getBichoCampeon() {
        return bichoCampeon;
    }

    public void setBichoCampeon(Bicho bichoCampeon) {
        this.bichoCampeon = bichoCampeon;
    }
}
