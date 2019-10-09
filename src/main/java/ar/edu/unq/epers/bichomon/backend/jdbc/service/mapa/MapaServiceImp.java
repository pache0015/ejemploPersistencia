package ar.edu.unq.epers.bichomon.backend.jdbc.service.mapa;

import ar.edu.unq.epers.bichomon.backend.jdbc.dao.BichoDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.EntrenadorDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.dao.UbicacionDao;
import ar.edu.unq.epers.bichomon.backend.jdbc.service.runner.TransactionRunner;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.historialDeCampeones.FichaDeCampeon;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.NoHayCampeonException;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDate;

public class MapaServiceImp implements MapaService {

    private EntrenadorDao entrenadorDao;
    private BichoDao bichoDao;
    private UbicacionDao ubicacionDao;


    public void setEntrenadorDao(EntrenadorDao entrenadorDao) {
        this.entrenadorDao = entrenadorDao;
    }

    public void setBichoDao(BichoDao bichoDao) {
        this.bichoDao = bichoDao;
    }

    public void setUbicacionDao(UbicacionDao ubicacionDao) {
        this.ubicacionDao = ubicacionDao;
    }

    @Override
    public void mover(String entrenador, String ubicacion) {

        Entrenador entrenadorRecuperado = this.entrenadorDao.recuperar(entrenador);
        Ubicacion ubicacionRecuperada = this.ubicacionDao.recuperar(ubicacion);
        entrenadorRecuperado.ubicarseEn(ubicacionRecuperada);

    }

    @Override
    public int cantidadDeEntrenadores(String ubicacion) {

        String ubicadosEn = ubicacion;

        Session session = TransactionRunner.getCurrentSession();
        String  hql = "select nombre from Entrenador where ubicacionActual = 'ubicadosEn' ";
        Query<Entrenador> query = session.createQuery(hql, Entrenador.class);

        return query.getResultList().size();
    }

    @Override
    public Bicho campeon(String dojo) {
        try {
            String dojou = dojo;
            Session session = TransactionRunner.getCurrentSession();
            String hql = "select nombre from Dojo where nombre = 'dojou'";
            Query<Dojo> query = session.createQuery(hql, Dojo.class);


            return query.getResultList().get(0).getBichoCampeon();

        } catch (RuntimeException e){
            throw new NoHayCampeonException();
        }
    }

    @Override
    public Bicho campeonHistorico(String dojo) {
        Bicho bichoHistorico = null;

        String dojou = dojo;
        Session session = TransactionRunner.getCurrentSession();
        String hql = "select nombre from Dojo where nombre = 'dojou'";
        Query<Dojo> query = session.createQuery(hql, Dojo.class);

        Dojo dojoRecuperado = query.getResultList().get(0);

        FichaDeCampeon fichaMasDuradera = new FichaDeCampeon(LocalDate.now(), LocalDate.now() );
        for (FichaDeCampeon ficha : dojoRecuperado.fichasDeCampeones()){
            if (ficha.duracionComoCampeon() > fichaMasDuradera.duracionComoCampeon()){
                fichaMasDuradera = ficha;
            }
        }
        return fichaMasDuradera.getBichoCampeon();
    }
}
