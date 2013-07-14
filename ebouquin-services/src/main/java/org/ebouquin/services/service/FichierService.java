package org.ebouquin.services.service;

import org.ebouquin.service.dto.RepertoireDTO;
import org.ebouquin.services.model.Livre;

import java.nio.file.Path;
import java.util.List;

public interface FichierService {

    public Path getLibrairiePath();

    public Path getOriginalPath();

    public Path getArchivePath();

    public Path getFileSystemPourLivre(boolean archive);

    public List<RepertoireDTO> getListRepertoire(Path path, boolean archive);

    public List<String> syncroFileSystem(Path repertoire, boolean archive);

    FichierServiceReturn beforeInsert(Path fichier, boolean original, boolean drm);

    public boolean insertLivre(Livre livre, String tmpFile);

    public boolean updateLivre(Livre livre);

    boolean archiverLivre(Livre livre);

    boolean dearchiverLivre(Livre livre);

    boolean copieOriginal(Path original, String titre);

    boolean deplacerLivre(Livre livre);
}
