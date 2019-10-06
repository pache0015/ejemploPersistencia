package bichomon.backend.model.ubicacion;

import static org.junit.Assert.*;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.LimiteBicho;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.ErrorDeBusquedaNoExitosa;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.UbicacionIncorrectaException;
import org.junit.Before;
import org.junit.Test;

public class DojoTest extends UbicacionTest {

    private Dojo dojo;
    private Entrenador entrenador;

    @Before
    public void setUp() {
        entrenador = this.nuevoEntrenador("el entrenador");
        dojo = new Dojo("El Dojo");
    }

    @Test
    public void un_dojo_sin_campeon_no_tiene_bichos() {
        assertFalse(dojo.tieneCampeon());
        try {
            dojo.bichomonPara(entrenador);
            fail();
        } catch (ErrorDeBusquedaNoExitosa e) {
            assertEquals(ErrorDeBusquedaNoExitosa.MENSAJE_ERROR, e.getMessage());
        }
    }

    @Test
    public void un_dojo_con_campeon_tiene_bichos() throws LimiteBicho {
        Bicho bicho = nuevoBicho("Bicho");
        entrenador.capturarBichomon(bicho, 10);
        dojo.declararCampeones(entrenador, bicho);

        assertNotNull(dojo.bichomonPara(entrenador));
    }

    @Test
    public void todos_los_bichomones_de_un_dojo_son_de_la_especie_raiz_del_bichomon_campeon() throws LimiteBicho {
        Bicho bichoCampeon = nuevoBicho("Bicho");
        entrenador.capturarBichomon(bichoCampeon, 10);
        dojo.declararCampeones(entrenador, bichoCampeon);

        assertEquals(dojo.bichomonPara(entrenador).getEspecie(), bichoCampeon.getEspecieRaiz());
    }

    @Test
    public void un_dojo_no_puede_dejar_abandonar_en_ningun_caso() {
        assertFalse(dojo.puedeDejarAbandonar(entrenador));
    }

    @Test
    public void se_lanza_una_excepcion_si_el_entrenador_intenta_abandonar_un_bichomon_en_un_dojo() throws LimiteBicho {
        Bicho bichoAAbandonar = nuevoBicho("Bicho");
        entrenador.capturarBichomon(bichoAAbandonar, 10);
        try {
            dojo.recibirAbandonado(entrenador, bichoAAbandonar);
            fail();
        } catch (UbicacionIncorrectaException e) {
            assertEquals(UbicacionIncorrectaException.MENSAJE_ERROR, e.getMessage());
        }
    }
}
