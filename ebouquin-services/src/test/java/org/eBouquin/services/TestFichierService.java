package org.eBouquin.services;

import org.ebouquin.service.dto.RepertoireDTO;
import org.ebouquin.services.service.FichierService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.file.Path;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-spring-context.xml"})
public class TestFichierService {

    @Autowired
    FichierService fichierService;

    @Test
    public void testFileSystem() {

        Path path = fichierService.getLibrairiePath();

        List<RepertoireDTO> listrep = fichierService.getListRepertoire(path, false);

        for (RepertoireDTO rep : listrep) {
            System.out.println(rep.getNom());
        }

    }

    @Test
    public void testSync() {

        Path path = fichierService.getLibrairiePath();

        List<String> retour = fichierService.syncroFileSystem(path, false);

        for (String res : retour) {
            System.out.println(res);
        }

    }

}
