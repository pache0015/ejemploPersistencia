package ar.edu.unq.epers.bichomon.backend.dao.impl;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class JDBCDataDAO extends JDBCDAO {

    public void eliminarDatos() {
        this.executeWithConnection(conn -> {
            PreparedStatement dropTable = conn.prepareStatement("DELETE FROM especie");

            dropTable.execute();
            dropTable.close();

            return null;
        });
    }

    public void crearDatosIniciales() {
        Connection connection = this.openConnection();
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        try {
            Reader reader = new BufferedReader(new FileReader("src/resources/create_all.sql"));
            scriptRunner.setLogWriter(null);
            scriptRunner.runScript(reader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("No se encontro el archivo", e);
        } finally {
            this.closeConnection(connection);
        }
    }
}
