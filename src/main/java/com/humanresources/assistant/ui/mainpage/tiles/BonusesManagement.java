package com.humanresources.assistant.ui.mainpage.tiles;

import static com.humanresources.assistant.backend.enums.ui.TileDetails.BACKGROUND;
import static com.humanresources.assistant.backend.enums.ui.TileDetails.DESCRIPTION;
import static com.humanresources.assistant.backend.enums.ui.TileDetails.HREF;
import static com.humanresources.assistant.backend.enums.ui.TileDetails.TITLE;

import com.humanresources.assistant.backend.enums.ui.TileDetails;
import com.vaadin.flow.component.html.Div;
import java.util.HashMap;
import java.util.Map;

public final class BonusesManagement extends Div implements ITileGenerics {


    public BonusesManagement() {
        add(getInitializedCard());
    }

    @Override
    public Map<TileDetails, String> getTileDetails() {
        return new HashMap<TileDetails, String>() {{
            put(TITLE, "Bonuses Management");
            put(DESCRIPTION, "Your employees will need sometimes to be remunerated with bonuses for their job, manage them "
                + "using our product.");
            put(BACKGROUND, "card_back_cv");
            put(HREF, "/bonuses_management");
        }};
    }
}
