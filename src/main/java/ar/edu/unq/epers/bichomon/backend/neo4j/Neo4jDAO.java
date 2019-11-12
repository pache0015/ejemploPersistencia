package ar.edu.unq.epers.bichomon.backend.neo4j;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Camino;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.neo4j.driver.v1.*;

import java.util.ArrayList;
import java.util.List;

public class Neo4jDAO {

    private Driver driver;

    public Neo4jDAO() {
        this.driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "root"));
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

    public void conectar(String nombreUbicacionUno, String nombreUbicacionDos, Camino camino) {
        Session session = this.driver.session();
        String query = "MATCH (ubicacionUno: Ubicacion {nombre: {nombreUbicacionUno}}) " +
                       "MATCH (ubicacionDos: Ubicacion {nombre: {nombreUbicacionDos}}) " +
                       "MERGE (ubicacionUno)-[c:" + camino.tipo() + "]->(ubicacionDos) " +
                        "SET c.precio = " + camino.precio();
        try{
           session.run(query, Values.parameters(
                                    "nombreUbicacionUno", nombreUbicacionUno,
                                    "nombreUbicacionDos", nombreUbicacionDos
                                   ));

        }finally {
            session.close();
        }
    }
    public List<UbicacionNodo> conectados(String nombreUbicacion, Camino camino){
        Session session = this.driver.session();
        String query = "MATCH (:Ubicacion { nombre: {nombreUbicacion} })-[:" + camino.tipo() + "]->(ubicacionConectada:Ubicacion) " +
                        "RETURN ubicacionConectada";
        try{
            StatementResult result = session.run(query, Values.parameters(
                    "nombreUbicacion", nombreUbicacion));
            return result.list(record ->{
                Value ubicacion = record.get(0);
                String nombre_ubicacion = ubicacion.get("nombre").asString();
                String tipo = ubicacion.get("tipo").asString();
                return new UbicacionNodo(nombre_ubicacion, tipo);
            });
        }finally {
            session.close();
        }
    }
    public List<UbicacionNodo> caminoMasCorto(String entrenador, String ubicacion){
        String ubicacionDelEntrenador = entrenador;
        Session session = this.driver.session();
        String query = "MATCH shorterPath=shortestPath(" +
                       "  (ubicacionEntrenador:Ubicacion {nombre:{ubicacionDelEntrenador}})" +
                "         -[tipoCamino]->(ubicacionFinal :Ubicacion {nombre : {ubicacion}})" +
                ")" +
                "RETURN shorterPath";
        try{
            StatementResult result =  session.run(query, Values.parameters(
                    "ubicacionEntrenador", ubicacionDelEntrenador,
                    "ubicacion", ubicacion
            ));

            return new ArrayList<>();
        }finally {
            session.close();
        }

    }
    public void puedeMover(Entrenador entrenador, String ubicacionFinal) {
        Ubicacion ubicacionActualEntrenador = entrenador.getUbicacionActual();
        if(!this.estaConectadoA(ubicacionActualEntrenador.getNombre(), ubicacionFinal)){
            throw new UbicacionMuyLejana();
        }
        Integer precioCamino = this.precioMinimoEntreUbicaciones(ubicacionActualEntrenador.getNombre(), ubicacionFinal);
        if (!entrenador.puedePagar(precioCamino)) {
            throw new CaminoMuyCostoso();
        }

    }

    public Boolean estaConectadoA(String ubicacionSalida, String ubicacionLlegada){
        Session session = this.driver.session();
        String query = "MATCH (:Ubicacion { nombre: {ubicacionSalida} })-[*]->(ubicacionConectada:Ubicacion  { nombre: {ubicacionLlegada} }) " +
                "RETURN count(ubicacionConectada) > 0";
        try{
            StatementResult result = session.run(query, Values.parameters(
                    "ubicacionSalida", ubicacionSalida,
                                    "ubicacionLlegada", ubicacionLlegada));
            return result.single().get(0).asBoolean();
        }finally {
            session.close();
        }
    }

    public Integer precioMinimoEntreUbicaciones(String ubicacionSalida, String ubicacionLlegada) {
        Session session = this.driver.session();
        String query = "MATCH caminos = (:Ubicacion {nombre: {ubicacionSalida}}) -[*]-> (:Ubicacion { nombre: {ubicacionLlegada} }) " +
                       "RETURN reduce(total = 0, camino IN relationships(caminos)| total + camino.precio) AS reduction";
        try{
            StatementResult result = session.run(query, Values.parameters(
                    "ubicacionSalida", ubicacionSalida,
                    "ubicacionLlegada", ubicacionLlegada));
            return result.list().stream().mapToInt(record -> record.get(0).asInt()).min().getAsInt();
        }finally {
            session.close();
        }
    }

    public void borrarTodo() {
        Session session = this.driver.session();
        String queryOne = "MATCH ()-[r]->() delete r";
        String queryTwo = "MATCH (r) DELETE r";
        try {
            session.run(queryOne);
            session.run(queryTwo);
        } finally {
            session.close();
        }
    }
}
