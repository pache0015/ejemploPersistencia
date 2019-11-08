package ar.edu.unq.epers.bichomon.backend.neo4j;

import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.neo4j.driver.v1.*;

public class Neo4jDAO {

    private Driver driver;

    public Neo4jDAO() {
        this.driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "root"));
    }

    public void guardar(Ubicacion item) {
        Session session = driver.session();
        try {
            String query = "CREATE (ubicacion:Ubicacion { tipo: {tipo}, nombre: {nombreUbicacion} })";
            session.run(query, Values.parameters(
                    "nombreUbicacion", item.getNombre(), "tipo", item.getClass().getSimpleName()));
        } finally {
            session.close();
        }
    }

    public UbicacionNodo recuperar(String nombre) {
        Session session = this.driver.session();
        String query = "MATCH (ubicacion:Ubicacion {nombre: {unNombre}}) RETURN ubicacion";
        try {
            StatementResult result = session.run(query, Values.parameters("unNombre", nombre));
            Record single = result.single();

            UbicacionNodo ubicacionNodo = new UbicacionNodo(single.get("ubicacion").get("nombre").asString(),
                    single.get("ubicacion").get("tipo").asString());
            return ubicacionNodo;
        } finally {
            session.close();
        }
    }

    public void deleteAllNodes() {
        Session session = this.driver.session();
        String query = "MATCH(R) DETACH DELETE R";
        try {
            session.run(query);
        } finally {
            session.close();
        }
    }
}
