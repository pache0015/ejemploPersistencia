package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.Duelo;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.historialDeCampeones.FichaDeCampeon;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Dojo extends Ubicacion {
    @ManyToOne
    private Entrenador entrenadorCampeon;
    @ManyToOne(cascade = CascadeType.ALL)
    private Bicho bichoCampeon;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Bicho> bichos;
    @OneToMany(cascade = CascadeType.ALL)
    private List<FichaDeCampeon> fichas;

    public Dojo(String nombre) {
        super(nombre);
        bichos = new ArrayList<>();
        fichas = new ArrayList<>();

    }

    public Dojo() {
    }

    @Override
    public void recibirAbandonado(Entrenador entrenador, Bicho bichoAAbandonar) {
        throw new ErrorAbandonoImposible();
    }

    @Override
    public Bicho bichomonPara(Entrenador entrenador) {
        assertBusquedaExitosa();
        return new Bicho(bichoCampeon.getEspecieRaiz(), "Hije del campeón");
    }

    public void assertBusquedaExitosa() {
        if (bichoCampeon == null) {
            throw new ErrorDeBusquedaNoExitosa();
        }
    }

    public Boolean tieneCampeon() {
        return false;
    }

    public void declararCampeones(Entrenador entrenador, Bicho bicho) {
        LocalDate fechaDeCambio = LocalDate.now();
        if (hayFichasDeCampeones()) {
            getUltimaFichaCampeon().setFechaFin(fechaDeCambio);
        }
        fichas.add(new FichaDeCampeon(entrenador, bicho, fechaDeCambio, this));
        entrenadorCampeon = entrenador;
        bichoCampeon = bicho;
    }

    public Entrenador getEntrenadorCampeon(){
        return entrenadorCampeon;
    }
    public Bicho getBichoCAmpeon(){return bichoCampeon;}


    public ResultadoCombate comenzarDuelo(Bicho bicho) {
        Duelo duelo = new Duelo(bicho,  this);
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

    public void  setFichas(FichaDeCampeon ficha){
        this.fichas.add(ficha);
    }
}
