package bichomon.backend.service.especie;


import ar.edu.unq.epers.bichomon.backend.jdbc.dao.EspecieDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.JDBCEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.data.DataServiceImp;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.especie.EspecieNoExistente;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.especie.EspecieServiceImpl;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import bichomon.backend.factory.Factory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class EspecieEntrenadorDaoServiceTest {

    private Especie unaEspecie;
    private DataServiceImp dataService = new DataServiceImp();
    private EspecieServiceImpl unaEspecieService;
    private EspecieDao dao;

    @Before
    public void setUp() {
        unaEspecie = Factory.especieSinEvolucion("fuego", TipoBicho.AIRE);
        unaEspecie.setPeso(15);
        unaEspecie.setAltura(198);
        unaEspecie.setCantidadBichos(0);
        unaEspecie.setNombre("asd");
        unaEspecie.setTipo(TipoBicho.ELECTRICIDAD);

        dao = new JDBCEspecieDAO();
        unaEspecieService = new EspecieServiceImpl(dao);

        unaEspecieService.crearEspecie(unaEspecie);
    }

    @After
    public void tearDown() {
        this.dataService.eliminarDatos();
    }


    @Test
    public void se_crea_un_bicho_para_especie_y_se_verifica_cantidad_de_bichos_de_especie_guardada(){
        unaEspecieService.crearBicho(unaEspecie.getNombre(), "chocho");
        Especie repcupero = unaEspecieService.getEspecie(unaEspecie.getNombre());

        assertEquals(1, repcupero.getCantidadBichos());

    }

    @Test
    public void intentar_obtener_una_especie_inexistente_provoca_un_error() {
        String nombreInexistente = "Nombre de especie inexistente";
        try {
            unaEspecieService.getEspecie(nombreInexistente);
        } catch (EspecieNoExistente e) {
            assertEquals("No se encuentra la especie [" + nombreInexistente + "]", e.getMessage());
        }
    }

    @Test
    public void recuperar_todos_retorna_todas_las_especies_guardadas() {
        List<Especie> especies = unaEspecieService.getAllEspecies();

        assertEquals(1, especies.size());
        assertEquals(especies.get(0).getNombre(), unaEspecie.getNombre());
    }
}
