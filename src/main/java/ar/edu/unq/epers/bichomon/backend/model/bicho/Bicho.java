package ar.edu.unq.epers.bichomon.backend.model.bicho;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

/**
 * Un {@link Bicho} existente en el sistema, el mismo tiene un nombre
 * y pertenece a una {@link Especie} en particular.
 * 
 * @author Charly Backend
 */
public class Bicho {

	private String nombre;
	private Especie especie;
	private Integer energia;
	
	public Bicho(Especie especie, String nombre) {
		this.especie = especie;
		this.nombre = nombre;
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
	public Integer getEnergia() {
		return this.energia;
	}
	public void setEnergia(Integer energia) {
		this.energia = energia;
	}

	public void evolucionarSegunCondicion(Condicion condicion) {
		if(condicion.evaluar(this))
			evolucionar();
	}

	private void evolucionar() {
		especie = especie.getEvolucionDeEspecie();
	}

}
