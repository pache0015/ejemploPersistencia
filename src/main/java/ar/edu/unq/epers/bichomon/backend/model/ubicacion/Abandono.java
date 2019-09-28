package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.*;

@Entity
public class Abandono {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;
    @OneToOne(cascade = CascadeType.ALL)
    public Bicho bichoAbandonado;
    @OneToOne(cascade = CascadeType.ALL)
    public Entrenador abandonador;

    public Abandono(Bicho bichoAbandonado, Entrenador abandonador) {
        this.bichoAbandonado = bichoAbandonado;
        this.abandonador = abandonador;
    }

    public Abandono() {
    }
}
