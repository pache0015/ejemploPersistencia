package ar.edu.unq.epers.bichomon.backend.jdbc.service.bicho;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.BichoDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.EntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.Service;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.ResultadoDeBusqueda;

import java.time.LocalTime;

import static ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner.run;

public class BichoServiceImpl extends Service implements BichoService {

    @Override
    public Bicho buscar(String nombreEntrenador) {
        return run(() -> {
            try {
                Entrenador entrenadorRecuperado = this.entrenadorDao.recuperar(nombreEntrenador);

                ResultadoDeBusqueda busqueda = getBusqueda();
                if (busqueda.busquedaExitosa()) {
                    return entrenadorRecuperado.buscar();
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);//ErrorDeBusquedaNoExitosa();
            }
            return null;
        });
    }

    public ResultadoDeBusqueda getBusqueda() {
        return new ResultadoDeBusqueda(LocalTime.of(1, 1, 1, 11111), 1, 1);
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
            throw new ErrorBichoNoPerteneceAEntrenador("El bicho no pertence al entrenador");
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
            throw new ErrorBichoNoPerteneceAEntrenador("El bicho no pertence al entrenador");
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
            throw new ErrorBichoNoPerteneceAEntrenador("El bicho no pertence al entrenador");
        });
    }
}
