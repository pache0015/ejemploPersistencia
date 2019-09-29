package bichomon.backend.model.duelo;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.Ataque;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AtaqueTest {

    private Especie especie = new Especie();
    private Bicho bicho1 = new Bicho(especie, "pepito");
    private Bicho bicho2 = new Bicho(especie, "pepote");
    private Ataque ataque;

    @Before
    public void setUp(){

        ataque = new Ataque(bicho1, bicho2, 10.0);
    }

    @Test
    public void seVerificaQueElMensajeDeAtaqueDevuelvaLoCorrecto(){
        Assert.assertEquals("El bicho pepito ataco al bicho pepote con la energia 10.0.", ataque.mensaje());
    }
}
