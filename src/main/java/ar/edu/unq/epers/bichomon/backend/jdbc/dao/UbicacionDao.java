package ar.edu.unq.epers.bichomon.backend.jdbc.dao;

import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

public interface UbicacionDao {

    void guardar(Ubicacion ubicacion);

    Ubicacion recuperar(String ubicacion);

}

