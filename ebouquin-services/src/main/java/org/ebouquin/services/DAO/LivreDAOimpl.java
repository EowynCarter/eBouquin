package org.ebouquin.services.DAO;

import org.ebouquin.services.model.Livre;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: catherine Date: 07/04/11 Time: 21:28 To
 * change this template use File | Settings | File Templates.
 */

@Repository
public class LivreDAOimpl implements LivreDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    @SuppressWarnings("unchecked")
    @Override
    public void save(Livre livre) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(livre);
    }

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public List<Livre> loadLivres(int first, int nombreLivre, boolean archive) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "from Livre ";

        if (!archive) {
            queryString += " where archive = :archive ";
        }

        queryString += "order by upper(titre)";

        Query query = session.createQuery(queryString);

        if (!archive) {
            query.setBoolean("archive", archive);
        }

        query.setFirstResult(first);
        query.setMaxResults(nombreLivre);

        List<Livre> liste = query.list();
        return liste;
    }

    @Transactional
    @Override
    public boolean removeAll() {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "delete from Livre ";

        Query query = session.createQuery(queryString);
        query.executeUpdate();

        return true;


    }


    @Override
    @Transactional
    public Long compte(boolean archive) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "select count(*) from Livre where archive = :archive";

        Query query = session.createQuery(queryString);
        query.setBoolean("archive", archive);

        return (Long) query.uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional
    public List<Livre> getListRepertoire(boolean archive, String path) {
        Session session = sessionFactory.getCurrentSession();

        String queryString = "from Livre livre where archive = :archive and location = :location order by livre.titre";

        Query query = session.createQuery(queryString);
        query.setBoolean("archive", archive);
        query.setString("location", path);

        return query.list();
    }

    @Override
    @Transactional
    public void supprimer(Livre livre) {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "delete from Livre where id = " + livre.getId();
        Query query = session.createQuery(queryString);

        query.executeUpdate();

    }

    @Override
    @Transactional
    public Livre getLivre(int id) {

        Session session = sessionFactory.getCurrentSession();

        String queryString = "from Livre where id = :id";

        Query query = session.createQuery(queryString);
        query.setInteger("id", id);

        return (Livre) query.uniqueResult();

    }
}
