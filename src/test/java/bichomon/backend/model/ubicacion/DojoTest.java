package bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.LimiteBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.ErrorAbandonoImposible;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.ErrorDeBusquedaNoExitosa;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DojoTest extends UbicacionTest {

    private Dojo dojo;
    private Entrenador entrenador;
    private Entrenador entrenador_nuevoCampeon;
    private Bicho bicho;

    @Before
    public void setUp() {
        entrenador = this.nuevoEntrenador("el entrenador");
        entrenador_nuevoCampeon = this.nuevoEntrenador("el nuevo entrenador");
        bicho = this.nuevoBicho("el bicho");
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
        entrenador.capturarBichomon(bicho, 10);
        dojo.declararCampeones(entrenador, bicho);

        assertNotNull(dojo.bichomonPara(entrenador));
    }

    @Test
    public void todos_los_bichomones_de_un_dojo_son_de_la_especie_raiz_del_bichomon_campeon() throws LimiteBicho {
        entrenador.capturarBichomon(bicho, 10);
        dojo.declararCampeones(entrenador, bicho);

        assertEquals(dojo.bichomonPara(entrenador).getEspecie(), bicho.getEspecieRaiz());
    }

    @Test
    public void se_lanza_una_excepcion_si_el_entrenador_intenta_abandonar_un_bichomon_en_un_dojo() throws LimiteBicho {
        entrenador.capturarBichomon(bicho, 10);
        try {
            dojo.recibirAbandonado(entrenador, bicho);
            fail();
        } catch (ErrorAbandonoImposible e) {
            assertEquals(ErrorAbandonoImposible.MENSAJE_ERROR, e.getMessage());
        }
    }

    @Test
    public void un_entrenador_se_queda_con_el_bichomon_que_encuentra_en_un_dojo() {
        entrenador.ubicarseEn(dojo);
        entrenador.capturarBichomon(bicho, 10);
        dojo.declararCampeones(entrenador, bicho);

        assertEquals(entrenador.getBichos().size(), 1);
        entrenador.buscar();
        assertEquals(entrenador.getBichos().size(), 2);
    }
    @Test
    public void un_entrenador_es_dos_veces_campeon_en_un_mismo_dojo(){
        Bicho bicho_otro_campeon = this.nuevoBicho("otro bicho");
        entrenador.ubicarseEn(dojo);
        entrenador.capturarBichomon(bicho, 1);
        entrenador_nuevoCampeon.ubicarseEn(dojo);
        entrenador_nuevoCampeon.capturarBichomon(bicho_otro_campeon, 1);

        dojo.declararCampeones(entrenador, bicho);

        assertEquals(dojo.getBichoCAmpeon().getNombre(), bicho.getNombre());

        dojo.declararCampeones(entrenador_nuevoCampeon, bicho_otro_campeon);

        assertEquals(dojo.getBichoCAmpeon().getNombre(), bicho_otro_campeon.getNombre());

        dojo.declararCampeones(entrenador, bicho);

        assertEquals(dojo.getBichoCAmpeon().getNombre(), bicho.getNombre());
    }

}