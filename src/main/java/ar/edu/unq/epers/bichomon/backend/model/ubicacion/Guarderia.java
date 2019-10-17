package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Entity
public class Guarderia extends Ubicacion {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
    public ResultadoCombate comenzarDuelo(Bicho bicho) {
        throw new UbicacionIncorrectaException();
    }

    public List<Bicho> getBichosAbandonados() {
        return this.abandonados;
    }
}
