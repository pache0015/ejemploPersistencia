package ar.edu.unq.epers.bichomon.backend.model.entrenador;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.Duelo;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;


import java.util.ArrayList;
import java.util.List;

public class Entrenador {
    private String  nombre;
    private Integer puntosDeExperiencia;
    private Integer nivel;
    private List<Bicho> bichos;
    private Boolean esCampeon;
    private Boolean ubicadoEnDojo;
    private Bicho bichoParaDuelo;


    public Entrenador(String nombre){
        this.nombre = nombre;
        this.bichos = new ArrayList<>();
        this.esCampeon = false;
        this.ubicadoEnDojo = false;

    }

    public String getNombre(){
        return this.nombre;
    }
    public Integer getPuntosDeExperiencia(){
        return this.puntosDeExperiencia;
    }
    public Integer getNivel(){
        return this.nivel;
    }
    public List<Bicho> getBichos(){
        return this.bichos;
    }
    public Boolean getEsCampeon() {
        return this.esCampeon;
    }


    public void setUbicadoEnDojo(Boolean estado){
        this.ubicadoEnDojo = estado;
    }
    public Duelo retarACampeon(Entrenador this, Entrenador campeon){
        this.setBichoParaDuelo(bichos.get(0));
        return new Duelo(this, campeon);

    }

    private void setBichoParaDuelo(Bicho bicho) {
        this.bichoParaDuelo = bicho;
    }

    public Bicho getBichoParaDuelo() {
        return bichoParaDuelo;
    }
}
