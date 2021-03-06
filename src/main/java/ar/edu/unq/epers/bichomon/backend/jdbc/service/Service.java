package ar.edu.unq.epers.bichomon.backend.jdbc.service;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.BichoDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.EntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.EspecieDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.UbicacionDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import static ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner.run;

public abstract class Service {

    protected BichoDao bichoDao;
    protected EntrenadorDao entrenadorDao;
    protected UbicacionDao ubicacionDao;
    protected EspecieDao especieDao;

    public void setEntrenadorDao(EntrenadorDao entrenadorDao) {
        this.entrenadorDao = entrenadorDao;
    }

    public void setBichoDao(BichoDao bichoDao) {
        this.bichoDao = bichoDao;
    }

    public void setUbicacionDao(UbicacionDao ubicacionDao) {
        this.ubicacionDao = ubicacionDao;
    }

    public void setEspecieDao(EspecieDao especieDao) {
        this.especieDao = especieDao;
    }

    public void guardarBicho(Bicho bicho) {
        run(() -> this.bichoDao.guardar(bicho));
    }

    public void guardarEntrenador(Entrenador entrenador) {
        run(() -> this.entrenadorDao.guardar(entrenador));
    }

    public void guardarUbicacion(Ubicacion ubicacion) {
        run(() -> {
            this.ubicacionDao.guardar(ubicacion);
        });
    }
}
