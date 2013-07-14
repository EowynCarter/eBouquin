package org.ebouquin.web.pages;

import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.ebouquin.services.DAO.LivreDAO;
import org.ebouquin.services.model.Livre;
import org.ebouquin.web.panel.EditLivreFormPanel;

public class ModifierLivrePage extends eBouquinPage {


    @SpringBean
    private LivreDAO livreDAOimpl;

    public ModifierLivrePage(int id) {

        Livre livre = livreDAOimpl.getLivre(id);

        EditLivreFormPanel editLivreFormPanel = new EditLivreFormPanel("FormPanel", new Model<Livre>(livre));
        add(editLivreFormPanel);

    }

}
