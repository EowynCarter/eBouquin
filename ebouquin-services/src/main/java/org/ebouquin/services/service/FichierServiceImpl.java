package org.ebouquin.services.service;

import eBouquin.ePubLib.ePubEdit.LectureService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.ebouquin.ebouquinFormatPlugin.FormatPluginReturn;
import org.ebouquin.ebouquinFormatPlugin.LivreFichierService;
import org.ebouquin.ebouquinFormatPlugin.LivreMetadata;
import org.ebouquin.service.dto.RepertoireDTO;
import org.ebouquin.services.DAO.LivreDAO;
import org.ebouquin.services.model.Livre;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FichierServiceImpl implements InitializingBean, FichierService {

    @Value("${ebouquin.archive.directory}")
    private String archiveDirectory;

    @Value("${ebouquin.librairie.directory}")
    private String librairieDirectory;

    @Value("${ebouquin.original.directory}")
    private String originalDirectory;


    @Autowired
    LivreServiceFactory livreServiceFactory;

    @Autowired
    private LivreDAO livreDAO;

    private Path archivePath;

    private Path originalPath;

    private Path librairiePath;

    Logger logger = Logger.getLogger(FichierServiceImpl.class);

    public void afterPropertiesSet() throws Exception {

        librairiePath = Paths.get(librairieDirectory, "");
        archivePath = Paths.get(archiveDirectory, "");
        originalPath = Paths.get(originalDirectory, "");
    }

    @Override
    public List<RepertoireDTO> getListRepertoire(Path path, boolean archive) {

        DirectoryStream<Path> stream;
        List<RepertoireDTO> listeRepertoire = new ArrayList<RepertoireDTO>();

        try {
            stream = Files.newDirectoryStream(path);
            for (Path path2 : stream) {

                System.out.println(path2);
                if (Files.isDirectory(path2)) {

                    RepertoireDTO repertoire = new RepertoireDTO();
                    repertoire.setNom(path2.getFileName().toString());
                    Path relativePath = null;

                    if (archive) {
                        relativePath = archivePath.relativize(path2);
                    } else {
                        relativePath = librairiePath.relativize(path2);
                    }

                    repertoire.setRelativePath(relativePath.toString());
                    listeRepertoire.add(repertoire);
                }
            }

        } catch (IOException e) {
            // TODO Gérer les exceptions
            e.printStackTrace();
        }

        Collections.sort(listeRepertoire);
        return listeRepertoire;
    }

    @Override
    public boolean archiverLivre(Livre livre) {

        Path origine = librairiePath.resolve(livre.getLocation() + "/" + livre.getFileName());

        Path destination = archivePath.resolve(livre.getLocation() + "/" + livre.getFileName());


        try {
            Files.createDirectories(destination.getParent());
            Files.move(origine, destination);
        } catch (IOException e) {
            e.printStackTrace();
            throw (new EbouquinException("erreur : " + e.getMessage()));
        }

        livre.setArchive(true);
        livreDAO.save(livre);

        return true;

    }

    ;

    @Override
    public boolean dearchiverLivre(Livre livre) {


        Path origine = archivePath.resolve(livre.getLocation() + "/" + livre.getFileName());
        Path destination = librairiePath.resolve(livre.getLocation() + "/" + livre.getFileName());

        try {
            Files.createDirectories(destination.getParent());
            Files.move(origine, destination);
        } catch (IOException e) {
            e.printStackTrace();
            throw (new EbouquinException("erreur : " + e.getMessage()));
        }

        livre.setArchive(false);
        livreDAO.save(livre);

        return true;

    }

    @Override
    public boolean deplacerLivre(Livre livre) {


        String libDirectory;

        if (livre.isArchive()) {
            libDirectory = archiveDirectory;
        } else {
            libDirectory = librairieDirectory;
        }

        Path destination = Paths.get(libDirectory + "/" + livre.getNewLocation() + "/" + livre.getNewFileName());
        Path origine = Paths.get(libDirectory + "/" + livre.getLocation() + "/" + livre.getFileName());

        try {
            Files.createDirectories(destination.getParent());
            Files.move(origine, destination);
        } catch (IOException e) {
            e.printStackTrace();
            throw (new EbouquinException("erreur : " + e.getMessage()));
        }

        livre.setLocation(livre.getNewLocation());
        livre.setFileName(livre.getNewFileName());
        livreDAO.save(livre);

        return true;

    }


    @Override
    public boolean copieOriginal(Path origine, String titre) {


        Path destination = originalPath.resolve(titre);


        if (Files.exists(destination)) {
            throw (new EbouquinException("erreur : le fichier eiste déja"));
        }

        try {
            Files.createDirectories(destination.getParent());
            Files.copy(origine, destination);
        } catch (IOException e) {
            e.printStackTrace();
            throw (new EbouquinException("erreur : " + e.getMessage()));
        }

        return true;

    }

    @Override
    public boolean insertLivre(Livre livre, String tmpFile) {

        Path tmpPath = Paths.get(tmpFile, "");

        Path finalFile = librairiePath.resolve(livre.getLocation() + "/" + livre.getFileName());

        if (Files.exists(finalFile)) {
            throw (new EbouquinException("le fichier existe déja"));
        }

        livreDAO.save(livre);
        try {

            CopyOption[] copyOption = new CopyOption[0];

            Files.createDirectories(finalFile.getParent());

            Files.copy(tmpPath, finalFile, copyOption);
        } catch (IOException e) {
            logger.error(e);
            throw (new EbouquinException("erreur : " + e.getMessage()));
        }

        return true;
    }

    @Override
    public boolean updateLivre(Livre livre) {

        Path libPath;

        if (livre.isArchive()) {
            libPath = archivePath;
        } else {
            libPath = librairiePath;
        }


        livreDAO.save(livre);

        LivreFichierService lectureService = livreServiceFactory.getService(livre.getFormat());
        lectureService.updateMetadata(LivresMapper.mapLivreToMetadata(livre), libPath.resolve(livre.getLocation() + "/" + livre.getFileName()));

        return true;
    }


    public List<String> syncroFileSystem(Path repertoire, boolean archive) {

        List<String> retours = new ArrayList<String>();
        DirectoryStream<Path> stream;

        try {

            stream = Files.newDirectoryStream(repertoire);
            for (Path path2 : stream) {

                if (Files.isDirectory(path2)) {

                    logger.debug("Repertoire : " + path2.getFileSystem().toString());
                    retours.addAll(syncroFileSystem(path2, archive));
                } else {
                    String chemmin = path2.toString();
                    try {

                        String extention = chemmin.substring(StringUtils.lastIndexOf(chemmin, ".") + 1, chemmin.length());
                        LivreFichierService lectureService = livreServiceFactory.getService(extention);
                        LivreMetadata metadata = lectureService.liremetadata(path2);

                        Livre livre = new Livre();
                        LivresMapper.MetadataToLivre(metadata, livre);

                        String fileName = path2.getFileName().toString();
                        livre.setFileName(fileName);

                        Path relativePath = null;

                        if (archive) {
                            relativePath = archivePath.relativize(path2);
                            livre.setArchive(true);
                        } else {
                            relativePath = librairiePath.relativize(path2);

                        }
                        livre.setLocation(relativePath.subpath(0, relativePath.getNameCount() - 1).toString());

                        String version = "";

                        try {
                            version = fileName.substring(fileName.lastIndexOf("_") + 1, fileName.lastIndexOf("."));
                        } catch (Exception ex) {
                            logger.error("impossible de récupérer la version");
                        }

                        livre.setVersion(version);

                        logger.debug(livre.getTitre());
                        retours.add("Fichier ajouté : " + livre.toString());
                        livreDAO.save(livre);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        retours.add("Erreur lors de la lecture du  fichier " + chemmin + " : " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            retours.add("Erreur lors de la lecture des fichier : " + e.getMessage());
        }

        return retours;
    }


    public FichierServiceReturn beforeInsert(Path fichier, boolean original, boolean drm) {

        FichierServiceReturn retour = new FichierServiceReturn();

        String chemmin = fichier.toString();
        String extention = chemmin.substring(StringUtils.lastIndexOf(chemmin, ".") + 1, chemmin.length());
        LivreFichierService lectureService = livreServiceFactory.getService(extention);

        FormatPluginReturn livreRetour = lectureService.beforeInsert(fichier, drm);

        Livre livre = new Livre();
        LivresMapper.MetadataToLivre(livreRetour.getLivre(), livre);

        retour.setFichier(livreRetour.getFichier());
        retour.setLivre(livre);

        return retour;
    }

    public Path getFileSystemPourLivre(boolean archive) {

        if (archive) {
            return archivePath;
        } else {
            return librairiePath;
        }
    }

    public Path getArchivePath() {
        return archivePath;
    }

    public void setArchivePath(Path archivePath) {
        this.archivePath = archivePath;
    }

    public Path getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(Path originalPath) {
        this.originalPath = originalPath;
    }

    public Path getLibrairiePath() {
        return librairiePath;
    }

    public void setLibrairiePath(Path librairiePath) {
        this.librairiePath = librairiePath;
    }

    public String getArchiveDirectory() {
        return archiveDirectory;
    }

    public void setArchiveDirectory(String archiveDirectory) {
        this.archiveDirectory = archiveDirectory;
    }

    public String getLibrairieDirectory() {
        return librairieDirectory;
    }

    public void setLibrairieDirectory(String librairieDirectory) {
        this.librairieDirectory = librairieDirectory;
    }

    public String getOriginalDirectory() {
        return originalDirectory;
    }

    public void setOriginalDirectory(String originalDirectory) {
        this.originalDirectory = originalDirectory;
    }

}
