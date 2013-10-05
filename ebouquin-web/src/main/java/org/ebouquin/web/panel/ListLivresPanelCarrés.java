package org.ebouquin.web.panel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.ebouquin.service.dto.RepertoireDTO;
import org.ebouquin.services.DAO.LivreDAO;
import org.ebouquin.services.model.Livre;
import org.ebouquin.web.pages.ModifierLivrePage;
import org.ebouquin.web.panel.ListeRepertoirePanel.RepertoireSelectEvent;

import java.util.List;

public class ListLivresPanelCarrés extends Panel {

    private static final long serialVersionUID = 1L;

    // --TODO a remplacer par un service !
    @SpringBean
    private LivreDAO livreDAOimpl;

    public ListLivresPanelCarrés(String id, Model<String> pathModel, final boolean archive) {

        super(id, pathModel);

        final Label nomRepertoire = new Label("nomRepertoire", pathModel);
        nomRepertoire.setOutputMarkupId(true);
        add(nomRepertoire);

        List<Livre> listeLivre = livreDAOimpl.getListRepertoire(archive, pathModel.getObject());

        final PropertyListView<Livre> listeLivreView = new PropertyListView<Livre>("listLivre", listeLivre) {

            private static final long serialVersionUID = 1L;

            protected void populateItem(ListItem<Livre> item) {

                Label titre = new Label("titre");
                item.add(titre);

                Label auteur = new Label("auteur");
                item.add(auteur);

                Label version = new Label("version");
                item.add(version);

                final int id = item.getModelObject().getId();

                AjaxLink<String> editLink = new AjaxLink<String>("lienEdit") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        setResponsePage(new ModifierLivrePage(id));
                    }
                };
                item.add(editLink);

            }

        };

        listeLivreView.setOutputMarkupId(true);

        final WebMarkupContainer livresContainer = new WebMarkupContainer("livrecontainer") {

            private static final long serialVersionUID = 1L;

            public void onEvent(org.apache.wicket.event.IEvent<?> event) {

                if (event.getPayload() instanceof RepertoireSelectEvent) {

                    RepertoireSelectEvent repEvent = (RepertoireSelectEvent) event.getPayload();

                    RepertoireDTO repertoire = repEvent.getRepertoire();

                    List<Livre> listeLivre = livreDAOimpl.getListRepertoire(archive, repertoire.getRelativePath());
                    listeLivreView.setModelObject(listeLivre);
                    nomRepertoire.setDefaultModel(new Model<String>(repertoire.getRelativePath()));
                    repEvent.getTarget().add(nomRepertoire);
                    repEvent.getTarget().add(this);
                }
            }

            ;
        };

        livresContainer.setOutputMarkupId(true);

        add(livresContainer);
        livresContainer.add(listeLivreView);

    }

}
