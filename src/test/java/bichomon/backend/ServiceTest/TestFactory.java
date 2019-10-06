package bichomon.backend.ServiceTest;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ProveedorDeNiveles;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import java.util.ArrayList;
import java.util.List;

public class TestFactory {

    public static Entrenador nuevoEntrenador(String nombre, Ubicacion ubicacion) {
        Nivel nivel = new Nivel(3, 1,99);
        List niveles = new ArrayList<Nivel>();
        niveles.add(nivel);
        ProveedorDeNiveles proveedor = new ProveedorDeNiveles(niveles);
        return new Entrenador(nombre, ubicacion, proveedor);
    }

    public static Bicho nuevoBicho() {
        Especie especie = new Especie("Especie", TipoBicho.TIERRA);
        return new Bicho(especie);
    }
}
