package bichomon.backend.model;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Condicion;

import java.util.ArrayList;

public class CondicionMultiple extends Condicion {

    private ArrayList<Condicion> condiciones;

    public CondicionMultiple(ArrayList<Condicion> condiciones) {
        this.condiciones = condiciones;
    }

    @Override
    public Boolean evaluar(Bicho bicho) {
        return condiciones.stream().allMatch(each -> each.evaluar(bicho));
    }
}
