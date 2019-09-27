package ar.edu.unq.epers.bichomon.backend.jdbc.service.bicho;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;

public interface BichoService {

    public Bicho buscar(String nombreEntrador);
    public void abandonar(String nombreEntrenador, Integer idBicho);
    public ResultadoCombate duelo(String entrenador, Integer idBicho);
    public Boolean puedeEvolucionar(String nombreEntrenador, Integer idBicho);
    public  Bicho evolucionar(String nombreEntrenador, Integer idBicho);

}
