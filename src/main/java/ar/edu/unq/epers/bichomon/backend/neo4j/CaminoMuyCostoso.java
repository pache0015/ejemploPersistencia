package ar.edu.unq.epers.bichomon.backend.neo4j;

public class CaminoMuyCostoso extends RuntimeException {
    public CaminoMuyCostoso(){
        super("El precio es muy costoso");
    }
}
