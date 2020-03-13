package com.humanresources.assistant.ui;

import com.humanresources.assistant.ui.cvs.CVs;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * The main layout. Contains the navigation menu.
 */
@Theme(value = Lumo.class,
       variant = Lumo.DARK)
@PWA(name = "Human Resource Production",
     shortName = "HR Assistant")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/menu-buttons.css", themeFor = "vaadin-button")
@Route("")
public class MainLayout extends AppLayout implements RouterLayout {

    private final Button logoutButton;

    public MainLayout() {

        final DrawerToggle drawerToggle = new DrawerToggle();
        drawerToggle.addClassName("menu-toggle");
        addToNavbar(drawerToggle);

        // Navigation items
//        addToDrawer(createMenuLink(InventoryView.class, InventoryView.VIEW_NAME, VaadinIcon.EDIT.create()));
//
//        addToDrawer(createMenuLink(AboutView.class, AboutView.VIEW_NAME, VaadinIcon.INFO_CIRCLE.create()));
//
        addToDrawer(createMenuLink(CVs.class, CVs.VIEW_NAME, VaadinIcon.DIPLOMA.create()));

        logoutButton = createMenuButton("Logout", VaadinIcon.SIGN_OUT.create());
        logoutButton.getElement().setAttribute("title", "Logout (Ctrl+L)");
        logoutButton.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
    }

    private RouterLink createMenuLink(Class<? extends Component> viewClass,
                                      String caption, Icon icon) {
        final RouterLink routerLink = new RouterLink(null, viewClass);
        routerLink.setClassName("menu-link");
        routerLink.add(icon);
        routerLink.add(new Span(caption));
        icon.setSize("24px");
        return routerLink;
    }

    private Button createMenuButton(String caption, Icon icon) {
        final Button routerButton = new Button(caption);
        routerButton.setClassName("menu-button");
        routerButton.addThemeVariants(ButtonVariant.LUMO_LARGE);
        routerButton.setIcon(icon);
        icon.setSize("24px");
        return routerButton;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        // Finally, add logout button for all users
        addToDrawer(logoutButton);
    }

}
