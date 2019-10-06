package ar.edu.unq.epers.bichomon.backend.jdbc.service.bicho;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.BichoDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.EntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.Service;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.BusquedaExitosa;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import java.time.LocalTime;

import static ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner.run;

public class BichoServiceImp extends Service implements BichoService {

    public void setEntrenadorDao(EntrenadorDao entrenadorDao) {
        this.entrenadorDao = entrenadorDao;
    }

    public void setBichoDao(BichoDao bichoDao) {
        this.bichoDao = bichoDao;
    }

    @Override
    public Bicho buscar(String nombreEntrenador) {
        return run(() -> {
            try {
                Entrenador entrenadorRecuperado = this.entrenadorDao.recuperar(nombreEntrenador);

                BusquedaExitosa busqueda = getBusqueda();
                if (busqueda.busquedaExitosa()) {
                    return entrenadorRecuperado.buscar();
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);//ErrorDeBusquedaNoExitosa();
            }
            return null;
        });
    }

    public BusquedaExitosa getBusqueda() {
        return new BusquedaExitosa(LocalTime.of(1, 1, 1, 11111), 1, 1);
    }


    @Override
    public void abandonar(String nombreEntrenador, Integer idBicho) {
        run(() -> {
            Entrenador entrenadorRecuperado = this.entrenadorDao.recuperar(nombreEntrenador);
            Bicho bichoRecuperado = this.bichoDao.recuperar(idBicho);
            if (entrenadorRecuperado.tieneBicho(idBicho)) {
                entrenadorRecuperado.abandonar(bichoRecuperado);
            }
        });
    }

    @Override
    public ResultadoCombate duelo(String entrenador, Integer idBicho) {
        return run(() -> {
            ResultadoCombate resultadoCombate;

            Entrenador entrenadorRecuperado = this.entrenadorDao.recuperar(entrenador);
            Bicho bichoRecuperado = this.bichoDao.recuperar(idBicho);

            if(entrenadorRecuperado.tieneBicho(idBicho)){
                resultadoCombate = entrenadorRecuperado.duelo(bichoRecuperado);
                return resultadoCombate;
            }

           return   null;
        });
    }

    @Override
    public Boolean puedeEvolucionar(String nombreEntrenador, Integer idBicho) {
        return run(() -> {
            Entrenador entrenadorRecuperado = this.entrenadorDao.recuperar(nombreEntrenador);
            Bicho bichoRecuperado = this.bichoDao.recuperar(idBicho);
            if (entrenadorRecuperado.tieneBicho(idBicho)) {
                return bichoRecuperado.puedeEvolucionar();
            }
            return null;
        });
    }

    @Override
    public Bicho evolucionar(String nombreEntrenador, Integer idBicho) {
        return run(() -> {
            Entrenador entrenadorRecuperado = this.entrenadorDao.recuperar(nombreEntrenador);
            Bicho bichoRecuperado = this.bichoDao.recuperar(idBicho);
            if (entrenadorRecuperado.tieneBicho(idBicho)) {
                bichoRecuperado.evolucionar();
                return bichoRecuperado;
            }
            return null;
        });
    }
}
