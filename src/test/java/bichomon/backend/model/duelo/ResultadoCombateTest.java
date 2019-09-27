package bichomon.backend.model.duelo;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.Ataque;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ProveedorDeNiveles;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;

public class ResultadoCombateTest {

    private Especie especie = new Especie();
    private Nivel nivel = new Nivel(1, 1, 99);
    private List niveles = new ArrayList<Nivel>();
    private ProveedorDeNiveles proveedor;
    private Entrenador entrenador;
    private Bicho bicho = new Bicho(especie, "pepito");
    private Bicho bicho1 = new Bicho(especie, "pepoto");
    private ResultadoCombate resultadoCombate;
    private Ataque ataque = new Ataque(bicho, bicho1, 1.00);
    private List<Ataque> ataques;

    @Before
    public void setUp(){
        niveles.add(nivel);
        proveedor = new ProveedorDeNiveles(niveles);
        entrenador = new Entrenador("Juan", null, proveedor);
        ataques = new ArrayList<>();

        ataques.add(ataque);


        resultadoCombate = new ResultadoCombate(entrenador, bicho, ataques);
    }

    @Test
    public void unResultadoCombateRetornaElBichoCampeon(){
        Assert.assertEquals("pepito", resultadoCombate.getBichoCampeon());
    }
    @Test
    public void unResultadoCombateRetornaElEntrenadorCampeon(){        Assert.assertEquals("Juan", resultadoCombate.getNombreDeEntrenadorCampeon());
    }

    @Test
    public void unResultadoCombateTieneAtaques(){
        Assert.assertFalse(resultadoCombate.getAtaques().isEmpty());
    }

}
