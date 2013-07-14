package org.ebouquin.web.pages;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.ebouquin.services.DAO.LivreDAO;
import org.ebouquin.services.service.FichierService;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SyncroDataPage extends eBouquinPage {

    private static final long serialVersionUID = 1L;

    @SpringBean
    LivreDAO livreDAOimpl;

    @SpringBean
    FichierService fichierService;

    public SyncroDataPage() {

        Form<Void> form = new org.apache.wicket.markup.html.form.Form<Void>("listForm");

        add(form);

        final WebMarkupContainer listRetourContainer = new WebMarkupContainer("listeRetourContainer");
        listRetourContainer.setOutputMarkupId(true);
        form.add(listRetourContainer);

        List<String> retour = new ArrayList<String>();
        retour.add("rien pour le moment");

        final ListView<String> listRetour = new ListView<String>("listeRetour", new ListModel<String>(retour)) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<String> item) {

                String retour = item.getModelObject();
                item.add(new Label("retour", retour));
            }

            ;
        };

        listRetourContainer.add(listRetour);

        AjaxButton fullSync = new AjaxButton("fullSync") {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

                final Path repertoire = fichierService.getLibrairiePath();
                final Path repertoireArchive = fichierService.getArchivePath();

                livreDAOimpl.removeAll();

                List<String> retours = fichierService.syncroFileSystem(repertoire, false);

                retours.addAll(fichierService.syncroFileSystem(repertoireArchive, true));

                listRetour.setModelObject(retours);
                target.add(listRetourContainer);

            }

            ;
        };
        form.add(fullSync);

    }
}
