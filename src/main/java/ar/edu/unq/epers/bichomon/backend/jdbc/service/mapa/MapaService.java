package ar.edu.unq.epers.bichomon.backend.jdbc.service.mapa;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public interface MapaService {

    void mover( String entrenador, String ubicacion);
    int cantidadDeEntrenadores(String ubicacion);
    Bicho campeon(String dojo);
    Bicho campeonHistorico(String dojo);
}
