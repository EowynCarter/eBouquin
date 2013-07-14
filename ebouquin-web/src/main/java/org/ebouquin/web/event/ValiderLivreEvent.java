package org.ebouquin.web.event;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.ebouquin.services.model.Livre;

public class ValiderLivreEvent {

    AjaxRequestTarget target;

    Livre livre;

    String path;

    public ValiderLivreEvent(AjaxRequestTarget target, Livre livre, String path) {
        this.target = target;
        this.livre = livre;
        this.path = path;
    }

    public AjaxRequestTarget getTarget() {
        return target;
    }

    public void setTarget(AjaxRequestTarget target) {
        this.target = target;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
