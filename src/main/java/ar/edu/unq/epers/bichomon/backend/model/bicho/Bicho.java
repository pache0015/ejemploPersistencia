package ar.edu.unq.epers.bichomon.backend.model.bicho;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import com.mysql.cj.xdevapi.DocumentID;

import javax.persistence.*;

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

	@ManyToOne(cascade = {CascadeType.ALL})
	private Especie especie;

	@Column
	private Double energia;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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
	public String getNombre() {
		return this.nombre;
	}
	
	/**
	 * @return la especie a la que este bicho pertenece.
	 */
	public Especie getEspecie() {
		return this.especie;
	}
	
	/**
	 * @return la cantidad de puntos de energia de este bicho en
	 * particular. Dicha cantidad crecerá (o decrecerá) conforme
	 * a este bicho participe en combates contra otros bichomones.
	 */
	public Double getEnergia() {
		return this.energia;
	}
	public void setEnergia(Double energia) {
		this.energia = energia;
	}


	public void reducirEnergia(Double valor){
		this.energia -= valor;
	}

	public boolean puedeSeguir() {
		return this.getEnergia()>0;
	}

	public Long getId() {
		return this.id;
	}

	public Especie getEspecieRaiz() {
		return especie;
	}
}
