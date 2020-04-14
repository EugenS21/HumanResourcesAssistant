package com.humanresources.assistant.ui.mainpage.tiles;

import static com.humanresources.assistant.backend.enums.ui.TileDetails.BACKGROUND;
import static com.humanresources.assistant.backend.enums.ui.TileDetails.HREF;

import com.humanresources.assistant.backend.enums.ui.TileDetails;
import com.vaadin.flow.component.html.Div;
import java.util.HashMap;
import java.util.Map;

public final class DocumentsManagement extends Div implements ITileGenerics {


    public DocumentsManagement() {
        add(getInitializedCard());
    }

    @Override
    public Map<TileDetails, String> getTileDetails() {
        return new HashMap<TileDetails, String>() {{
            put(TileDetails.TITLE, "Documents Management");
            put(TileDetails.DESCRIPTION, "Using our product you'll be able to add your documents without need of "
                + "physical presence.");
            put(BACKGROUND, "card_back_docs");
            put(HREF, "/files_uploader");
        }};
    }
}
