package bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.LimiteBicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.ErrorAbandonoImposible;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PuebloTest extends UbicacionTest {

    private Pueblo pueblo;
    private Entrenador entrenador;

    @Before
    public void setUp() {
        entrenador = this.nuevoEntrenador("el entrenador");
        pueblo = new Pueblo("El Pueblo");
    }

    @Test
    public void se_lanza_una_excepcion_si_el_entrenador_intenta_abandonar_un_bichomon_en_un_pueblo() throws LimiteBicho {
        Bicho bichoAAbandonar = nuevoBicho("Bicho");
        entrenador.capturarBichomon(bichoAAbandonar, 10);
        try {
            pueblo.recibirAbandonado(entrenador, bichoAAbandonar);
        } catch (ErrorAbandonoImposible e) {
            assertEquals(ErrorAbandonoImposible.MENSAJE_ERROR, e.getMessage());
        }
    }

    @Test
    public void un_pueblo_recien_creado_no_tiene_especies_posibles_de_bichomones_para_capturar() {
        assertTrue(pueblo.especiesPosibles().isEmpty());
    }

    @Test
    public void se_agrega_una_especie_posible_a_un_pueblo_vacio() {
        Especie especie = nuevaEspecie("Especie");
        pueblo.agregarEspecie(especie, 10);

        assertFalse(pueblo.especiesPosibles().isEmpty());
    }

    @Test
    public void no_se_puede_agregar_una_especie_posible_si_sobrepasa_la_probabilidad_total_de_todas_las_especies_de_aparecer() {
        Especie especie = nuevaEspecie("Especie");

        try {
            pueblo.agregarEspecie(especie, 101);
        } catch (RuntimeException e) {
            assertEquals(Pueblo.ERROR_EXCESO_ESPECIES, e.getMessage());
        }
    }

    @Test
    public void un_entrenador_se_queda_con_el_bichomon_que_encuentra_en_un_pueblo() {
        Especie especie = nuevaEspecie("Especie");
        pueblo.agregarEspecie(especie, 100);

        entrenador.ubicarseEn(pueblo);

        assertEquals(entrenador.getBichos().size(), 0);
        entrenador.buscar();
        assertEquals(entrenador.getBichos().size(), 1);
    }
    @Test
    public void un_entrenador_puede_encontrar_mas_de_una_especie_en_el_pueblo(){
        Especie especie = nuevaEspecie("Especie");
        Especie especie_no_Probable = nuevaEspecie("Especie_no_probable");
        Especie especie_poco_probable = nuevaEspecie("Especie_poco_probable");
        pueblo.agregarEspecie(especie, 88);
        pueblo.agregarEspecie(especie_no_Probable, 2);
        pueblo.agregarEspecie(especie_poco_probable, 10);

        entrenador.ubicarseEn(pueblo);

        assertEquals(entrenador.getBichos().size(), 0);
        entrenador.buscar();
        assertEquals(entrenador.getBichos().size(), 1);
        entrenador.buscar();
        assertEquals(entrenador.getBichos().size(), 2);

    }
}
