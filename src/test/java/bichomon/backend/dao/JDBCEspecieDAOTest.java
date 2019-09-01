package bichomon.backend.dao;
import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.JDBCEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.DriverManager.*;
import static org.junit.Assert.assertEquals;

public class JDBCEspecieDAOTest {
    private EspecieDAO dao = new JDBCEspecieDAO();
    private Especie especie;

    @Before
    public void setUp() throws FileNotFoundException {
        limpiarBaseDeDatos();
        crearEspecie();
    }

    private void limpiarBaseDeDatos() throws FileNotFoundException {
        try {
            Connection con = getConnection("jdbc:mysql://localhost:3306/bichomonJDBC?user=root&password=root");
            ScriptRunner sr = new ScriptRunner(con);
            Reader reader = new BufferedReader(new FileReader("src/resources/create_all.sql"));
            sr.runScript(reader);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void crearEspecie() {
        this.especie = new Especie();
        this.especie.setPeso(15);
        this.especie.setAltura(198);
        this.especie.setCantidadBichos(2500);
        this.especie.setNombre("pikachu");
        this.especie.setTipo(TipoBicho.ELECTRICIDAD);
    }

    @Test
    public void al_guardar_y_luego_recuperar_se_obtiene_objetos_similares() {
        this.dao.guardar(this.especie);

        Especie especieX = this.dao.recuperar("pikachu");
        assertEquals(this.especie.getNombre(), especieX.getNombre());
        assertEquals(this.especie.getPeso(), especieX.getPeso());
        assertEquals(this.especie.getAltura(), especieX.getAltura());
        assertEquals(this.especie.getTipo(), especieX.getTipo());
        assertEquals(this.especie.getCantidadBichos(), especieX.getCantidadBichos());
    }
}
