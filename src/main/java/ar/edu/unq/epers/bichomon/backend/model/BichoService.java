package ar.edu.unq.epers.bichomon.backend.model;

import ar.edu.unq.epers.bichomon.backend.dao.impl.BichoDao;
import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateBichoDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;

public class BichoService {

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
}
