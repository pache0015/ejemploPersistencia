package ar.edu.unq.epers.bichomon.backend.jdbc.dao;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import java.util.List;

public interface EspecieDao {

    Especie recuperar(String nombre_especie);

    void guardar(Especie especie);

    List<Especie> recuperarEspeciesMasPopulares();

    List<Especie> recuperarEspeciesMenosPopulares();

    void actualizar(Especie especie);

    List<Especie> recuperarTodos();

    Especie recuperarEspecieLider();
}
