package ar.edu.unq.epers.bichomon.backend.service.data;

import ar.edu.unq.epers.bichomon.backend.dao.impl.ConnectionBlock;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.ibatis.jdbc.ScriptRunner;

public class DataServiceImp implements DataService {

    @Override
    public void eliminarDatos() {
        this.executeWithConnection(conn -> {
            PreparedStatement dropTable = conn.prepareStatement("DELETE FROM especie");

            dropTable.execute();

            if (dropTable.getUpdateCount() != 1) {
                throw new RuntimeException("No se elimino correctamente la informacion" );
            }
            dropTable.close();

            return null;
        });
    }


    @Override
    public void crearSetDatosIniciales() {
        Connection connection = this.openConnection();
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        try {
            Reader reader = new BufferedReader(new FileReader("/src/resources/create_all.sql"));
            scriptRunner.runScript(reader );
        } catch (FileNotFoundException e) {
            throw new RuntimeException("No se encontro el archivo", e);
        } finally {
            this.closeConnection(connection);
        }

    }


    private <T> T executeWithConnection(ConnectionBlock<T> bloque) {
        Connection connection = this.openConnection();
        try {
            return bloque.executeWith(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Error no esperado", e);
        } finally {
            this.closeConnection(connection);
        }
    }

    private Connection openConnection() {
        try {

            return DriverManager.getConnection("jdbc:mysql://localhost:3306/bichomonJDBC?user=root");
        } catch (SQLException e) {
            throw new RuntimeException("No se puede establecer una conexion", e);
        }
    }

    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al cerrar la conexion", e);
        }
    }
}
