package bichomon.backend.factory;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateEntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.condicion.CondicionBasadaEnEnergia;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Experiencia;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ProveedorDeNiveles;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import java.util.List;

public class Factory {

    public static Experiencia experienciaPorCaptura(int puntosGanados, String accion) {
        return new Experiencia(puntosGanados, accion);
    }

    public static Nivel nivel(int numeroNivel, int experienciaMinima, int experienciaMaxima) {
        return new Nivel(numeroNivel, experienciaMinima, experienciaMaxima);
    }

    public static ProveedorDeNiveles proveedorDeNiveles(List<Nivel> niveles) {
        return new ProveedorDeNiveles(niveles);
    }

    public static Especie especieSinEvolucion(String nombre, TipoBicho tipoBicho) {
        return new Especie(nombre, tipoBicho);
    }

    public static Bicho bicho(Especie especie) {
        return new Bicho(especie);
    }

    public static Entrenador entrenador(String nombre, Ubicacion ubicacion, ProveedorDeNiveles proveedor) {
        return new Entrenador(nombre, ubicacion, proveedor);
    }

    public static HibernateEntrenadorDao hibernateEntrenadorDAO() {
        return new HibernateEntrenadorDao();
    }

    public static CondicionBasadaEnEnergia condicionBasadaEnEnergia(int cantidadDeEnergia) {
        return new CondicionBasadaEnEnergia(cantidadDeEnergia);
    }

}
