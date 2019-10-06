package ar.edu.unq.epers.bichomon.backend.jdbc.service.mapa;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public interface MapaService {

    public void mover( String entrenador, String ubicacion);
    public int cantidadDeEntrenadores(String ubicacion);
    public Bicho campeon(String dojo);
    public Bicho campeonHistorico(String dojo);
}
