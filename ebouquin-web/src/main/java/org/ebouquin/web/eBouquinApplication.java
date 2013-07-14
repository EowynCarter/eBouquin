package org.ebouquin.web;

import org.apache.log4j.Logger;
import org.apache.wicket.Page;
import org.apache.wicket.core.request.mapper.MountedMapper;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.ebouquin.web.pages.AjouterLivrePage;
import org.ebouquin.web.pages.ArchivePage;
import org.ebouquin.web.pages.MainPage;
import org.ebouquin.web.pages.SyncroDataPage;

/**
 * Created by IntelliJ IDEA. User: catherine Date: 11/09/11 Time: 16:58 To
 * change this template use File | Settings | File Templates.
 */

public class eBouquinApplication extends WebApplication {
    /**
     * Logging
     */
    private static Logger log = Logger.getLogger(eBouquinApplication.class);

    public void init() {

        super.init();
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        mountPage("/syncro", SyncroDataPage.class);
        mountPage("/home", MainPage.class);
        mountPage("/ajouterLivre", AjouterLivrePage.class);
        mountPage("/archive", ArchivePage.class);


    }

    public Class<? extends Page> getHomePage() {
        return MainPage.class;
    }

    void mountPage(Class<IRequestablePage> classe) {
        mount(new MountedMapper("/mount/path", classe));
    }

}
