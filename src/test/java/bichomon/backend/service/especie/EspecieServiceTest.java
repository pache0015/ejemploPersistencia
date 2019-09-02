package bichomon.backend.service.especie;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.JDBCEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceImp;
import ar.edu.unq.epers.bichomon.backend.service.especie.EspecieServiceImpl;
import org.junit.*;
import static org.junit.Assert.assertEquals;

public class EspecieServiceTest {

    private Especie unaEspecie;
    private DataServiceImp dataService = new DataServiceImp();
    private EspecieServiceImpl unaEspecieService;
    private EspecieDAO dao;

    @Before
    public void setUp() {
        unaEspecie = new Especie();
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
    public void seCreaUnBichoParaEspecieYSeVerificaCantidadDeBichosDeEspecieGuardada(){
        unaEspecieService.crearBicho(unaEspecie.getNombre(), "chocho");
        Especie repcupero = unaEspecieService.getEspecie(unaEspecie.getNombre());

        assertEquals(1, repcupero.getCantidadBichos());

    }
}
