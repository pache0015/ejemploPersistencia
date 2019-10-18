package ar.edu.unq.epers.bichomon.backend.jdbc.dao;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

public interface UbicacionDao {

    void guardar(Ubicacion ubicacion);
    Guarderia recuperarGuarderia(String nombre_entrenador);
    Ubicacion recuperar(String ubicacion);
    Dojo recuperarDojo(String dojo);
    Bicho recuperarIdCampeonHistoricoEnDojo(String dojo);
}