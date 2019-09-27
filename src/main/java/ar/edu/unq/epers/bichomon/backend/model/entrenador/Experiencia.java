package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import javax.persistence.*;

@Entity
public class Experiencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String accion;
    @Column
    private Integer puntos;

    public Experiencia(Integer puntosGanados, String accion) {
        this.accion = accion;
        this.puntos = puntosGanados;
    }
    public Integer puntosDeExperiencia(){
        return this.puntos;
    }
    public Long getId(){
        return this.id;
    }
    public Experiencia() {
    }
}
