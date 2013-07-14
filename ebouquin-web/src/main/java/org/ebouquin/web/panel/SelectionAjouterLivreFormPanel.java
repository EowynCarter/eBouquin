package org.ebouquin.web.panel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.ebouquin.services.service.FichierService;
import org.ebouquin.services.service.FichierServiceReturn;
import org.ebouquin.web.event.ValiderLivreEvent;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class SelectionAjouterLivreFormPanel extends Panel {

    private static final long serialVersionUID = 1L;

    @SpringBean
    FichierService fichierService;

    public SelectionAjouterLivreFormPanel(String id) {
        super(id);

        Form<Void> form = new Form<Void>("ajoutLivreForm");
        add(form);

        final FileUploadField fichier = new FileUploadField("fichier");
        form.add(fichier);

        final CheckBox drmField = new CheckBox("drm", new Model<Boolean>(false));
        form.add(drmField);

        final CheckBox originalField = new CheckBox("original", new Model<Boolean>(false));
        form.add(originalField);

        AjaxButton validFichierBouton = new AjaxButton("validerFichier") {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

                System.out.println("submit");

                final List<FileUpload> uploads = fichier.getFileUploads();
                if (uploads != null) {
                    for (FileUpload upload : uploads) {

                        Path tempFile;
                        try {
                            tempFile = Files.createTempFile("temp_ebouquin_", upload.getClientFileName());
                            upload.writeTo(tempFile.toFile());

                            boolean drm = drmField.getConvertedInput();
                            boolean original = originalField.getConvertedInput();

                            FichierServiceReturn livreRetour = fichierService.beforeInsert(tempFile, original, drm);

                            if (original) {
                                //TODO extantion
                                fichierService.copieOriginal(livreRetour.getFichier(), livreRetour.getLivre().getTitre() + ".epub");
                            }

                            send(this.getPage(), Broadcast.BREADTH, new ValiderLivreEvent(target, livreRetour.getLivre(), livreRetour.getFichier().toString()));
                            SelectionAjouterLivreFormPanel.this.setVisible(false);
                            target.add(SelectionAjouterLivreFormPanel.this);

                        } catch (Exception e) {
                            throw new IllegalStateException("Unable to write file", e);
                        }
                    }
                }
            }

            ;

        };

        form.add(validFichierBouton);

    }
}