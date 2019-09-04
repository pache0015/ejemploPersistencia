package bichomon.backend.dao;
import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.JDBCEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceImp;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class JDBCEspecieDAOTest {
    private EspecieDAO dao = new JDBCEspecieDAO();
    private DataServiceImp dataService = new DataServiceImp();
    private Especie especie;
    private String nombreBichomon = "Pikachu";

    @Before
    public void setUp() {
        dataService.crearSetDatosIniciales();
        this.especie = crearEspecie(this.nombreBichomon);
    }

    @After
    public void tearDown() {
        dataService.eliminarDatos();
    }

    private Especie crearEspecie(String nombreBichomon) {
        Especie especie = new Especie();
        especie.setPeso(15);
        especie.setAltura(198);
        especie.setCantidadBichos(2500);
        especie.setNombre(nombreBichomon);
        especie.setTipo(TipoBicho.ELECTRICIDAD);
        return especie;
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
    @Test
    public void se_actualiza_una_especie_guardada() {
        Integer alturaSinActualizar = this.especie.getAltura();
        Integer nuevaAltura = 1;

        this.dao.guardar(this.especie);
        this.especie.setAltura(nuevaAltura);

        this.dao.actualizar(this.especie);

        Especie especieRecuperada = this.dao.recuperar(this.nombreBichomon);

        assertNotEquals(alturaSinActualizar, especieRecuperada.getAltura(), 0);
    }

    @Test
    public void se_pide_la_lista_de_todas_las_especies(){
        this.dao.guardar(this.especie);
        this.dao.guardar(crearEspecie("El vamo a calmarno"));

        Integer cantidad_de_especies  = this.dao.recuperarTodos().size();

        assertEquals(2, cantidad_de_especies, 0);
    }

    @Test(expected = RuntimeException.class)
    public void no_se_puede_agregar_dos_especies_con_el_mismo_nombre() {
        this.dao.guardar(this.especie);
        this.dao.guardar(this.especie);
    }
}
