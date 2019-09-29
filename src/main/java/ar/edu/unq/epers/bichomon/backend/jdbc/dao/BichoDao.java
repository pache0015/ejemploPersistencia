package ar.edu.unq.epers.bichomon.backend.jdbc.dao;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public interface BichoDao {

    Bicho recuperar(int id);
    void guardar(Bicho bicho);
}
