package com.humanresources.assistant.ui.userprofile;

import static com.humanresources.assistant.ui.userprofile.ProfileSettings.VIEW_NAME;

import com.humanresources.assistant.ui.MainLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route (value = "profile_details", layout = MainLayout.class)
@PageTitle (VIEW_NAME)
public class ProfileSettings extends HorizontalLayout {

    public static final String VIEW_NAME = "Profile Details";

    private final UserPictureLayout userImageLayout;
    private final UserDetailsLayout userDetailsLayout;

    public ProfileSettings() {
        userImageLayout = new UserPictureLayout();
        userDetailsLayout = new UserDetailsLayout();
        add(userImageLayout, userDetailsLayout);
    }


}
