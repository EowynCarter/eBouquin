package org.ebouquin.services.model;

import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "LIVRES")
public class Livre implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2810470122592629615L;

    @Id
    @SequenceGenerator(name = "livreGen", sequenceName = "LIVRE_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "livreGen")
    @Column(name = "LIV_ID")
    int id;

    @Column(name = "TITRE")
    String titre;

    @Column(name = "VERSION")
    String version;

    @Column(name = "FORMAT")
    String format;

    @Column(name = "FILE_NAME")
    String fileName;

    @Column(name = "LOCATION")
    String location;

    @Transient
    String newLocation;

    @Transient
    String newFileName;

    @Column(name = "ARCHIVE")
    boolean archive;

    @Column(name = "DRM")
    boolean drm;

    @Column(name = "ORIGINAL")
    boolean original;

    @Column(name = "NOM_AUTEUR")
    String nomAuteur;

    @Column(name = "PRENOM_AUTEUR")
    String prenomAuteur;

    @Column(name = "NOM_AUTEUR_2")
    String nomAuteur2;

    @Column(name = "PRENOM_AUTEUR_2")
    String prenomAuteur2;

    @Column(name = "NOM_AUTEUR_3")
    String nomAuteur3;

    @Column(name = "PRENOM_AUTEUR_3")
    String prenomAuteur3;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public String getNomAuteur() {
        return nomAuteur;
    }

    public void setNomAuteur(String nomAuteur) {
        this.nomAuteur = nomAuteur;
    }

    public String getPrenomAuteur() {
        return prenomAuteur;
    }

    public void setPrenomAuteur(String prenomAuteur) {
        this.prenomAuteur = prenomAuteur;
    }

    public String getNomAuteur2() {
        return nomAuteur2;
    }

    public void setNomAuteur2(String nomAuteur2) {
        this.nomAuteur2 = nomAuteur2;
    }

    public String getPrenomAuteur2() {
        return prenomAuteur2;
    }

    public void setPrenomAuteur2(String prenomAuteur2) {
        this.prenomAuteur2 = prenomAuteur2;
    }

    public String getNomAuteur3() {
        return nomAuteur3;
    }

    public void setNomAuteur3(String nomAuteur3) {
        this.nomAuteur3 = nomAuteur3;
    }

    public String getPrenomAuteur3() {
        return prenomAuteur3;
    }

    public void setPrenomAuteur3(String prenomAuteur3) {
        this.prenomAuteur3 = prenomAuteur3;
    }

    public boolean isOriginal() {
        return original;
    }

    public void setOriginal(boolean original) {
        this.original = original;
    }

    public boolean isDrm() {
        return drm;
    }

    public void setDrm(boolean drm) {
        this.drm = drm;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    @Transient
    public String getAuteur() {
        if (StringUtils.isNotBlank(nomAuteur) && StringUtils.isNotBlank(prenomAuteur)) {
            return nomAuteur + "," + prenomAuteur;
        } else

        {
            return "";
        }
    }

    @Transient
    public String getAuteur2() {
        if (StringUtils.isNotBlank(nomAuteur2) && StringUtils.isNotBlank(prenomAuteur2)) {
            return nomAuteur2 + "," + prenomAuteur2;
        } else

        {
            return "";
        }
    }

    @Transient
    public String getAuteur3() {
        if (StringUtils.isNotBlank(nomAuteur3) && StringUtils.isNotBlank(prenomAuteur3)) {
            return nomAuteur3 + "," + prenomAuteur3;
        } else

        {
            return "";
        }
    }

    public String getNewLocation() {
        return newLocation;
    }

    public void setNewLocation(String newLocation) {
        this.newLocation = newLocation;
    }

    @Override
    public String toString() {

        String retour = "";
        retour += titre + " ";

        retour += " par ";
        retour += nomAuteur + " " + prenomAuteur;

        retour += " version : " + version;
        retour += " location : " + location;

        return retour;
    }

}
