package ar.edu.unq.epers.bichomon.backend.jdbc.service.especie;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.EntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

import java.util.List;

import static ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner.run;

public class EspecieServiceDaoImp implements EspecieServiceDao {

    private EntrenadorDao entrenadorDao;
    private GuarderiaDao guarderiaDao;

    public void setEntrenadorDao(EntrenadorDao entrenadorDao) {
        this.entrenadorDao = entrenadorDao;
    }
    public void setGuarderiaDao(GuarderiaDao guarderiaDao){ this.guarderiaDao = guarderiaDao}

    @Override
    public List<Bicho> especiesMasPopulares() {
        return run(()-> {
            return this.entrenadorDao.recuperarEspeciesMasPopulares();
        });
    }


    @Override
    public List<Bicho> especiesMenosPopulares() {
        return run(() -> {
            return this.guarderiaDao.recuperarEspeciesMenosPopulares();
        });
    }
}
