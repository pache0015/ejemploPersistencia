package ar.edu.unq.epers.bichomon.backend.jdbc.dao;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import java.util.List;

public interface EntrenadorDao {

    Entrenador recuperar(String nombre_entrenador);
    void guardar(Entrenador entrenador);
    List<Entrenador> recuperarTodosEnUbicacion(String ubicacion);
    void actualizarUbicacion(Entrenador entrenador, Ubicacion ubicacionRecuperada);
    List<Entrenador> recuperarCampeones();
    List<Entrenador> recuperarLideres();
}
