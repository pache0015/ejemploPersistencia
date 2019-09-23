package bichomon.backend.model.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ProveedorDeNiveles;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ProveedorDeNivelesTest {

    private ProveedorDeNiveles proveedor;
    private Nivel nivelUno  = new Nivel(1, 1, 99);
    private Nivel nivelDos  = new Nivel(2, 100, 400);
    private Nivel nivelTres = new Nivel(3, 401, 1000);
    private Nivel nivelCuatro = new Nivel(4, 1001, 2000);
    private Nivel nivelCinco  = new Nivel(5, 2001, 3000);
    private List<Nivel> niveles = new ArrayList<>();



    @Before
    public void setUp() {
        niveles.add(nivelUno);
        niveles.add(nivelDos);
        niveles.add(nivelTres);
        niveles.add(nivelCuatro);
        niveles.add(nivelCinco);

        proveedor = new ProveedorDeNiveles(niveles);
    }
    @Test
    public void test001_unProovedorSabeDeterminarUnNivelParaUnaCantidadDeExperiencia(){
        Assert.assertEquals(proveedor.getNivelDeEntrenador(10).numeroNivel, nivelUno.numeroNivel);
        Assert.assertEquals(proveedor.getNivelDeEntrenador(399).numeroNivel, nivelDos.numeroNivel);
        Assert.assertEquals(proveedor.getNivelDeEntrenador(1500).numeroNivel, nivelCuatro.numeroNivel);
    }

}
