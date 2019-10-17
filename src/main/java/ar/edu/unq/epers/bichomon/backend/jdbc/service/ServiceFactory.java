package ar.edu.unq.epers.bichomon.backend.jdbc.service;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.EspecieDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.GuarderiaDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateEspecieDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.HibernateGuarderiaDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.impl.JDBCEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.data.DataService;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.data.DataServiceImp;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.especie.EspecieService;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.especie.EspecieServiceImpl;

/**
 * Esta clase es un singleton, el cual sera utilizado por equipo de frontend
 * para hacerse con implementaciones a los servicios.
 * 
 * @author Steve Frontend
 * 
 * Gente de backend, una vez que tengan las implementaciones de sus
 * servicios propiamente realizadas apunten a ellas en los metodos provistos
 * debajo. Gracias!
 *
 * Hecho
 * - Gente de Backend
 */
public class ServiceFactory {
	
	/**
	 * @return un objeto que implementa {@link EspecieService}
	 */
	public EspecieService getEspecieService() {
		EspecieDao especieDao = new HibernateEspecieDao();
		GuarderiaDao guarderiaDao = new HibernateGuarderiaDao();
		return new EspecieServiceImpl(especieDao, guarderiaDao);
	}
	
	/**
	 * @return un objeto que implementa {@link DataService}
	 */
	public DataService getDataService() {
		return new DataServiceImp();
	}

}
