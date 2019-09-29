package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Pueblo extends Ubicacion {
    @OneToMany(cascade = CascadeType.ALL)
    public List<ProbabilidadEspecie> especiesYProbabilidades = new ArrayList<>();
    public static String ERROR_EXCESO_ESPECIES = "No se puede agregar esa especie al pueblo";

    public Pueblo(String nombre) {
        super(nombre);
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

        Random randomGenerator = new Random();
        ProbabilidadEspecie probabilidadEspecieEncontrada = especiesYProbabilidades.stream()
                .filter(probabilidadEspecie -> probabilidadEspecie.probabilidad > randomGenerator.nextInt(101))
                .findFirst().get();

        Bicho bichoElegido = new Bicho(probabilidadEspecieEncontrada.especie);
        List<Bicho> bichosParaEntrenador = new ArrayList<>();
        bichosParaEntrenador.add(bichoElegido);
        return bichosParaEntrenador;
    }
    @Override
    public void declararCampeones(Entrenador entrenador, Bicho bicho) {
        throw new UbicacionIncorrectaException();
    }

    public List<Especie> especiesPosibles() {
        return new ArrayList<>(especiesYProbabilidades.stream().map(probabilidadEspecie -> probabilidadEspecie.especie).collect(Collectors.toList()));
    }

    public void agregarEspecie(Especie especie, Integer probabilidadDeAparecer) {
        chequearProbabilidadesTotales(probabilidadDeAparecer);
        especiesYProbabilidades.add(new ProbabilidadEspecie(especie, probabilidadDeAparecer));
    }

    @Override
    public Entrenador getEntrenadorCampeon() {
        throw new UbicacionIncorrectaException();
    }

    @Override
    public ResultadoCombate comenzarDuelo(Entrenador entrenador) {
        throw new UbicacionIncorrectaException();
    }

    private void chequearProbabilidadesTotales(Integer probabilidadDeAparecer) {
        if (probabilidadTotalesDeAparicion() + probabilidadDeAparecer > 100) {
            throw new RuntimeException(Pueblo.ERROR_EXCESO_ESPECIES);
        }
    }

    private Integer probabilidadTotalesDeAparicion() {
        return especiesYProbabilidades.stream().mapToInt(probabilidadEspecie -> probabilidadEspecie.probabilidad).sum();
    }
}
