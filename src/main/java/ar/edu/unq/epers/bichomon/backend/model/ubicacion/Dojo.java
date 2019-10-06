package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.Duelo;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.historialDeCampeones.FichaDeCampeon;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Entity
public class Dojo extends Ubicacion {
    //TODO: Pasar a ManyToOne
    @OneToOne
    private Entrenador entrenadorCampeon;
    //TODO: Pasar a ManyToOne
    @OneToOne(cascade = CascadeType.ALL)
    private Bicho bichoCampeon;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Bicho> bichos;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "dojo_nombre")
    private List<FichaDeCampeon> fichas;

    public Dojo(String nombre) {
        super(nombre);
        bichos = new ArrayList<>();
        fichas = new ArrayList<>();

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
    public List<Bicho> bichomonesPara(Entrenador entrenador) {
        return bichos;
    }

    public Boolean tieneCampeon() {
        return false;
    }

    public void declararCampeones(Entrenador entrenador, Bicho bicho) {
        LocalDate fechaDeCambio = LocalDate.now();
        if (hayFichasDeCampeones()) {
            getUltimaFichaCampeon().setFechaFin(fechaDeCambio);
        }
        fichas.add(new FichaDeCampeon(entrenador, bicho, fechaDeCambio));

        entrenadorCampeon = entrenador;
        bichoCampeon = bicho;
        llenarDojo(bicho.getEspecieRaiz());
    }


    private void llenarDojo(Especie especie) {
        IntStream.range(0, 10).mapToObj(i -> new Bicho(especie, "Hije del campeón"))
                .forEach(nuevoBicho -> bichos.add(nuevoBicho));
    }

    public Entrenador getEntrenadorCampeon(){
        return entrenadorCampeon;
    }

    @Override
    public ResultadoCombate comenzarDuelo(Entrenador entrenador) {
        Duelo duelo = new Duelo(entrenador,  this);
        return duelo.pelear();
    }

    public FichaDeCampeon getUltimaFichaCampeon() {
        return fichas.get(fichas.size() - 1);
    }

    public Boolean hayFichasDeCampeones() {
        return fichas.size() > 0;
    }

    public Bicho getBichoCampeon() {
        return bichoCampeon;
    }

    public void setBichoCampeon(Bicho bichoCampeon) {
        this.bichoCampeon = bichoCampeon;
    }
}
