package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

public class Camino {
    private String tipo;
    private Integer precio;

    public Camino(String tipo, Integer precio) {
        this.tipo = tipo;
        this.precio = precio;
    }

    public String tipo() {
        return this.tipo;
    }

    public Integer precio() {
        return this.precio;
    }

    public static Camino terrestre() {
        return new Camino("Terrestre", 1);
    }

    public static Camino maritimo() {
        return new Camino("Maritimo", 2);
    }

    public static Camino aereo() {
        return new Camino("Aereo", 5);
    }
}
