package ar.edu.unq.epers.bichomon.backend.jdbc.service.bicho;

import ar.edu.unq.epers.bichomon.backend.jdbc.service.entrenador.EntrenadorDaoService;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.Duelo;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

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
    public Bicho buscar(String nombreEntrador) {
        return null;
    }

    @Override
    public void abandonar(String nombreEntrenador, int idBicho) {
        Entrenador entrenadorRecuperado = this.entrenadorDaoService.recuperarEntrenador(nombreEntrenador);
        Bicho bichoRecuperado = this.bichoDaoService.recuperarBicho(idBicho);

        //entrenadorRecuperado.buscarBichoPorId(idBicho);

        entrenadorRecuperado.abandonar(bichoRecuperado);
    }

    @Override
    public ResultadoCombate duelo(String entrenador, int idBicho) {
        Entrenador entrenadorRecuperado = this.entrenadorDaoService.recuperarEntrenador(entrenador);

        Bicho bicho = entrenadorRecuperado.buscarBichoPorId(idBicho);
        entrenadorRecuperado.setBichoParaDuelo(bicho);

        Duelo duelo = new Duelo(entrenadorRecuperado, entrenadorRecuperado.getUbicacionActual());

        return duelo.pelear();
    }

    @Override
    public Boolean puedeEvolucionar(String nombreEntrenador, int idBicho) {
        Entrenador entrenadorRecuperado = this.entrenadorDaoService.recuperarEntrenador(nombreEntrenador);
        Bicho bichoRecuperado = this.bichoDaoService.recuperarBicho(idBicho);

        //entrenadorRecuperado.buscarBichoPorId(idBicho);
        return bichoRecuperado.puedeEvolucionar();
    }

    @Override
    public Bicho evolucionar(String nombreEntrenador, int idBicho) {
        Entrenador entrenadorRecuperado = this.entrenadorDaoService.recuperarEntrenador(nombreEntrenador);
        Bicho bichoRecuperado = this.bichoDaoService.recuperarBicho(idBicho);
        bichoRecuperado.evolucionar();
        return bichoRecuperado;
    }
}
