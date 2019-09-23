package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class ProveedorDeNiveles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Transient
    private List nivelesPosibles = new ArrayList<Nivel>();

    public ProveedorDeNiveles(List<Nivel> niveles){
        this.nivelesPosibles = niveles;
    }

    public ProveedorDeNiveles() {
    }

    public Nivel getNivelDeEntrenador(Integer puntosDeExperiencia) {
        List<Nivel> niveles = this.nivelesPosibles;
       return  niveles.stream().filter(unNivel -> unNivel.getExperienciaMinima() <= puntosDeExperiencia
                                        && unNivel.getExperienciaMaxima() >= puntosDeExperiencia)
                .collect(Collectors.toList()).get(0);

    }



}
