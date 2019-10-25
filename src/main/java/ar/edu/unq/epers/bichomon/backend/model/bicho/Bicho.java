package ar.edu.unq.epers.bichomon.backend.model.bicho;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Un {@link Bicho} existente en el sistema, el mismo tiene un nombre
 * y pertenece a una {@link Especie} en particular.
 *
 * @author Charly Backend
 */
@Entity
public class Bicho {

    @Column
    private String nombre;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Especie especie;
    @Column
    private int victorias;
    @Column
    private LocalDate fechaDeCaptura;
    @ManyToMany
    private List<Entrenador> historialDeEntrenadores = new ArrayList<>();

    @Column
    private Double energia;

    private Double energiaParaDuelo;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private Entrenador entrenadorDueño;

    public Bicho(Especie especie) {
        this.especie = especie;
    }

    public Bicho(Especie especie, String nombre) {
        this.especie = especie;
        this.nombre = nombre;
    }

    public Bicho() {
    }


    /**
     * @return el nombre de un bicho (todos los bichos tienen
     * nombre). Este NO es el nombre de su especie.
     */
    public String getNombre() {return this.nombre;}

    /**
     * @return la especie a la que este bicho pertenece.
     */
    public Especie getEspecie() {return this.especie;}

    /**
     * @return la cantidad de puntos de energia de este bicho en
     * particular. Dicha cantidad crecerá (o decrecerá) conforme
     * a este bicho participe en combates contra otros bichomones.
     */
    public Double getEnergia() {return this.energia;}

    public void setEnergia(Double energia) {
        this.energia = energia;
        this.energiaParaDuelo = energia;
    }

    public Boolean puedeSeguir() {return this.getEnergia() > 0;}

    public Double atacar(Bicho atacado, Double valor) {
        Double energiaPorAtaque = energia * valor;
        atacado.recibirAtaque(energiaPorAtaque);
        return energiaPorAtaque;
    }

    private void recibirAtaque(Double energia) {this.energiaParaDuelo -= energia;}

    public Integer getId() {return this.id;}

    public Especie getEspecieRaiz() {
        return especie.getEspecieRaiz();
    }

    public void evolucionar() {
        if (puedeEvolucionar())
            especie = especie.getEvolucionDeEspecie();
    }

    public Integer getVictorias() {return victorias;}

    public void setVictorias(Integer victorias) {
        this.victorias = victorias;
    }

    public void setFechaDeCaptura(LocalDate fechaDeCaptura) {
        this.fechaDeCaptura = fechaDeCaptura;
    }

    public LocalDate getFechaDeCaptura() {return fechaDeCaptura;}

    public Boolean puedeEvolucionar() {
        return especie.evaluarEvolucion(this);
    }

    public void aumentarEnergiaDeBichoPorDuelo() {
        this.setEnergia((Math.random() * 5.0) + 1.0);
    }

    public void restaurarEnergia(){energiaParaDuelo = energia;}

    public Double getEnergiaPorDuelo() {
        return this.energiaParaDuelo;
    }

    public int nivelEntrenador() {
        return entrenadorDueño.getNivel().numeroNivel;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenadorDueño = entrenador;
    }

    public void agregarEntrenadorAlHistorial(Entrenador entrenador) {
        this.historialDeEntrenadores.add(entrenador);
    }

    public boolean noTuvoEntrenador(Entrenador entrenador) {
        return !this.historialDeEntrenadores.contains(entrenador);
    }

    public Entrenador getEntrenadorDueño(){return this.entrenadorDueño;}

    public void aumentarVictorias() {
        victorias = victorias + 1;
    }
}
