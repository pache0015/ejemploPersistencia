package ar.edu.unq.epers.bichomon.backend.jdbc.service.especie;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.EntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import java.util.ArrayList;
import java.util.List;

import static ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner.run;

public class EspecieServiceDaoImp implements EspecieServiceDao {

    private EntrenadorDao entrenadorDao;

    public void setEntrenadorDao(EntrenadorDao entrenadorDao) {
        this.entrenadorDao = entrenadorDao;
    }

    @Override
    public List<Bicho> especiesMasPopulares() {
        return run(()-> {
            return this.entrenadorDao.recuperarEspecie();
        });
    }


    @Override
    public List<Especie> especiesMenosPopulares() {
        return null;
    }
}
