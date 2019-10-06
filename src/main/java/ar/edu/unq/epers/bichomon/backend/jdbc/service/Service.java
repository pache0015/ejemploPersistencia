package ar.edu.unq.epers.bichomon.backend.jdbc.service;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.BichoDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.EntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.UbicacionDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import static ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner.run;

public abstract class Service {

    protected BichoDao bichoDao;
    protected EntrenadorDao entrenadorDao;
    protected UbicacionDao ubicacionDao;

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
    public void recuperarEspecieConMasCampeonatos() {
        run (() -> {
            this.ubicacionDao.recuperarEspecie();
        });
    }
}
