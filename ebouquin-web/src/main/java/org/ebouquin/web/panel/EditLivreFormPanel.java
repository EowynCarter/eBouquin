package org.ebouquin.web.panel;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.ebouquin.services.model.Livre;
import org.ebouquin.services.service.FichierService;

public class EditLivreFormPanel extends Panel {

    private Form<CompoundPropertyModel<Livre>> form;

    @SpringBean
    FichierService fichierService;

    public EditLivreFormPanel(String id, Model<Livre> model) {

        super(id, model);
        Livre livre = model.getObject();

        livre.setNewFileName(livre.getFileName());
        livre.setNewLocation(livre.getLocation());
        CompoundPropertyModel livreCPM = new CompoundPropertyModel<Livre>(livre);

        form = new Form<CompoundPropertyModel<Livre>>("LivreForm", livreCPM);
        add(form);

        TextField<String> titre = new TextField<String>("titre");
        titre.setRequired(true);
        Label version = new Label("version");

        TextField<String> nomAuteur = new TextField<String>("nomAuteur");
        TextField<String> prenomAuteur = new TextField<String>("prenomAuteur");
        nomAuteur.setRequired(true);
        prenomAuteur.setRequired(true);

        TextField<String> nomAuteur2 = new TextField<String>("nomAuteur2");
        TextField<String> prenomAuteur2 = new TextField<String>("prenomAuteur2");

        TextField<String> nomAuteur3 = new TextField<String>("nomAuteur3");
        TextField<String> prenomAuteur3 = new TextField<String>("prenomAuteur3");

        form.add(titre);
        form.add(version);
        form.add(nomAuteur);
        form.add(prenomAuteur);
        form.add(nomAuteur2);
        form.add(prenomAuteur2);
        form.add(nomAuteur3);
        form.add(prenomAuteur3);

        final FeedbackPanel feedbackpanel = new FeedbackPanel("feedbackpanel");
        feedbackpanel.setOutputMarkupId(true);
        form.add(feedbackpanel);

        TextField<String> newLocation = new TextField<String>("newLocation");
        form.add(newLocation);

        TextField<String> newFileName = new TextField<String>("newFileName");
        form.add(newFileName);


        AjaxButton deplacer = new AjaxButton("deplacer") {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

                Livre livre = (Livre) form.getDefaultModelObject();

                try {
                    if (StringUtils.isNotBlank(livre.getNewLocation())) {
                        fichierService.deplacerLivre(livre);
                        feedbackpanel.info("Sauvgarde OK");
                        target.add(feedbackpanel);
                    } else {
                        form.error("La nouvelle location est obligatoire");
                        target.add(feedbackpanel);
                    }

                } catch (Exception e) {
                    form.error("Une erreur s'est produite : " + e.getMessage());
                    target.add(feedbackpanel);
                }


            }
        };

        form.add(deplacer);

        AjaxButton soumettre = new AjaxButton("soumettre") {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

                Livre livre = (Livre) form.getDefaultModelObject();


                try {
                    fichierService.updateLivre(livre);

                    feedbackpanel.info("Sauvgarde OK");
                    target.add(feedbackpanel);

                } catch (Exception e) {
                    form.error("Une erreur s'est produite : " + e.getMessage());
                    target.add(feedbackpanel);
                }


            }
        };
        form.add(soumettre);

        AjaxButton archiver = new AjaxButton("archiver") {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

                Livre livre = (Livre) form.getDefaultModelObject();

                try {
                    fichierService.archiverLivre(livre);
                    AjaxButton dearchiver = (AjaxButton) form.get("dearchiver");
                    dearchiver.setVisible(true);
                    this.setVisible(false);
                    target.add(dearchiver);
                    target.add(this);
                } catch (Exception e) {
                    form.error("Une erreur s'est produite : " + e.getMessage());

                    target.add(feedbackpanel);
                }

            }
        };

        archiver.setVisible(!livre.isArchive());
        archiver.setOutputMarkupPlaceholderTag(true);
        form.add(archiver);
        AjaxButton dearchiver = new AjaxButton("dearchiver") {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

                Livre livre = (Livre) form.getDefaultModelObject();

                try {
                    fichierService.dearchiverLivre(livre);
                    AjaxButton archiver = (AjaxButton) form.get("archiver");
                    archiver.setVisible(true);
                    this.setVisible(false);
                    target.add(archiver);
                    target.add(this);
                } catch (Exception e) {
                    form.error("Une erreur s'est produite : " + e.getMessage());
                }

            }
        };

        dearchiver.setVisible(livre.isArchive());
        dearchiver.setOutputMarkupPlaceholderTag(true);
        form.add(dearchiver);


    }
}
