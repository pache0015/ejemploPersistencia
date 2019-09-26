package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.ubicaciones.UbicacionIncorrectaException;

import java.util.*;

public class Pueblo extends Ubicacion {
    private Map<Especie, Integer> especiesHabitantes;
    public static String ERROR_EXCESO_ESPECIES = "No se puede agregar esa especie al pueblo";

    public Pueblo(String nombre) {
        super(nombre);
        especiesHabitantes = new HashMap<>();
    }

    @Override
    public boolean puedeDejarAbandonar(Entrenador entrenador) {
        return false;
    }

    @Override
    public void recibirAbandonado(Entrenador entrenador, Bicho bichoAAbandonar) {
        throw new UbicacionIncorrectaException();
    }

    // TODO: Llenar este método o reemplazarlo en todas las subclases por bichomonPara(Entrenador entrenador)
    @Override
    public List<Bicho> bichomonesPara(Entrenador entrenador) {
        return new ArrayList<>();
    }

    @Override
    public Entrenador getEntrenadorCampeon() {
        throw new UbicacionIncorrectaException();
    }

    public List<Especie> especiesPosibles() {
        return new ArrayList<>(especiesHabitantes.keySet());
    }

    public Bicho bichomonPara(Entrenador entrenador) {
        List<Especie> especiesPosibles = especiesPosibles();

        Random randomGenerator = new Random();
        Especie especieElegida = especiesPosibles.stream().filter((Especie especiePosible) -> {
            return especiesHabitantes.get(especiePosible) > randomGenerator.nextInt(101);
        }).findFirst().get();

        return new Bicho(especieElegida, "Bicho de pueblo");
    }

    public void agregarEspecie(Especie especie, Integer probabilidadDeAparecer) {
        chequearProbabilidadesTotales(probabilidadDeAparecer);
        especiesHabitantes.put(especie, probabilidadDeAparecer);
    }

    private void chequearProbabilidadesTotales(Integer probabilidadDeAparecer) {
        if (probabilidadTotalesDeAparicion() + probabilidadDeAparecer > 100) {
            throw new RuntimeException(Pueblo.ERROR_EXCESO_ESPECIES);
        }
    }

    private Integer probabilidadTotalesDeAparicion() {
        return especiesHabitantes.values().stream().mapToInt(Integer::intValue).sum();
    }
}
