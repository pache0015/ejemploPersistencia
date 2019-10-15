package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Entity
public class Guarderia extends Ubicacion {

    @OneToMany(cascade = CascadeType.ALL)
    private List<Bicho> abandonados = new ArrayList<>();

    public Guarderia(String nombre) {
        super(nombre);
    }

    public Guarderia() {
    }

    public void recibirAbandonado(Entrenador entrenador, Bicho bichoAAbandonar) {
        if (entrenador.puedeAbandonar()) {
            entrenador.soltarBicho(bichoAAbandonar);
            abandonados.add(bichoAAbandonar);
        } else {
            throw new ErrorAbandonoImposible();
        }
    }

    public Integer cantidadDeBichos() {
        return abandonados.size();
    }

    public Bicho bichomonPara(Entrenador entrenador) {
        try {
            return abandonados.stream().filter(abandonado -> abandonado.noTuvoEntrenador(entrenador)).findFirst().get();
        } catch (NoSuchElementException e) {
            throw new ErrorDeBusquedaNoExitosa();
        }
    }

    @Override
    public Entrenador getEntrenadorCampeon() {
        throw new UbicacionIncorrectaException();
    }

    @Override
    public ResultadoCombate comenzarDuelo(Entrenador entrenador) {
        throw new UbicacionIncorrectaException();
    }

    @Override
    public void declararCampeones(Entrenador entrenador, Bicho bicho) {
        throw new UbicacionIncorrectaException();
    }

    public List<Bicho> getBichosAbandonados() {
        return this.abandonados;
    }
}
