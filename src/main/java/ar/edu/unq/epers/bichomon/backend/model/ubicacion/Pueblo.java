package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.*;

@Entity
public class Pueblo extends Ubicacion {
    @Transient
    private Map<Especie, Integer> especiesHabitantes;
    @Column
    public static String ERROR_EXCESO_ESPECIES = "No se puede agregar esa especie al pueblo";

    public Pueblo(String nombre) {
        super(nombre);
        especiesHabitantes = new HashMap<>();
    }

    public Pueblo() {
    }

    @Override
    public Boolean puedeDejarAbandonar(Entrenador entrenador) {
        return false;
    }

    @Override
    public void recibirAbandonado(Entrenador entrenador, Bicho bichoAAbandonar) {
        throw new UbicacionIncorrectaException();
    }

    @Override
    public List<Bicho> bichomonesPara(Entrenador entrenador) {
        List<Especie> especiesPosibles = especiesPosibles();

        Random randomGenerator = new Random();
        Especie especieElegida = especiesPosibles.stream()
                .filter((Especie especiePosible) -> especiesHabitantes.get(especiePosible) > randomGenerator.nextInt(101))
                .findFirst().get();

        Bicho bichoElegido = new Bicho(especieElegida, "Bicho de pueblo");
        List<Bicho> bichosParaEntrenador = new ArrayList<>();
        bichosParaEntrenador.add(bichoElegido);
        return bichosParaEntrenador;
    }
    @Override
    public void declararCampeones(Entrenador entrenador, Bicho bicho) {
        throw new UbicacionIncorrectaException();
    }

    public List<Especie> especiesPosibles() {
        return new ArrayList<>(especiesHabitantes.keySet());
    }

    public void agregarEspecie(Especie especie, Integer probabilidadDeAparecer) {
        chequearProbabilidadesTotales(probabilidadDeAparecer);
        especiesHabitantes.put(especie, probabilidadDeAparecer);
    }

    @Override
    @Transient
    public Entrenador getEntrenadorCampeon() {
        throw new UbicacionIncorrectaException();
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
