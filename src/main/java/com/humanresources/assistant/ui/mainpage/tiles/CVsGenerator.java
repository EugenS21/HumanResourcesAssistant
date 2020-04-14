package com.humanresources.assistant.ui.mainpage.tiles;

import static com.humanresources.assistant.backend.enums.ui.TileDetails.BACKGROUND;
import static com.humanresources.assistant.backend.enums.ui.TileDetails.DESCRIPTION;
import static com.humanresources.assistant.backend.enums.ui.TileDetails.HREF;
import static com.humanresources.assistant.backend.enums.ui.TileDetails.TITLE;

import com.humanresources.assistant.backend.enums.ui.TileDetails;
import com.vaadin.flow.component.html.Div;
import java.util.HashMap;
import java.util.Map;

public final class CVsGenerator extends Div implements ITileGenerics {


    public CVsGenerator() {
        add(getInitializedCard());
    }

    @Override
    public Map<TileDetails, String> getTileDetails() {
        return new HashMap<TileDetails, String>() {{
            put(TITLE, "Job Generator");
            put(DESCRIPTION, "Are you tired of writing job description documents? Leave it to us our "
                + "sophisticated algorithms will handle this for you.");
            put(BACKGROUND, "card_back_cv");
            put(HREF, "/cvs_generator");
        }};
    }
}
