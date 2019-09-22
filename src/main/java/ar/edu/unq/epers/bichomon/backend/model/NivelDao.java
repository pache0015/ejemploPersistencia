package ar.edu.unq.epers.bichomon.backend.model;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;

public interface NivelDao {

    Nivel recuperar(Long id);
    void guardar(Nivel nivel);

}
