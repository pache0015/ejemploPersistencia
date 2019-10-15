package ar.edu.unq.epers.bichomon.backend.jdbc.service.especie;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;


import java.util.List;

public interface EspecieDao {

    Especie recuperar(String nombre_especie);
    void guardar(Especie especie);

    List<Especie> recuperarEspeciesMasPopulares();

    List<Especie> recuperarEspeciesMenosPopulares();
}
