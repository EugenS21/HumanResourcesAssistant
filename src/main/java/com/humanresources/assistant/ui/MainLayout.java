package com.humanresources.assistant.ui;

import static com.humanresources.assistant.backend.authentication.AccessControlFactory.SINGLETONE;
import static com.vaadin.flow.component.icon.VaadinIcon.BOOK_PERCENT;
import static com.vaadin.flow.component.icon.VaadinIcon.CLOUD_UPLOAD;
import static com.vaadin.flow.component.icon.VaadinIcon.DIPLOMA;
import static com.vaadin.flow.component.icon.VaadinIcon.SIGN_OUT;
import static com.vaadin.flow.component.icon.VaadinIcon.TOOLBOX;
import static com.vaadin.flow.component.icon.VaadinIcon.USER_CHECK;

import com.humanresources.assistant.backend.authentication.AccessData;
import com.humanresources.assistant.ui.bonuses.BonusesManagement;
import com.humanresources.assistant.ui.cvs.CVs;
import com.humanresources.assistant.ui.cvs.Generator;
import com.humanresources.assistant.ui.employees.EmployeesCrud;
import com.humanresources.assistant.ui.fileuploader.FileUploader;
import com.humanresources.assistant.ui.mainpage.MainPage;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.JsModule;
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
@Theme(value = Lumo.class, variant = Lumo.DARK)
@PWA(name = "Human Resource Production", shortName = "HR Assistant", iconPath = "src/main/resources/icons/icon.png")
@JsModule("./styles/shared-styles.js")
@Route("")
public class MainLayout extends AppLayout implements RouterLayout {

    private final MainPage mainPage;
    private Button logoutButton;

    public MainLayout() {

        addMenuItems();

        mainPage = new MainPage();

        setContent(mainPage);

    }

    private void addMenuItems() {
        final DrawerToggle drawerToggle = new DrawerToggle();
        drawerToggle.addClassName("menu-toggle");

        addToDrawer(createMenuLink(BonusesManagement.class, BonusesManagement.VIEW_NAME, BOOK_PERCENT));
        addToDrawer(createMenuLink(Generator.class, Generator.VIEW_NAME, TOOLBOX));
        addToDrawer(createMenuLink(FileUploader.class, FileUploader.VIEW_NAME, CLOUD_UPLOAD));
        addToDrawer(createMenuLink(CVs.class, CVs.VIEW_NAME, DIPLOMA));
        addToDrawer(createMenuLink(EmployeesCrud.class, EmployeesCrud.VIEW_NAME, USER_CHECK));

        logoutButton = createMenuButton("Logout", SIGN_OUT);
        logoutButton.getElement().setAttribute("title", "Logout (Ctrl+L)");
        logoutButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        logoutButton.addClickListener(logout -> {
            AccessData accessData = SINGLETONE.createAccessData();
            accessData.signOut();
        });
    }

    private RouterLink createMenuLink(Class<? extends Component> viewClass, String caption, VaadinIcon vaadinIcon) {
        final RouterLink routerLink = new RouterLink(null, viewClass);
        routerLink.setClassName("menu-link");
        final Icon icon = vaadinIcon.create();
        routerLink.add(icon);
        routerLink.add(new Span(caption));
        return routerLink;
    }

    private Button createMenuButton(String caption, VaadinIcon vaadinIcon) {
        final Button routerButton = new Button(caption);
        routerButton.setClassName("menu-button");
        routerButton.addThemeVariants(ButtonVariant.LUMO_LARGE);
        final Icon icon = vaadinIcon.create();
        routerButton.setIcon(icon);
        return routerButton;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        addToDrawer(logoutButton);
    }

}
