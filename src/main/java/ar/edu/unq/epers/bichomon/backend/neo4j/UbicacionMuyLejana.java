package ar.edu.unq.epers.bichomon.backend.neo4j;

public class UbicacionMuyLejana extends RuntimeException{
    public UbicacionMuyLejana(){
        super("La ubicacion esta muy lejana");
    }
}
