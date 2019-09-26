package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.ubicaciones.UbicacionIncorrectaException;

import java.util.*;
import java.util.stream.Collectors;

public class Guarderia extends Ubicacion {

    private Map<Bicho, Entrenador> abandonos;

    public Guarderia(String nombre) {
        super(nombre);
        abandonos = new HashMap<Bicho, Entrenador>();
    }

    @Override
    public boolean puedeDejarAbandonar(Entrenador entrenador) {
        return entrenador.tieneMasDeUnBicho();
    }

    @Override
    public void recibirAbandonado(Entrenador entrenador, Bicho bichoAAbandonar) {
        if (puedeDejarAbandonar(entrenador)) {
            abandonos.put(bichoAAbandonar, entrenador);
        } else {
            throw new UbicacionIncorrectaException();
        }
    }

    public Integer cantidadDeBichos() {
        return abandonos.size();
    }

    public List<Bicho> bichomonesPara(Entrenador entrenador) {
        return bichomones().stream().filter((bicho -> !abandonos.get(bicho).getNombre().equals(entrenador.getNombre()))).collect(Collectors.toList());
    }

    @Override
    public Entrenador getEntrenadorCampeon() {
        throw new UbicacionIncorrectaException();
    }

    private List<Bicho> bichomones() {
        return new ArrayList<>(abandonos.keySet());
    }
}
