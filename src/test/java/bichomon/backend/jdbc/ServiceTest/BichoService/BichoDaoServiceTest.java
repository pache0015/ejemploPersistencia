package bichomon.backend.jdbc.ServiceTest.BichoService;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateBichoDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.bicho.BichoDaoService;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BichoDaoServiceTest {

    private Bicho bicho;
    private Especie especie;
    private BichoDaoService service;

    @Before
    public void prepare() {
        especie = new Especie("fuego", TipoBicho.AIRE);
        bicho = new Bicho(especie, "alfredo");
        service = new BichoDaoService();
        service.setBichoDao(new HibernateBichoDao());

    }
    @Test
    public void test01_NoSePuedeRecuperarUnBichoQueNoPerteneceALaBaseDeDatos(){
        this.service.guardarBicho(bicho);
        Bicho bichoRecuperado = this.service.recuperarBicho(bicho.getId());
        Assert.assertEquals(bichoRecuperado.getNombre(), bicho.getNombre());
    }

}
