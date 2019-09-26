package bichomon.backend.model.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.*;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ProveedorDeNiveles;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EntrenadorTest {

    private Entrenador entrenador;
    private ProveedorDeNiveles proveedor;
    private Nivel nivelUno  = new Nivel(1, 1, 99);
    private Nivel nivelDos  = new Nivel(2, 100, 400);
    private Nivel nivelTres = new Nivel(3, 401, 1000);
    private Nivel nivelCuatro = new Nivel(4, 1001, 2000);
    private Nivel nivelCinco  = new Nivel(5, 2001, 3000);
    private List<Nivel> niveles = new ArrayList<>();
    private Bicho bichoUno;
    private Bicho bichoDos;
    private Bicho bichoTres;
    Experiencia experienciaPorCaptura;


    @Before
    public void setUp(){
        niveles.add(nivelUno);
        niveles.add(nivelDos);
        niveles.add(nivelTres);
        niveles.add(nivelCuatro);
        niveles.add(nivelCinco);

        proveedor = new ProveedorDeNiveles(niveles);
        entrenador = new Entrenador("Ash", null, proveedor);

        bichoUno = new Bicho();
        bichoDos = new Bicho();
        bichoTres= new Bicho();
        experienciaPorCaptura = new Experiencia(10, "Capturar bichomon");

    }
    @Test
    public void test_constructoDeEntrenador(){
        List listaDeBichosVacios =  new ArrayList<>();

        Assert.assertEquals(entrenador.getBichos(), listaDeBichosVacios);
        Assert.assertEquals(entrenador.getNivel().numeroNivel, 1, 0);
        Assert.assertEquals(entrenador.getPuntosDeExperiencia(), 1, 0);
    }
    @Test
    public void test001_unEntrenadorTieneUnNivel() {
        Assert.assertEquals(entrenador.getNivel().numeroNivel, this.nivelUno.numeroNivel);
    }
    @Test
    public void test002_unEntrandorAumentaSuEnergia(){
        entrenador.ganarEnergia(experienciaPorCaptura.puntosDeExperiencia());

        Assert.assertEquals(entrenador.getPuntosDeExperiencia(), 11, 0);
        Assert.assertEquals(entrenador.getNivel().numeroNivel, 1, 1);
    }

    @Test
    public void test003_unEntrenadorGanaTantaExperienciaQueSubeDeNivel(){
        Experiencia tabla = new Experiencia(200, "Just for testing");


        Assert.assertEquals(entrenador.getNivel().numeroNivel, nivelUno.numeroNivel);

        entrenador.ganarEnergia(tabla.puntosDeExperiencia());

        Assert.assertEquals(entrenador.getNivel().numeroNivel, nivelDos.numeroNivel);
        Assert.assertEquals(entrenador.getPuntosDeExperiencia(), 201, 0);
    }

    @Test
    public void test004_unEntrenadorPuedeCapturarPokemonsYGanaExperienciaPorEllo() throws LimitePokemon {
        entrenador.capturarBichomon(bichoUno, experienciaPorCaptura.puntosDeExperiencia());

        Assert.assertEquals(entrenador.getBichos().size(), 1);
    }

    @Test
    public void test005_unEntrenadorNoPuedeCapturarMasBichosDeLoQueSuNivelLePermita(){
        try {
            entrenador.capturarBichomon(bichoUno, experienciaPorCaptura.puntosDeExperiencia());
            entrenador.capturarBichomon(bichoDos, experienciaPorCaptura.puntosDeExperiencia());
            entrenador.capturarBichomon(bichoTres, experienciaPorCaptura.puntosDeExperiencia());
        }catch (LimitePokemon error){
            Assert.assertEquals("Tu lista esta llena, sube de nivel para caputar mas bichomons", error.getMessage());
        }

    }
}
