package bichomon.backend.model;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Condicion;

public class CondicionBasadaEnVictorias extends Condicion {
    private final Integer victorias;

    public CondicionBasadaEnVictorias(Integer victorias) {
        this.victorias = victorias;
    }

    @Override
    public Boolean evaluar(Bicho bicho) {
        return bicho.getVictorias() > victorias;
    }
}
