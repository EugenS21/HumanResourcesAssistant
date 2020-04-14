package com.humanresources.assistant.ui.mainpage.tiles;

import static com.humanresources.assistant.backend.enums.ui.TileDetails.BACKGROUND;
import static com.humanresources.assistant.backend.enums.ui.TileDetails.HREF;

import com.humanresources.assistant.backend.enums.ui.TileDetails;
import com.vaadin.flow.component.html.Div;
import java.util.HashMap;
import java.util.Map;

public final class EmployeeManagement extends Div implements ITileGenerics {


    public EmployeeManagement() {
        add(getInitializedCard());
    }

    @Override
    public Map<TileDetails, String> getTileDetails() {
        return new HashMap<TileDetails, String>() {{
            put(TileDetails.TITLE, "Staff Management");
            put(TileDetails.DESCRIPTION, "This is a powerful module for the management of your existing and new "
                + "coming employees. ");
            put(BACKGROUND, "card_back_emp");
            put(HREF, "/employees");
        }};
    }
}
