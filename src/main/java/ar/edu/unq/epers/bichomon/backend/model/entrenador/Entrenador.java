package ar.edu.unq.epers.bichomon.backend.model.entrenador;
import ar.edu.unq.epers.bichomon.backend.model.LimitePokemon;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Entrenador {
    @Id
    private String nombre;
    @Column
    private Integer puntosDeExperiencia;
    @OneToMany
    private List<Bicho> bichos;
    @OneToOne
    private Bicho bichoParaDuelo;
    @Transient
    private Ubicacion ubicacionActual;
    @ManyToOne(cascade = CascadeType.ALL)
    private Nivel nivel;
    @ManyToOne(cascade = CascadeType.ALL)
    private ProveedorDeNiveles proveedor;

    public Entrenador(String nombre, Ubicacion ubicacion, ProveedorDeNiveles proveedor) {
        this.nombre = nombre;
        this.bichos = new ArrayList<>();
        this.puntosDeExperiencia = 1;
        this.nivel = proveedor.getNivelDeEntrenador(this.puntosDeExperiencia);
        this.ubicacionActual = ubicacion;
        this.proveedor = proveedor;
    }

    public Entrenador() {
    }

    public void updateNivel() {
        this.nivel = this.proveedor.getNivelDeEntrenador(this.getPuntosDeExperiencia());
    }

    public String getNombre() {
        return this.nombre;
    }

    public Integer getPuntosDeExperiencia() {
        return this.puntosDeExperiencia;
    }

    public Nivel getNivel() {
        return this.nivel;
    }

    public List<Bicho> getBichos() {
        return this.bichos;
    }


   /*
    public Duelo retarACampeon(Gimnasio gym) {
        this.setBichoParaDuelo(bichos.get(0));
        return new Duelo(this, gym.getCampeon());

    }
    */

    private void setBichoParaDuelo(Bicho bicho) {
        this.bichoParaDuelo = bicho;
    }

    public Bicho getBichoParaDuelo() {
        return bichoParaDuelo;
    }

    public void ganarEnergia(Integer cantidadDePuntosDeExperienciaGanada) {
        this.puntosDeExperiencia = this.puntosDeExperiencia + cantidadDePuntosDeExperienciaGanada;
        this.updateNivel();
    }

    public void capturarBichomon(Bicho unBichoCapturado, Integer puntosDeExperienciaGanados) throws LimitePokemon {
        if (this.puedeAgregarBichomon()) {
            this.bichos.add(unBichoCapturado);
            this.ganarEnergia(puntosDeExperienciaGanados);
        } else {
            throw new LimitePokemon("Tu lista esta llena, sube de nivel para caputar mas bichomons");
        }
    }

    public void ubicarseEn(Ubicacion ubicacion) {
        this.ubicacionActual = ubicacion;
    }

    private boolean puedeAgregarBichomon() {
        return this.nivel.llegoAlLimite(this.bichos.size());
    }

    public boolean tieneMasDeUnBicho() {
        return this.bichos.size() > 1;
    }

    public void abandonar(Bicho bichoAAbandonar) {
        ubicacionActual.recibirAbandonado(this, bichoAAbandonar);
    }

    public void setUbicacionEn(Ubicacion ubicacion) {
        this.ubicacionActual = ubicacion;
    }
}