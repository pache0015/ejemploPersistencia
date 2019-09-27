package bichomon.backend.model.duelo;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import bichomon.backend.model.entrenador.EntrenadorTest;
import org.junit.Before;

import static org.junit.Assert.*;

public class DueloTest {
    private Entrenador retador;
    private Entrenador rival;



    @Before
    public void setUp(){
        retador = new Entrenador("Ash", null, null);

    }
}
