package ar.edu.unq.epers.bichomon.backend.jdbc.service.especie;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.EntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;

import java.util.List;

import static ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner.run;

public class EspecieServiceDaoImp implements EspecieServiceDao {

    private EspecieDao especieDao;
    private GuarderiaDao guarderiaDao;

    public void setEspecieDao(EspecieDao especieDao) {
        this.especieDao = especieDao;
    }
    public void setGuarderiaDao(GuarderiaDao guarderiaDao){ this.guarderiaDao = guarderiaDao; }

    @Override
    public List<Especie> especiesMasPopulares() {
        return run(()-> {
            return this.especieDao.recuperarEspeciesMasPopulares();
        });
    }


    @Override
    public List<Especie> especiesMenosPopulares() {
        return run(() -> {
            return this.especieDao.recuperarEspeciesMenosPopulares();
        });
    }
    @Override
    public void guardarGuarderia(Guarderia guarderia){
        run(() -> {
            this.guarderiaDao.guardar(guarderia);
        });
    }
}
