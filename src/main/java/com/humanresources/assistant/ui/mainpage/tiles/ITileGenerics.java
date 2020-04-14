package com.humanresources.assistant.ui.mainpage.tiles;

import static com.humanresources.assistant.backend.enums.ui.TileDetails.BACKGROUND;
import static com.humanresources.assistant.backend.enums.ui.TileDetails.DESCRIPTION;
import static com.humanresources.assistant.backend.enums.ui.TileDetails.HREF;
import static com.humanresources.assistant.backend.enums.ui.TileDetails.TITLE;

import com.humanresources.assistant.backend.enums.ui.TileDetails;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import java.util.Map;

@CssImport(value = "styles/views/cardlist/custom-card.css")
public interface ITileGenerics {

    Map<TileDetails, String> getTileDetails();

    default Div getInitializedCard() {

        Div mainTile;
        Div tileBody;
        Div linkerTo;
        Anchor anchor;
        H2 tileTitle;
        Paragraph tileDescription;
        final Map<TileDetails, String> tileDetails = getTileDetails();

        mainTile = new Div();
        linkerTo = new Div();
        anchor = new Anchor();

        tileTitle = new H2(tileDetails.get(TITLE));
        tileTitle.addClassName("transition");

        tileDescription = new Paragraph(tileDetails.get(DESCRIPTION));
        tileDescription.addClassName("description");

        anchor.addClassName("cta");
        anchor.setText("Try it");
        anchor.setHref(tileDetails.get(HREF));
        linkerTo.addClassNames("cta-container", "transition");
        linkerTo.add(anchor);

        tileBody = new Div();
        tileBody.addClassNames("cta-container", "transition");

        Div circleTransition = new Div();
        circleTransition.addClassNames("card_circle", tileDetails.get(BACKGROUND), "transition");

        mainTile.addClassNames("custom-card", "transition");
        mainTile.add(tileTitle, tileDescription, tileBody, linkerTo, circleTransition);

        return mainTile;
    }
}
