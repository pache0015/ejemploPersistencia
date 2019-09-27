package ar.edu.unq.epers.bichomon.backend.model;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.BichoDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateBichoDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.Duelo;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.ubicaciones.UbicacionIncorrectaException;

import static ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner.run;

public class BichoDaoService {

    private BichoDao bichoDao;

    public void setBichoDao(HibernateBichoDao hibernateBichoDao) {
        this.bichoDao = hibernateBichoDao;
    }

    public void guardarBicho(Bicho bicho){
        run(() -> { this.bichoDao.guardar(bicho);});
    }
    public Bicho recuperarBicho(Long id_bicho){
        return run(()-> this.bichoDao.recuperar(id_bicho));
    }

    public ResultadoCombate duelo(Entrenador entrenador, Integer idBicho) {
        Duelo duelo = new Duelo(entrenador, entrenador.getUbicacionActual());
        return duelo.pelear();
    }
}
