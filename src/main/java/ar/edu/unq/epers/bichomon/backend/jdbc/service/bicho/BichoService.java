package ar.edu.unq.epers.bichomon.backend.jdbc.service.bicho;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

public interface BichoService {

    public void guardarEntrenador(Entrenador entrenador);
    public void guardarBicho(Bicho bicho);
    public Bicho buscar(String nombreEntrenador);
    public void abandonar(String nombreEntrenador, Integer idBicho);
    public ResultadoCombate duelo(String entrenador, Integer idBicho);
    public Boolean puedeEvolucionar(String nombreEntrenador, Integer idBicho);
    public  Bicho evolucionar(String nombreEntrenador, Integer idBicho);

}
