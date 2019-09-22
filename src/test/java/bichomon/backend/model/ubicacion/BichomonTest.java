package bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;

public class BichomonTest {
    public Bicho nuevoBicho(String nombre) {
        Especie especie = new Especie("Especie",  TipoBicho.AIRE);
        return new Bicho(especie, nombre);
    }
}
