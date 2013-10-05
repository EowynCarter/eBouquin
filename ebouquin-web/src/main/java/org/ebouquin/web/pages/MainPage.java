package org.ebouquin.web.pages;

import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.ebouquin.service.dto.RepertoireDTO;
import org.ebouquin.services.service.FichierService;
import org.ebouquin.web.panel.ListLivresPanel;
import org.ebouquin.web.panel.ListLivresPanelCarrés;
import org.ebouquin.web.panel.ListeRepertoirePanel;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class MainPage extends eBouquinPage {

    @SpringBean
    FichierService fichierService;

    private static final long serialVersionUID = 1L;

    public MainPage() {

        Path librairie = fichierService.getLibrairiePath();

        List<RepertoireDTO> repertoireList = fichierService.getListRepertoire(librairie, false);

        Collections.sort(repertoireList);

        ListeRepertoirePanel panelListRepertoire = new ListeRepertoirePanel("panelListRepertoire", new ListModel<RepertoireDTO>(repertoireList));

        ListLivresPanelCarrés panelListLivre = new ListLivresPanelCarrés("panelListLivre", new Model<String>(""), false);
        add(panelListRepertoire);
        add(panelListLivre);
    }
}
