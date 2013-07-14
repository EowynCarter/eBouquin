package org.ebouquin.web;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

/**
 * Created by IntelliJ IDEA. User: catherine Date: 11/09/11 Time: 17:05 To
 * change this template use File | Settings | File Templates.
 */
public class eBouquinSession extends WebSession {

    private static final long serialVersionUID = 1L;

    public eBouquinSession(Request request) {
        super(request);
    }
}
