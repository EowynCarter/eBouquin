package org.ebouquin.services.service;

import org.apache.commons.lang.StringUtils;
import org.ebouquin.ebouquinFormatPlugin.AuteurMetadata;
import org.ebouquin.ebouquinFormatPlugin.LivreMetadata;
import org.ebouquin.services.model.Livre;

import java.util.ArrayList;

public class LivresMapper {

    static LivreMetadata mapLivreToMetadata(Livre livre) {
        LivreMetadata metadata = new LivreMetadata();

        metadata.setFormat(livre.getFormat());
        metadata.setTitre(livre.getTitre());

        metadata.setAuteurs(new ArrayList<AuteurMetadata>());
        AuteurMetadata auteurMetadata;

        if (StringUtils.isNotBlank(livre.getAuteur())) {
            auteurMetadata = new AuteurMetadata();
            auteurMetadata.setNom(livre.getNomAuteur());
            auteurMetadata.setPrenom(livre.getPrenomAuteur());
            auteurMetadata.setAuthorSort(livre.getAuteur());
            metadata.getAuteurs().add(auteurMetadata);
        }

        if (StringUtils.isNotBlank(livre.getAuteur2())) {
            auteurMetadata = new AuteurMetadata();
            auteurMetadata.setNom(livre.getNomAuteur2());
            auteurMetadata.setPrenom(livre.getPrenomAuteur2());
            auteurMetadata.setAuthorSort(livre.getAuteur2());
            metadata.getAuteurs().add(auteurMetadata);
        }

        if (StringUtils.isNotBlank(livre.getAuteur3())) {
            auteurMetadata = new AuteurMetadata();
            auteurMetadata.setNom(livre.getNomAuteur3());
            auteurMetadata.setPrenom(livre.getPrenomAuteur3());
            auteurMetadata.setAuthorSort(livre.getAuteur3());
            metadata.getAuteurs().add(auteurMetadata);
        }

        return metadata;
    }

    static void MetadataToLivre(LivreMetadata livreMetadata, Livre livre) {

        livre.setTitre(livreMetadata.getTitre());
        livre.setVersion(livreMetadata.getVersion());
        livre.setFormat(livreMetadata.getFormat());

        AuteurMetadata auteur;

        if (livreMetadata.getAuteurs().size() >= 1) {
            auteur = livreMetadata.getAuteurs().get(0);
            livre.setNomAuteur(auteur.getNom());
            livre.setPrenomAuteur(auteur.getPrenom());
        }

        if (livreMetadata.getAuteurs().size() >= 2) {
            auteur = livreMetadata.getAuteurs().get(1);
            livre.setNomAuteur2(auteur.getNom());
            livre.setPrenomAuteur2(auteur.getPrenom());
        }

        if (livreMetadata.getAuteurs().size() >= 3) {
            auteur = livreMetadata.getAuteurs().get(2);
            livre.setNomAuteur3(auteur.getNom());
            livre.setPrenomAuteur3(auteur.getPrenom());
        }

    }

}
