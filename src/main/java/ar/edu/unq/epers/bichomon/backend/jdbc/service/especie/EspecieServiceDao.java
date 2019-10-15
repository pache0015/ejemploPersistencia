package ar.edu.unq.epers.bichomon.backend.jdbc.service.especie;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;

import java.util.List;

public interface EspecieServiceDao {

    List<Especie> especiesMasPopulares();
    List<Especie> especiesMenosPopulares();
    void guardarGuarderia(Guarderia guarderia);

}
