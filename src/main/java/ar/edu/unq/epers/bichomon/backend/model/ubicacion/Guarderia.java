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
    private List<Abandono> abandonos = new ArrayList<>();

    public Guarderia(String nombre) {
        super(nombre);
    }

    public Guarderia() {
    }

    @Override
    public Boolean puedeDejarAbandonar(Entrenador entrenador) {
        return entrenador.tieneMasDeUnBicho();
    }

    @Override
    public void recibirAbandonado(Entrenador entrenador, Bicho bichoAAbandonar) {
        if (puedeDejarAbandonar(entrenador)) {
            abandonos.add(new Abandono(bichoAAbandonar, entrenador));
        } else {
            throw new UbicacionIncorrectaException();
        }
    }

    public Integer cantidadDeBichos() {
        return abandonos.size();
    }

    public Bicho bichomonPara(Entrenador entrenador) {
        try {
            return abandonos.stream().filter((abandono -> !abandono.abandonador.getNombre().equals(entrenador.getNombre()))).map(abandono -> abandono.bichoAbandonado).findFirst().get();
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
}
