package ar.edu.unq.epers.bichomon.backend.jdbc.dao;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

public interface EntrenadorDao {

    Entrenador recuperar(String nombre_entrenador);
    void guardar(Entrenador entrenador);

    // List<Bicho> recuperarEspeciesMasPopulares();

}
