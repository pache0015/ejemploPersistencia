package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Guarderia extends Ubicacion {
    @Transient
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
            throw new RuntimeException(Ubicacion.ERROR_ABANDONO);
        }
    }

    public Integer cantidadDeBichos() {
        return abandonos.size();
    }

    public List<Bicho> bichomonesPara(Entrenador entrenador) {
        return bichomones().stream().filter((bicho -> !abandonos.get(bicho).getNombre().equals(entrenador.getNombre()))).collect(Collectors.toList());
    }

    private List<Bicho> bichomones() {
        return new ArrayList<>(abandonos.keySet());
    }
}
