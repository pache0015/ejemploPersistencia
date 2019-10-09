package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Bicho> bichomonesPara(Entrenador entrenador) {
        return abandonos.stream().filter((abandono -> !abandono.abandonador.getNombre().equals(entrenador.getNombre()))).map(abandono -> abandono.bichoAbandonado).collect(Collectors.toList());
    }

    @Override
    public ResultadoCombate comenzarDuelo(Bicho bicho) {
        throw new UbicacionIncorrectaException();
    }

}
