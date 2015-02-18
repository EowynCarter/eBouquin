package org.ebouquin.web.panel;

import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.ebouquin.services.model.Livre;
import org.ebouquin.services.service.FichierService;
import org.ebouquin.web.event.ValiderLivreEvent;

public class AjouterLivreFormPanel extends Panel {

    private Form<CompoundPropertyModel<Livre>> form;

    @SpringBean
    FichierService fichierService;

    String pathToTmpFile;

    public AjouterLivreFormPanel(String id, Model<Livre> model) {
        super(id, model);

        form = new Form<CompoundPropertyModel<Livre>>("LivreForm") {
            protected void onSubmit() {

                Livre livre = (Livre) this.getDefaultModelObject();

                System.out.println(livre);
                System.out.println(pathToTmpFile);

                livre.setFileName(livre.getTitre() + "_" + livre.getVersion() + "." + livre.getFormat());

                try {
                    fichierService.insertLivre(livre, pathToTmpFile);
                } catch (Exception e) {
                    form.error("Une erreur s'est produite : " + e.getMessage());
                }
            }

            ;
        };
        add(form);

        TextField<String> titre = new TextField<String>("titre");
        titre.setRequired(true);

        TextField<String> version = new TextField<String>("version");
        version.setRequired(true);

        TextField<String> nomAuteur = new TextField<String>("nomAuteur");
        nomAuteur.setRequired(true);

        TextField<String> prenomAuteur = new TextField<String>("prenomAuteur");
        prenomAuteur.setRequired(true);

        TextField<String> nomAuteur2 = new TextField<String>("nomAuteur2");
        TextField<String> prenomAuteur2 = new TextField<String>("prenomAuteur2");

        TextField<String> nomAuteur3 = new TextField<String>("nomAuteur3");
        TextField<String> prenomAuteur3 = new TextField<String>("prenomAuteur3");

        TextField<String> location = new TextField<String>("location");
        location.setRequired(true);

        form.add(titre);
        form.add(version);
        form.add(nomAuteur);
        form.add(prenomAuteur);
        form.add(nomAuteur2);
        form.add(prenomAuteur2);
        form.add(nomAuteur3);
        form.add(prenomAuteur3);
        form.add(location);

        FeedbackPanel feedbackpanel = new FeedbackPanel("feedbackpanel");
        form.add(feedbackpanel);

        form.setOutputMarkupPlaceholderTag(true);

    }

    @Override
    public void onEvent(IEvent<?> event) {
        super.onEvent(event);

        if (event.getPayload() instanceof ValiderLivreEvent) {

            ValiderLivreEvent payload = (ValiderLivreEvent) event.getPayload();
            payload.getTarget().add(form);
            form.setDefaultModel(new CompoundPropertyModel<Livre>(payload.getLivre()));
            pathToTmpFile = payload.getPath();
            AjouterLivreFormPanel.this.setVisible(true);
            ((ValiderLivreEvent) event.getPayload()).getTarget().add(AjouterLivreFormPanel.this);

        }
    }
}
