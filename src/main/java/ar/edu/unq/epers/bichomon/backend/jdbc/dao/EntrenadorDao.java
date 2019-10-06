package ar.edu.unq.epers.bichomon.backend.jdbc.dao;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import java.util.List;

public interface EntrenadorDao {

    Entrenador recuperar(String nombre_entrenador);
    void guardar(Entrenador entrenador);

    List<Bicho> recuperarEspecie();
}
