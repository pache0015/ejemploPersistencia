package bichomon.backend.model.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.*;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import bichomon.backend.factory.Factory;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EntrenadorTest {

    private Entrenador entrenador;
    private ProveedorDeNiveles proveedor;
    private Nivel nivelUno = Factory.nivel(1, 1, 99);
    private Nivel nivelDos = Factory.nivel(2, 100, 400);
    private Nivel nivelTres = Factory.nivel(3, 401, 1000);
    private Nivel nivelCuatro = Factory.nivel(4, 1001, 2000);
    private Nivel nivelCinco = Factory.nivel(5, 2001, 3000);
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

        proveedor = Factory.proveedorDeNiveles(niveles);
        entrenador = Factory.entrenador("Ash", null, proveedor);

        bichoUno = new Bicho();
        bichoDos = new Bicho();
        bichoTres= new Bicho();
        experienciaPorCaptura = Factory.experienciaPorCaptura(10, "Capturar bichomon");

    }

    @Test
    public void test_constructoDeEntrenador(){
        List listaDeBichosVacios =  new ArrayList<>();

        assertEquals(entrenador.getBichos(), listaDeBichosVacios);
        assertEquals(entrenador.getNivel().numeroNivel, 1, 0);
        assertEquals(entrenador.getPuntosDeExperiencia(), 1, 0);
    }
    @Test
    public void test001_unEntrenadorTieneUnNivel() {
        assertEquals(entrenador.getNivel().numeroNivel, this.nivelUno.numeroNivel);
    }
    @Test
    public void test002_unEntrandorAumentaSuEnergia(){
        entrenador.ganarEnergia(experienciaPorCaptura.puntosDeExperiencia());

        assertEquals(entrenador.getPuntosDeExperiencia(), 11, 0);
        assertEquals(entrenador.getNivel().numeroNivel, 1, 1);
    }

    @Test
    public void test003_unEntrenadorGanaTantaExperienciaQueSubeDeNivel(){
        Experiencia tabla = Factory.experienciaPorCaptura(200, "Just for testing");


        assertEquals(entrenador.getNivel().numeroNivel, nivelUno.numeroNivel);

        entrenador.ganarEnergia(tabla.puntosDeExperiencia());

        assertEquals(entrenador.getNivel().numeroNivel, nivelDos.numeroNivel);
        assertEquals(entrenador.getPuntosDeExperiencia(), 201, 0);
    }

    @Test
    public void test004_unEntrenadorPuedeCapturarPokemonsYGanaExperienciaPorEllo() throws LimiteBicho {
        entrenador.capturarBichomon(bichoUno, experienciaPorCaptura.puntosDeExperiencia());

        assertEquals(entrenador.getBichos().size(), 1);
    }

    @Test
    public void test005_unEntrenadorNoPuedeCapturarMasBichosDeLoQueSuNivelLePermita(){
        try {
            entrenador.capturarBichomon(bichoUno, experienciaPorCaptura.puntosDeExperiencia());
            entrenador.capturarBichomon(bichoDos, experienciaPorCaptura.puntosDeExperiencia());
            entrenador.capturarBichomon(bichoTres, experienciaPorCaptura.puntosDeExperiencia());
        }catch (LimiteBicho error){
            assertEquals(LimiteBicho.ERROR_LIMITE_DE_BICHOS, error.getMessage());
        }
    }

    @Test
    public void un_entrenador_puede_abandonar_un_pokemon_en_principio_si_tiene_mas_de_uno() throws LimiteBicho {
        Especie especie = Factory.especieSinEvolucion("Especie", TipoBicho.TIERRA);
        entrenador.capturarBichomon(Factory.bicho(especie), 100);
        entrenador.capturarBichomon(Factory.bicho(especie), 10);
        assertTrue(entrenador.puedeAbandonar());
    }
    @Test
    public void un_entrenador_empieza_siempre_con_cero_monedas(){
        assertEquals(0, entrenador.getCantidadDeMonedas()) ;
    }
    @Test
    public void se_le_pueden_setear_las_cantidades_de_monedas_a_un_entrenador(){
        entrenador.setCantidadDeMonedas(3);

        assertEquals(3, entrenador.getCantidadDeMonedas());
    }
    @Test
    public void si_a_un_entrenador_se_le_quitan_mas_monedas_de_las_que_lleva_este_lanza_un_error(){
        entrenador.setCantidadDeMonedas(3);
        try {
            entrenador.quitarUnaCantidadDeMonedas(4);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "El entrenador no tiene la cantidad suficiente de monedas");
        }
    }
}
