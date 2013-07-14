package org.ebouquin.web.pages;

import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.ebouquin.service.dto.RepertoireDTO;
import org.ebouquin.services.service.FichierService;
import org.ebouquin.web.panel.ListLivresPanel;
import org.ebouquin.web.panel.ListeRepertoirePanel;

import java.nio.file.Path;
import java.util.List;

public class ArchivePage extends eBouquinPage {

    @SpringBean
    FichierService fichierService;

    private static final long serialVersionUID = 1L;

    public ArchivePage() {

        Path archive = fichierService.getArchivePath();

        List<RepertoireDTO> repertoireList = fichierService.getListRepertoire(archive, true);

        ListeRepertoirePanel panelListRepertoire = new ListeRepertoirePanel("panelListRepertoire", new ListModel<RepertoireDTO>(repertoireList));

        ListLivresPanel panelListLivre = new ListLivresPanel("panelListLivre", new Model<String>(""), true);
        add(panelListRepertoire);
        add(panelListLivre);
    }
}
