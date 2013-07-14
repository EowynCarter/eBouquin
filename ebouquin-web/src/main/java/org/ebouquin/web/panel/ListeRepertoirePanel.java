package org.ebouquin.web.panel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.ebouquin.service.dto.RepertoireDTO;
import org.hibernate.mapping.Collection;

import java.util.Collections;

public class ListeRepertoirePanel extends Panel {

    private static final long serialVersionUID = 1L;

    public ListeRepertoirePanel(String id, ListModel<RepertoireDTO> listeRepertoire) {

        super(id, listeRepertoire);

        final ListView<RepertoireDTO> listeRepertoireView = new ListView<RepertoireDTO>("repertoireListe", listeRepertoire) {

            private static final long serialVersionUID = 1L;


            protected void populateItem(ListItem<RepertoireDTO> item) {
                final RepertoireDTO repertoire = (RepertoireDTO) item.getModelObject();

                AjaxLink<String> lien = new AjaxLink<String>("nomRepertoire") {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                        send(getPage(), Broadcast.BREADTH, new RepertoireSelectEvent(ajaxRequestTarget, repertoire));
                    }

                };
                lien.setBody(new Model<String>(repertoire.getNom()));
                item.add(lien);

            }
        };

        final WebMarkupContainer repertoireContainer = new WebMarkupContainer("repertoireContainer") {

            private static final long serialVersionUID = 1L;

            public void onEvent(org.apache.wicket.event.IEvent<?> event) {
                // --TODO sous répertoires !
            }

            ;
        };

        repertoireContainer.setOutputMarkupId(true);

        add(repertoireContainer);
        repertoireContainer.add(listeRepertoireView);

    }

    public class RepertoireSelectEvent {

        private final AjaxRequestTarget target;

        private final RepertoireDTO repertoire;

        public RepertoireSelectEvent(AjaxRequestTarget target, RepertoireDTO repertoire) {
            this.target = target;
            this.repertoire = repertoire;
        }

        public AjaxRequestTarget getTarget() {
            return target;
        }

        public RepertoireDTO getRepertoire() {
            return repertoire;
        }

    }

}
