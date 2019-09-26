package ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.especie.EspecieExistente;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.especie.EspecieNoExistente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCEspecieDAO implements EspecieDAO {

    public JDBCEspecieDAO() {
    }

    @Override
    public void guardar(Especie especie) {
        try {
            this.executeWithConnection(conn -> {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO especie (nombre, peso, altura, tipo, cantidad_de_bichos) VALUES (?,?,?,?,?)");
                this.setearEspecie(especie, ps);
                ps.execute();
                ps.close();
                return null;
            });
        } catch (RuntimeException e) {
            if (e.getMessage().equals("")) {
                throw new EspecieExistente(especie.getNombre());
            } else if(e.getMessage().equals("")) {
                throw new RuntimeException("No se puede establecer una conexion", e);
            }
            throw new RuntimeException(e);
        }
    }


    @Override
    public void actualizar(Especie especie) {
        try {
            this.executeWithConnection(conn -> {
                PreparedStatement ps = conn.prepareStatement("UPDATE especie SET nombre = ?, peso = ?, altura = ?, tipo = ?, cantidad_de_bichos = ?");
                this.setearEspecie(especie, ps);
                ps.execute();
                ps.close();
                return null;
            });
        } catch (RuntimeException e) {
            throw new EspecieNoExistente(especie.getNombre());
        }
    }

    @Override
    public Especie recuperar(String nombreEspecie) {
        return this.executeWithConnection(conn -> {
            PreparedStatement ps = conn.prepareStatement("SELECT id, nombre, peso, altura, tipo, cantidad_de_bichos FROM especie WHERE nombre = ?");
            ps.setString(1, nombreEspecie);
            ResultSet resultSet = ps.executeQuery();
            Especie especie = null;
            while (resultSet.next()) {
                especie = this.getEspecieResultante(resultSet);
            }
            ps.close();
            return especie;
        });
    }

    @Override
    public List<Especie> recuperarTodos() {
        return this.executeWithConnection(conn -> {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM especie ORDER BY nombre ASC");
            ResultSet resultSet = ps.executeQuery();
            List<Especie> especiesRecuperadas = new ArrayList<>();
            while (resultSet.next()) {
                Especie nuevaEspecieRecuperada = this.getEspecieResultante(resultSet);
                especiesRecuperadas.add(nuevaEspecieRecuperada);
            }
            ps.close();
            return especiesRecuperadas;
        });
    }

    private Especie getEspecieResultante(ResultSet resultSet) throws SQLException {
        Especie especie = new Especie(resultSet.getInt("id"), resultSet.getString("nombre"), TipoBicho.valueOf(resultSet.getString("tipo")));
        especie.setAltura(resultSet.getInt("altura"));
        especie.setCantidadBichos(resultSet.getInt("cantidad_de_bichos"));
        especie.setPeso(resultSet.getInt("peso"));
        return especie;
    }

    private void setearEspecie(Especie especie, PreparedStatement ps) throws SQLException {
        ps.setString(1, especie.getNombre());
        ps.setInt(2, especie.getPeso());
        ps.setInt(3, especie.getAltura());
        ps.setString(4, especie.getTipo().toString());
        ps.setInt(5, especie.getCantidadBichos());
    }
    /**
     * Ejecuta un bloque de codigo contra una conexion.
     */
    private <T> T executeWithConnection(ConnectionBlock<T> bloque) {
        Connection connection = this.openConnection();
        try {
            return bloque.executeWith(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.closeConnection(connection);
        }
    }
    /**
     * Establece una conexion a la url especificada
     *
     * @return la conexion establecida
     */
    private Connection openConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/bichomonJDBC?user=root&useSSL=false& ");
        } catch (SQLException e) {
            throw new RuntimeException("No se puede establecer una conexion", e);
        }
    }
    /**
     * Cierra una conexion con la base de datos (libera los recursos utilizados por la misma)
     *
     * @param connection - la conexion a cerrar.
     */
    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al cerrar la conexion", e);
        }
    }
}
