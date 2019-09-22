package bichomon.backend.model.ubicacion;

import static org.junit.Assert.*;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.junit.Before;
import org.junit.Test;

public class DojoTest extends BichomonTest {

    private Entrenador entrenador;
    private Dojo dojo;

    @Before
    public void setUp() {
        entrenador = new Entrenador("El Entrenador");
        dojo = new Dojo("El Dojo");
    }

    @Test
    public void un_dojo_sin_campeon_no_tiene_bichos() {
        assertFalse(dojo.tieneCampeon());
        assertTrue(dojo.bichomonesPara(entrenador).isEmpty());
    }

    @Test
    public void un_dojo_con_campeon_tiene_bichos() {
        Bicho bicho = nuevoBicho("Bicho");
        entrenador.capturarBicho(bicho);
        dojo.declararCampeones(entrenador, bicho);

        assertFalse(dojo.bichomonesPara(entrenador).isEmpty());
    }

    @Test
    public void todos_los_bichomones_de_un_dojo_son_de_la_especie_raiz_del_bichomon_campeon() {
        Bicho bichoCampeon = nuevoBicho("Bicho");
        entrenador.capturarBicho(bichoCampeon);
        dojo.declararCampeones(entrenador, bichoCampeon);

        assertTrue(dojo.bichomonesPara(entrenador).stream().allMatch(bicho -> bicho.getEspecie().equals(bichoCampeon.getEspecieRaiz())));
    }

    @Test
    public void un_dojo_no_puede_dejar_abandonar_en_ningun_caso() {
        assertFalse(dojo.puedeDejarAbandonar(entrenador));
    }

    @Test
    public void se_lanza_una_excepcion_si_el_entrenador_intenta_abandonar_un_bichomon_en_un_dojo() {
        Bicho bichoAAbandonar = nuevoBicho("Bicho");
        entrenador.capturarBicho(bichoAAbandonar);
        try {
            dojo.recibirAbandonado(entrenador, bichoAAbandonar);
            fail();
        } catch (RuntimeException e) {
            assertEquals(Ubicacion.ERROR_ABANDONO, e.getMessage());
        }
    }
}
