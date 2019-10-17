package ar.edu.unq.epers.bichomon.backend.jdbc.service.bicho;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

public interface BichoService {

    void guardarEntrenador(Entrenador entrenador);

    void guardarBicho(Bicho bicho);

    Bicho buscar(String nombreEntrenador);

    void abandonar(String nombreEntrenador, Integer idBicho);

    ResultadoCombate duelo(String entrenador, Integer idBicho);

    Boolean puedeEvolucionar(String nombreEntrenador, Integer idBicho);

    Bicho evolucionar(String nombreEntrenador, Integer idBicho);

    void guardarUbicacion(Ubicacion ubicacion);
}
