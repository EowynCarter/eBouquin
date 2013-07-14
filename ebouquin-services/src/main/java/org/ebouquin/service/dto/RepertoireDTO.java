package org.ebouquin.service.dto;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

public class RepertoireDTO implements Serializable, Comparable<RepertoireDTO> {

    private static final long serialVersionUID = 1L;

    private String nom;

    private String relativePath;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    @Override
    public int compareTo(RepertoireDTO o) {
        return nom.toUpperCase().compareTo(o.getNom().toUpperCase());
    }
}
