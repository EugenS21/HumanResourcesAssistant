package com.humanresources.assistant.ui.mainpage.tiles;

import static com.humanresources.assistant.backend.enums.ui.TileDetails.BACKGROUND;
import static com.humanresources.assistant.backend.enums.ui.TileDetails.DESCRIPTION;
import static com.humanresources.assistant.backend.enums.ui.TileDetails.HREF;
import static com.humanresources.assistant.backend.enums.ui.TileDetails.TITLE;

import com.humanresources.assistant.backend.enums.ui.TileDetails;
import com.vaadin.flow.component.html.Div;
import java.util.HashMap;
import java.util.Map;

public final class CVsManagement extends Div implements ITileGenerics {


    public CVsManagement() {
        add(getInitializedCard());
    }

    @Override
    public Map<TileDetails, String> getTileDetails() {
        return new HashMap<TileDetails, String>() {{
            put(TITLE, "CVs Management");
            put(DESCRIPTION, "Did you have a lot of CVs but no open vacations? Store them and use later, using our "
                + "fast and easy to use tool.");
            put(BACKGROUND, "card_back_cv");
            put(HREF, "/cvs_list");
        }};
    }
}
