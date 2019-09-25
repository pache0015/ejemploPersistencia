package ar.edu.unq.epers.bichomon.backend.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract class JDBCDAO {

    /**
     * Ejecuta un bloque de codigo contra una conexion.
     */
    <T> T executeWithConnection(ConnectionBlock<T> bloque) {
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
    Connection openConnection() {
        try {

            return DriverManager.getConnection("jdbc:mysql://localhost:3306/bichomonJDBC?user=root&password=root&useSSL=false");
        } catch (SQLException e) {
            throw new RuntimeException("No se puede establecer una conexion", e);
        }
    }

    /**
     * Cierra una conexion con la base de datos (libera los recursos utilizados por la misma)
     *
     * @param connection - la conexion a cerrar.
     */
    void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al cerrar la conexion", e);
        }
    }
}
