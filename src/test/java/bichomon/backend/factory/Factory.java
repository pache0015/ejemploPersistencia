package bichomon.backend.factory;

import ar.edu.unq.epers.bichomon.backend.model.condicion.CondicionBasadaEnEnergia;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ProveedorDeNiveles;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;

import java.util.List;

public class Factory {

    public CondicionBasadaEnEnergia getCondicionBasadaEnEnergia(int cantidadDeEnergia) {
        return new CondicionBasadaEnEnergia(cantidadDeEnergia);
    }

    public Guarderia guarderia() {
        return new Guarderia("guarderia");
    }

    public ProveedorDeNiveles proveedorDeNiveles(List niveles) {
        return new ProveedorDeNiveles(niveles);
    }

    public Especie especieSinEvolucion(String nombre, TipoBicho tipoBicho) {
        return new Especie(nombre, tipoBicho);
    }

    public Especie especieConEvolucionYRaiz(String nombre, TipoBicho tipoBicho, Especie evolucionEspecie, Especie raizEspecie) {
        return new Especie(nombre, tipoBicho, evolucionEspecie, raizEspecie);
    }
}
