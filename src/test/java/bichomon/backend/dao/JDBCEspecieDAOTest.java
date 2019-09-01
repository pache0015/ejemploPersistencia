package bichomon.backend.dao;
import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.JDBCEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceImp;
import org.junit.*;
import static org.junit.Assert.assertEquals;

public class JDBCEspecieDAOTest {
    private EspecieDAO dao = new JDBCEspecieDAO();
    private DataServiceImp dataService = new DataServiceImp();
    private Especie especie;
    private String nombreBichomon = "Pikachu";

    @Before
    public void setUp() {
        dataService.crearSetDatosIniciales();
        crearEspecie();
    }

    @After
    public void tearDown() {
        dataService.eliminarDatos();
    }

    private void crearEspecie() {
        this.especie = new Especie();
        this.especie.setPeso(15);
        this.especie.setAltura(198);
        this.especie.setCantidadBichos(2500);
        this.especie.setNombre(this.nombreBichomon);
        this.especie.setTipo(TipoBicho.ELECTRICIDAD);
    }

    @Test
    public void al_guardar_y_luego_recuperar_se_obtiene_objetos_similares() {
        this.dao.guardar(this.especie);

        Especie especieX = this.dao.recuperar(this.nombreBichomon);
        assertEquals(this.especie.getNombre(), especieX.getNombre());
        assertEquals(this.especie.getPeso(), especieX.getPeso());
        assertEquals(this.especie.getAltura(), especieX.getAltura());
        assertEquals(this.especie.getTipo(), especieX.getTipo());
        assertEquals(this.especie.getCantidadBichos(), especieX.getCantidadBichos());

    }
}
