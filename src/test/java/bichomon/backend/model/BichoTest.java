package bichomon.backend.model;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.bicho.CondicionBasadaEnEnergia;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import org.junit.Assert;
import org.junit.Test;

import java.time.*;

public class BichoTest {

    @Test
    public void cuandoCumpleUnaCondicionBasadaEnEnergiaEvolucionaYCambiaSuEspecie(){
        Especie reptilmon = new Especie("Reptilmon", TipoBicho.TIERRA);
        Especie lagartomon = new Especie("Lagartomon", TipoBicho.TIERRA, reptilmon);

        Bicho bicho = new Bicho(lagartomon, "helloworld");
        bicho.setEnergia(1);

        bicho.evolucionarSegunCondicion(condicionDeEnergia(0));
        Assert.assertEquals(bicho.getEspecie(), reptilmon);
    }

    @Test
    public void cuandoNoCumpleUnaCondicionBasadaEnEnergiaNoEvoluciona(){
        Especie reptilmon = new Especie("Reptilmon", TipoBicho.TIERRA);
        Especie lagartomon = new Especie("Lagartomon", TipoBicho.TIERRA, reptilmon);

        Bicho bicho = new Bicho(lagartomon, "helloworld");
        bicho.setEnergia(0);

        bicho.evolucionarSegunCondicion(condicionDeEnergia(0));
        Assert.assertEquals(bicho.getEspecie(), lagartomon);
    }

    @Test
    public void cuandoCumpleUnaCondicionBasadaEnVictoriasEvolucionaYCambiaSuEspecie(){
        Especie reptilmon = new Especie("Reptilmon", TipoBicho.TIERRA);
        Especie lagartomon = new Especie("Lagartomon", TipoBicho.TIERRA, reptilmon);

        Bicho bicho = new Bicho(lagartomon, "helloworld");
        bicho.setVictorias(3);

        bicho.evolucionarSegunCondicion(new CondicionBasadaEnVictorias(2));
        Assert.assertEquals(bicho.getEspecie(), reptilmon);
    }

    @Test
    public void cuandoCumpleUnaCondicionBasadaEnEdadEvolucionaYCambiaSuEspecie(){
        Especie reptilmon = new Especie("Reptilmon", TipoBicho.TIERRA);
        Especie lagartomon = new Especie("Lagartomon", TipoBicho.TIERRA, reptilmon);

        Bicho bicho = new Bicho(lagartomon, "helloworld");
        bicho.setFechaDeCaptura(LocalDate.of(2019, Month.JANUARY,1));

        LocalDate localDate = LocalDate.of(2019, Month.JANUARY, 2);
        bicho.evolucionarSegunCondicion(new CondicionBasadaEnEdad(Period.ofDays(1), localDate));
        Assert.assertEquals(bicho.getEspecie(), reptilmon);
    }



    private CondicionBasadaEnEnergia condicionDeEnergia(Integer cantidadDeEnergia) {
        return new CondicionBasadaEnEnergia(cantidadDeEnergia);
    }
}
