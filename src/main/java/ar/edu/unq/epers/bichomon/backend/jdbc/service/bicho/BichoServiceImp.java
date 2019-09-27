package ar.edu.unq.epers.bichomon.backend.jdbc.service.bicho;

import ar.edu.unq.epers.bichomon.backend.jdbc.service.entrenador.EntrenadorDaoService;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.Duelo;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.BusquedaExitosa;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.ErrorDeBusquedaNoExitosa;

import java.time.LocalTime;

public class BichoServiceImp implements BichoService{


    private EntrenadorDaoService entrenadorDaoService;
    private  BichoDaoService bichoDaoService;

    public void setEntrenadorDaoService(EntrenadorDaoService entrenadorDaoService){
        this.entrenadorDaoService = entrenadorDaoService;
    }
    public void setBichoDaoService(BichoDaoService bichoDaoService){
        this.bichoDaoService = bichoDaoService;
    }

    @Override
    public Bicho buscar(String nombreEntrenador) {

        try {
            Entrenador entrenadorRecuperado = this.entrenadorDaoService.recuperarEntrenador(nombreEntrenador);

            BusquedaExitosa busqueda
                    = new BusquedaExitosa(LocalTime.of(1, 1, 1, 11111), 1, 1);
            if (busqueda.busquedaExitosa()) {
                return entrenadorRecuperado.getUbicacionActual().bichomonesPara(entrenadorRecuperado).get(0);
            }
        } catch (RuntimeException e) {
            new ErrorDeBusquedaNoExitosa();
        }
        return null;
    }


        @Override
    public void abandonar(String nombreEntrenador, Integer idBicho) {
        Entrenador entrenadorRecuperado = this.entrenadorDaoService.recuperarEntrenador(nombreEntrenador);
        Bicho bichoRecuperado = this.bichoDaoService.recuperarBicho(idBicho);
        if(entrenadorRecuperado.tieneBicho(idBicho)){
            entrenadorRecuperado.abandonar(bichoRecuperado);
        }
    }

    @Override
    public ResultadoCombate duelo(String entrenador, Integer idBicho) {
        Entrenador entrenadorRecuperado = this.entrenadorDaoService.recuperarEntrenador(entrenador);
        Bicho bichoRecuperado = this.bichoDaoService.recuperarBicho(idBicho);
        entrenadorRecuperado.setBichoParaDuelo(bichoRecuperado);

        Duelo duelo = new Duelo(entrenadorRecuperado, entrenadorRecuperado.getUbicacionActual());
        if(entrenadorRecuperado.tieneBicho(idBicho)){
            return duelo.pelear();
        }
        return null;
    }

    @Override
    public Boolean puedeEvolucionar(String nombreEntrenador, Integer idBicho) {
        Entrenador entrenadorRecuperado = this.entrenadorDaoService.recuperarEntrenador(nombreEntrenador);
        Bicho bichoRecuperado = this.bichoDaoService.recuperarBicho(idBicho);
        if(entrenadorRecuperado.tieneBicho(idBicho)){
            return bichoRecuperado.puedeEvolucionar();
        }
        return null;

    }

    @Override
    public Bicho evolucionar(String nombreEntrenador, Integer idBicho) {
        Entrenador entrenadorRecuperado = this.entrenadorDaoService.recuperarEntrenador(nombreEntrenador);
        Bicho bichoRecuperado = this.bichoDaoService.recuperarBicho(idBicho);
        if(entrenadorRecuperado.tieneBicho(idBicho)) {
            bichoRecuperado.evolucionar();
            return bichoRecuperado;
        }
        return null;
    }
}
