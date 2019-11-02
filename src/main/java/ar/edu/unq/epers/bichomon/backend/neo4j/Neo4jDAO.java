package ar.edu.unq.epers.bichomon.backend.neo4j;

import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.neo4j.driver.v1.*;

import java.util.List;

public class Neo4jDAO {

    private Driver driver;

    public Neo4jDAO() {
        this.driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "password"));
    }

    public void guardar(Ubicacion ubicacion) {
        Session session = driver.session();
        try {
            String query = "MERGE (ubicacion:Ubicacion {nombre: {nombre}, tipo: {tipo} }) ";
          session.run(query, Values.parameters(
            "nombre", ubicacion.getNombre(), "tipo", ubicacion.getClass().getSimpleName()));
        } finally {
            session.close();
        }
    }

    public UbicacionNodo recuperar(String nombre) {
        Session session = this.driver.session();
        String query = "MATCH (ubicacion{nombre: {unNombre}}) RETURN ubicacion";
        try {

            StatementResult result = session.run(query, Values.parameters("unNombre", nombre));
            return result.list(record ->{
               Value ubicacion = record.get(0);
               String nombre_ubicacion = ubicacion.get("nombre").asString();
               String tipo = ubicacion.get("tipo").asString();
                return new UbicacionNodo(nombre_ubicacion, tipo);
            }).get(0);

        } finally {
            session.close();
        }
    }

    public void conectar(String nombreUbicacionUno, String nombreUbicacionDos, String tipoCamino) {
        Session session = this.driver.session();
        String query = "MATCH (ubicacionUno: Ubicacion {nombre: {nombreUbicacionUno}}) " +
                       "MATCH (ubicacionDos: Ubicacion {nombre: {nombreUbicacionDos}}) " +
                       "MERGE (ubicacionUno)-[:{tipoCamino}]->(ubicacionDos)";
        try{
           session.run(query, Values.parameters(
                     "tipoCamino", tipoCamino,
                                    "nombreUbicacionUno", nombreUbicacionUno,
                                    "nombreUbicacionDos", nombreUbicacionDos
                                   ));

        }finally {
            session.close();
        }
    }
}
