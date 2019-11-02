package ar.edu.unq.epers.bichomon.backend.neo4j;

public class UbicacionNodo {

    private String nombre;
    private String tipo;

    UbicacionNodo(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }
}
