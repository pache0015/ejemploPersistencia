package ar.edu.unq.epers.bichomon.backend.jdbc.service.mapa;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.BichoDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.EntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import java.util.List;

public class MapaServiceImp implements MapaService {

    private EntrenadorDao entrenadorDao;
    private BichoDao bichoDao;
    //private UbicacionDao ubicacionDao;


    public void setEntrenadorDao(EntrenadorDao entrenadorDao) {
        this.entrenadorDao = entrenadorDao;
    }

    public void setBichoDao(BichoDao bichoDao) {
        this.bichoDao = bichoDao;
    }

    //public void setUbicacionDao(UbicacionDao ubicacionDao) {
    //    this.ubicacionDao = ubicacionDao;
    //}

    @Override
    public void mover(String entrenador, String ubicacion) {

        //Entrenador entrenadorRecuperado = this.entrenadorDao.recuperar(entrenador);
        //Ubicacion ubicacionRecuperada = this.ubicacionDao.recuperar(ubicacion);
        //entrenadorRecuperado.ubicarseEn(ubicacionRecuperada);
    }

    @Override
    public int cantidadDeEntrenadores(String ubicacion) {
        List<Entrenador> entrenadoresRecuperadosPorUbicacion = this.entrenadorDao.recuperarTodosEnUbicacion(ubicacion);
        return entrenadoresRecuperadosPorUbicacion.size();
    }

    @Override
    public Bicho campeon(String dojo) {
        //Dojo ubicacionRecuperada = this.ubicacionDao.recuperar(ubicacion);
        //return ubicacionRecuperada.getBichoCampeon();
        return null;
    }

    @Override
    public Bicho campeonHistorico(String dojo) {
        //Bicho bichoHistorico = null;
        //List<Dojo> todosLosDojosRecuperados = this.ubicacionDao.recuperarTodosLosDojos();
//
        //for(Dojo undojo : todosLosDojosRecuperados){
//
//
        //}
        return null;
    }
}
