package org.ebouquin.services.service;

import org.ebouquin.services.model.Livre;

import java.nio.file.Path;

public class FichierServiceReturn {

    private Livre livre;

    private Path fichier;

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public Path getFichier() {
        return fichier;
    }

    public void setFichier(Path fichier) {
        this.fichier = fichier;
    }

}
