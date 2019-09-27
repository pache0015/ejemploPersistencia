package ar.edu.unq.epers.bichomon.backend.model.condicion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;

@Entity
public class CondicionMultiple extends Condicion {
    @OneToMany
    private ArrayList<Condicion> condiciones;

    public CondicionMultiple(ArrayList<Condicion> condiciones) {
        this.condiciones = condiciones;
    }

    @Override
    public Boolean evaluar(Bicho bicho) {
        return condiciones.stream().allMatch(each -> each.evaluar(bicho));
    }
}
