package ar.edu.unq.epers.bichomon.backend.model;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;

public interface EntrenadorDao {

    Entrenador recuperar(Long id);
    void guardar(Entrenador entrenador);
    Nivel getNivel(Integer puntosDeExperiencia);

}
