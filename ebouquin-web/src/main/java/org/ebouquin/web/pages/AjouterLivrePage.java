package org.ebouquin.web.pages;

import org.apache.log4j.Logger;
import org.apache.wicket.model.Model;
import org.ebouquin.services.model.Livre;
import org.ebouquin.web.panel.AjouterLivreFormPanel;
import org.ebouquin.web.panel.SelectionAjouterLivreFormPanel;

public class AjouterLivrePage extends eBouquinPage {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(org.ebouquin.web.pages.AjouterLivrePage.class);

    public AjouterLivrePage() {

        SelectionAjouterLivreFormPanel panel = new SelectionAjouterLivreFormPanel("panelForm");
        panel.setOutputMarkupPlaceholderTag(true);
        add(panel);

        AjouterLivreFormPanel panelLivre = new AjouterLivreFormPanel("paneLivreForm", new Model<Livre>(new Livre()));
        add(panelLivre);
        panelLivre.setVisible(false);
        panelLivre.setOutputMarkupPlaceholderTag(true);

    }
}
