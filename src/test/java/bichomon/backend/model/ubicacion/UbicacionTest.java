package bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;

class UbicacionTest {
    Entrenador entrenador;

    Bicho nuevoBicho(String nombre) {
        Especie especie = new Especie("Especie",  TipoBicho.AIRE);
        return new Bicho(especie, nombre);
    }

    Especie nuevaEspecie(String nombre) {
        return new Especie(nombre, TipoBicho.AIRE);
    }
}
