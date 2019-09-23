package ar.edu.unq.epers.bichomon.backend.model.entrenador;
import ar.edu.unq.epers.bichomon.backend.dao.impl.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.duelo.Duelo;
import ar.edu.unq.epers.bichomon.backend.ubicaciones.Gimnasio;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Entrenador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nombre;
    @Column
    private Integer puntosDeExperiencia;
    @OneToMany
    private List<Bicho> bichos;
    @OneToOne
    private Bicho bichoParaDuelo;
    @ManyToOne
    private Ubicacion ubicacionActual;
    @ManyToOne (cascade = CascadeType.ALL)
    private Nivel nivel;
    @ManyToOne (cascade = CascadeType.ALL)
    private ProveedorDeNiveles proveedor;

    public Entrenador(String nombre, Ubicacion ubicacion, Nivel nivel, ProveedorDeNiveles proveedor){
        this.nombre = nombre;
        this.bichos = new ArrayList<>();
        this.puntosDeExperiencia = 1;
        this.nivel = nivel;
        this.ubicacionActual = ubicacion;
        this.proveedor = proveedor;
    }

    public Entrenador() {
    }

    public void updateNivel(){
        this.nivel = this.proveedor.getNivelDeEntrenador(this.getPuntosDeExperiencia());
    }

    public String getNombre(){
        return this.nombre;
    }
    public Integer getPuntosDeExperiencia(){
        return this.puntosDeExperiencia;
    }
    public Nivel getNivel(){
        return this.nivel;
    }
    public List<Bicho> getBichos(){
        return this.bichos;
    }

    public Duelo retarACampeon(Gimnasio gym){
        this.setBichoParaDuelo(bichos.get(0));
        return new Duelo(this, gym.getCampeon());

    }

    private void setBichoParaDuelo(Bicho bicho) {
        this.bichoParaDuelo = bicho;
    }

    public Long getId(){
        return this.id;
    }
    public Bicho getBichoParaDuelo() {
        return bichoParaDuelo;
    }

    public void ganarEnergia(Integer cantidadDePuntosDeExperienciaGanada){
        this.puntosDeExperiencia = this.puntosDeExperiencia + cantidadDePuntosDeExperienciaGanada;
        this.updateNivel();
    }
}
