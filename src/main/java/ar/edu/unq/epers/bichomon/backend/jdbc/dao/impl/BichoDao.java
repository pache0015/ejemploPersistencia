package ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public interface BichoDao {

    Bicho recuperar(Long id);
    void guardar(Bicho bicho);
}
