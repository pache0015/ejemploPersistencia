package bichomon.backend.model.ubicacion;

import static org.junit.Assert.*;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.LimiteBicho;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.ErrorDeBusquedaNoExitosa;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.UbicacionIncorrectaException;
import org.junit.Before;
import org.junit.Test;


public class GuarderiaTest extends UbicacionTest {

    private Guarderia guarderia;
    private Entrenador entrenador;

    @Before
    public void setUp() {
        entrenador = this.nuevoEntrenador("el entrenador");
        guarderia = new Guarderia("La Guarderia");
    }

    @Test
    public void una_guarderia_recien_creada_no_tiene_bichos_abandonados() {
        assertEquals(guarderia.cantidadDeBichos(), (Integer)0);
    }

    @Test
    public void un_entrenador_no_puede_abandonar_su_ultimo_pokemon_en_este_lugar() {
        assertFalse(guarderia.puedeDejarAbandonar(entrenador));
    }

    @Test
    public void un_entrenador_puede_abandonar_un_pokemon_en_este_lugar_si_tiene_mas_de_uno() throws LimiteBicho {
        entrenador.capturarBichomon(nuevoBicho("Bicho Uno"), 10);
        entrenador.capturarBichomon(nuevoBicho("Bicho Dos"), 10);
        assertTrue(guarderia.puedeDejarAbandonar(entrenador));
    }

    @Test
    public void la_cantidad_de_bichos_en_guarderia_aumenta_cuando_un_jugador_abandona_un_pokemon() throws LimiteBicho {
        Bicho bichoAAbandonar = nuevoBicho("Bicho Uno");
        entrenador.capturarBichomon(bichoAAbandonar, 10);
        entrenador.capturarBichomon(nuevoBicho("Bicho Dos"), 10);
        entrenador.ubicarseEn(guarderia);
        entrenador.abandonar(bichoAAbandonar);

        assertEquals(guarderia.cantidadDeBichos(), (Integer)1);
    }

    @Test
    public void se_genera_un_error_al_intentar_abandonar_en_una_guarderia_si_no_se_cumplen_las_condiciones_necesarias() throws LimiteBicho {
        Bicho bichoAAbandonar = nuevoBicho("Bicho Uno");
        entrenador.capturarBichomon(bichoAAbandonar, 10);
        entrenador.ubicarseEn(guarderia);

        try {
            entrenador.abandonar(bichoAAbandonar);
            fail();
        } catch(UbicacionIncorrectaException e) {
            assertEquals(UbicacionIncorrectaException.MENSAJE_ERROR, e.getMessage());
        }
    }

    @Test
    public void un_entrenador_no_puede_recapturar_un_bichomon_que_ha_abandonado_previamente() throws LimiteBicho {
        Bicho bichoAAbandonar = nuevoBicho("Bicho Uno");
        entrenador.capturarBichomon(bichoAAbandonar, 10);
        entrenador.capturarBichomon(nuevoBicho("Bicho Dos"), 10);
        entrenador.ubicarseEn(guarderia);

        entrenador.abandonar(bichoAAbandonar);

        try {
            guarderia.bichomonPara(entrenador);
        } catch (ErrorDeBusquedaNoExitosa e) {
            assertEquals(ErrorDeBusquedaNoExitosa.MENSAJE_ERROR, e.getMessage());
        }
    }

    @Test
    public void un_entrenador_puede_capturar_un_bichomon_que_otro_entrenador_ha_abandonado_previamente() throws LimiteBicho {
        Bicho bichoAAbandonar = nuevoBicho("Bicho Uno");
        entrenador.capturarBichomon(bichoAAbandonar, 01);
        entrenador.capturarBichomon(nuevoBicho("Bicho Dos"), 10);
        entrenador.ubicarseEn(guarderia);

        entrenador.abandonar(bichoAAbandonar);

        Entrenador entrenadorDos = this.nuevoEntrenador("Entrenador_Dos");

        assertEquals(guarderia.bichomonPara(entrenadorDos), bichoAAbandonar);
    }
}
