package ar.edu.unq.epers.bichomon.backend.model.condicion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class CondicionMultiple extends Condicion {
    @OneToMany
    private List<Condicion> condiciones;

    public CondicionMultiple(List<Condicion> condiciones) {
        this.condiciones = condiciones;
    }

    public CondicionMultiple() {
    }

    @Override
    public Boolean evaluar(Bicho bicho) {
        return condiciones.stream().allMatch(each -> each.evaluar(bicho));
    }
}
