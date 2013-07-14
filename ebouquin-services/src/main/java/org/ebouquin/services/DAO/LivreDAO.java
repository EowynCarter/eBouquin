package org.ebouquin.services.DAO;

import org.ebouquin.services.model.Livre;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface LivreDAO {

    public void save(Livre livre);

    List<Livre> loadLivres(int first, int nombreLivre, boolean archive);

    public Long compte(boolean archive);

    public List<Livre> getListRepertoire(boolean archive, String path);

    @Transactional
    public void supprimer(Livre livre);

    @Transactional
    public Livre getLivre(int id);

    @Transactional
    boolean removeAll();
}
