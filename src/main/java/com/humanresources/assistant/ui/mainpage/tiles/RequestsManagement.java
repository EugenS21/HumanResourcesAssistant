package com.humanresources.assistant.ui.mainpage.tiles;

import static com.humanresources.assistant.backend.enums.ui.TileDetails.BACKGROUND;
import static com.humanresources.assistant.backend.enums.ui.TileDetails.DESCRIPTION;
import static com.humanresources.assistant.backend.enums.ui.TileDetails.HREF;
import static com.humanresources.assistant.backend.enums.ui.TileDetails.TITLE;

import com.humanresources.assistant.backend.enums.ui.TileDetails;
import com.vaadin.flow.component.html.Div;
import java.util.HashMap;
import java.util.Map;

public final class RequestsManagement extends Div implements ITileGenerics {


    public RequestsManagement() {
        add(getInitializedCard());
    }

    @Override
    public Map<TileDetails, String> getTileDetails() {
        return new HashMap<TileDetails, String>() {{
            put(TITLE, "Requests Management");
            put(DESCRIPTION, "Manage employees request like vacation or unexpected leaves easily using our adaptive and "
                + "modern engine. ");
            put(BACKGROUND, "card_back_rqs");
            put(HREF, "/requests_management");
        }};
    }
}
