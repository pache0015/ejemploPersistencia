package bichomon.backend.ServiceTest.BichoService;

import ar.edu.unq.epers.bichomon.backend.dao.impl.HibernateBichoDao;
import ar.edu.unq.epers.bichomon.backend.model.BichoService;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BichoEntrenadorService {

    private Bicho bicho;
    private Especie especie;
    private BichoService bichoService;

    @Before
    public void prepare() {
        especie = new Especie("fuego", TipoBicho.AIRE);
        bicho = new Bicho(especie, "alfredo");
        bichoService = new BichoService();
        bichoService.setBichoDao(new HibernateBichoDao());

    }
    @Test
    public void test01_NoSePuedeRecuperarUnBichoQueNoPerteneceALaBaseDeDatos(){
        this.bichoService.guardarBicho(bicho);
        Bicho bichoRecuperado = this.bichoService.recuperarBicho(bicho.getId());
        Assert.assertEquals(bichoRecuperado.getNombre(), bicho.getNombre());
    }

}
