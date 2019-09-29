package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import javax.persistence.*;

@Entity
public class ProbabilidadEspecie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;
    @OneToOne(cascade = CascadeType.ALL)
    public Especie especie;
    @Column
    public Integer probabilidad;

    public ProbabilidadEspecie(Especie especie, Integer probabilidad) {
        this.especie = especie;
        this.probabilidad = probabilidad;
    }

    public ProbabilidadEspecie() {
    }
}
