package ar.edu.unq.epers.bichomon.backend.jdbc.dao;

import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;

public interface GuarderiaDao {

    Guarderia recuperar(String nombre_entrenador);

    void guardar(Guarderia guarderia);

}
