package ar.edu.unq.epers.bichomon.backend.jdbc.service.especie;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;

import java.util.List;

public interface GuarderiaDao {

    Guarderia recuperar(String nombre_entrenador);
    void guardar(Guarderia guarderia);

}
