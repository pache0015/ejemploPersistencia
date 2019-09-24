package bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ProveedorDeNiveles;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;

import java.util.ArrayList;
import java.util.List;

class UbicacionTest {

    private Nivel nivelUno = new Nivel(1, 1,99);
    private Nivel nivelDos = new Nivel(2,100,300);
    private List<Nivel> niveles = new ArrayList();
    ProveedorDeNiveles proveedor;

    Entrenador nuevoEntrenador(String nombre){
        niveles.add(nivelUno);
        niveles.add(nivelDos);
        proveedor = new ProveedorDeNiveles(niveles);
      return new Entrenador(nombre, null, nivelUno, proveedor);
    }
    Bicho nuevoBicho(String nombre) {
        Especie especie = new Especie("Especie",  TipoBicho.AIRE);
        return new Bicho(especie, nombre);
    }

    Especie nuevaEspecie(String nombre) {
        return new Especie(nombre, TipoBicho.AIRE);
    }
}
