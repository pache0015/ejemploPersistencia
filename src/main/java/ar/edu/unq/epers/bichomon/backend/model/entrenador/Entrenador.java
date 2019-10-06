package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.ResultadoCombate;
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
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Bicho> bichos;
    @ManyToOne(cascade = CascadeType.ALL)
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

    public String getNombre() {return this.nombre;}

    public Integer getPuntosDeExperiencia() {return this.puntosDeExperiencia;}

    public Nivel getNivel() {return this.nivel;}

    public List<Bicho> getBichos() {return this.bichos;}

    public Ubicacion getUbicacionActual(){return this.ubicacionActual;}

    public Boolean tieneBicho(Integer idBicho){
        return bichos.stream().anyMatch((bicho -> bicho.getId().equals(idBicho)));
    }

    public void ganarEnergia(Integer cantidadDePuntosDeExperienciaGanada) {
        this.puntosDeExperiencia = this.puntosDeExperiencia + cantidadDePuntosDeExperienciaGanada;
        this.updateNivel();
    }

    public void capturarBichomon(Bicho unBichoCapturado, Integer puntosDeExperienciaGanados) {
        if (this.puedeAgregarBichomon()) {
            this.bichos.add(unBichoCapturado);
            this.ganarEnergia(puntosDeExperienciaGanados);
            unBichoCapturado.setEntrenadorDueño(this);
        } else {
            throw new LimiteBicho("Tu lista esta llena, sube de nivel para caputar mas bichomons");
        }
    }

    public void ubicarseEn(Ubicacion ubicacion) {this.ubicacionActual = ubicacion;}

    private Boolean puedeAgregarBichomon() {
        return this.nivel.llegoAlLimite(this.bichos.size());
    }

    public Boolean tieneMasDeUnBicho() {return this.bichos.size() > 1;}

    public void abandonar(Bicho bichoAAbandonar) {
        ubicacionActual.recibirAbandonado(this, bichoAAbandonar);
    }

    public ResultadoCombate duelo(Bicho bichoParaDuelo){
        return  getUbicacionActual().comenzarDuelo(bichoParaDuelo);
    }

    public void moverseA(Ubicacion ubicacion) {this.ubicacionActual = ubicacion;}

    public Boolean tieneNivelNecesario(Integer nivelNecesarioDeEntrenador) {
        return this.getNivel().numeroNivel <= nivelNecesarioDeEntrenador;
    }

    public Bicho buscar() {return this.getUbicacionActual().bichomonPara(this);}
}