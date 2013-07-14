package org.ebouquin.web.pages;

import org.apache.wicket.markup.html.WebPage;
import org.ebouquin.web.eBouquinSession;

/**
 * Created by IntelliJ IDEA. User: catherine Date: 11/09/11 Time: 17:06 To
 * change this template use File | Settings | File Templates.
 */
public abstract class eBouquinPage extends WebPage {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Get downcast session object for easy access by subclasses
     *
     * @return The session
     */
    public eBouquinSession geteBouquinSession() {
        return (eBouquinSession) getSession();
    }
}
