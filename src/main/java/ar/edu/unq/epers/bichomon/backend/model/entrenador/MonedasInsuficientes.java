package ar.edu.unq.epers.bichomon.backend.model.entrenador;

public class MonedasInsuficientes extends RuntimeException {

    public MonedasInsuficientes(){
        super("El entrenador no tiene la cantidad suficiente de monedas");
    }
}
