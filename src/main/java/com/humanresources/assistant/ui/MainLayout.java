package com.humanresources.assistant.ui;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_SMALL;
import static com.vaadin.flow.component.icon.VaadinIcon.BOOK_PERCENT;
import static com.vaadin.flow.component.icon.VaadinIcon.CALENDAR_USER;
import static com.vaadin.flow.component.icon.VaadinIcon.CLOUD_UPLOAD;
import static com.vaadin.flow.component.icon.VaadinIcon.DIPLOMA;
import static com.vaadin.flow.component.icon.VaadinIcon.INSTITUTION;
import static com.vaadin.flow.component.icon.VaadinIcon.NEWSPAPER;
import static com.vaadin.flow.component.icon.VaadinIcon.PRESENTATION;
import static com.vaadin.flow.component.icon.VaadinIcon.SIGN_OUT;
import static com.vaadin.flow.component.icon.VaadinIcon.TOOLBOX;
import static com.vaadin.flow.component.icon.VaadinIcon.USER;
import static com.vaadin.flow.component.icon.VaadinIcon.USER_CHECK;

import com.humanresources.assistant.ui.bonuses.BonusesManagement;
import com.humanresources.assistant.ui.crudgrids.ClientCrud;
import com.humanresources.assistant.ui.crudgrids.DepartmentsCrud;
import com.humanresources.assistant.ui.crudgrids.EmployeesCrud;
import com.humanresources.assistant.ui.crudgrids.ProjectCrud;
import com.humanresources.assistant.ui.crudgrids.UsersCrud;
import com.humanresources.assistant.ui.cvs.CVs;
import com.humanresources.assistant.ui.cvs.Generator;
import com.humanresources.assistant.ui.fileuploader.FileUploader;
import com.humanresources.assistant.ui.mainpage.MainPage;
import com.humanresources.assistant.ui.requests.RequestsManagement;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
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
import com.vaadin.flow.server.VaadinSession;
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

        addToDrawer(createMenuLink(RequestsManagement.class, RequestsManagement.VIEW_NAME, NEWSPAPER));
        addToDrawer(createMenuLink(BonusesManagement.class, BonusesManagement.VIEW_NAME, BOOK_PERCENT));
        addToDrawer(createMenuLink(Generator.class, Generator.VIEW_NAME, TOOLBOX));
        addToDrawer(createMenuLink(FileUploader.class, FileUploader.VIEW_NAME, CLOUD_UPLOAD));
        addToDrawer(createMenuLink(CVs.class, CVs.VIEW_NAME, DIPLOMA));
        addToDrawer(createMenuLink(EmployeesCrud.class, EmployeesCrud.VIEW_NAME, CALENDAR_USER));
        addToDrawer(createMenuLink(UsersCrud.class, UsersCrud.VIEW_NAME, USER));
        addToDrawer(createMenuLink(DepartmentsCrud.class, DepartmentsCrud.VIEW_NAME, INSTITUTION));
        addToDrawer(createMenuLink(ClientCrud.class, ClientCrud.VIEW_NAME, USER_CHECK));
        addToDrawer(createMenuLink(ProjectCrud.class, ProjectCrud.VIEW_NAME, PRESENTATION));

        logoutButton = createMenuButton("Logout", SIGN_OUT);
        logoutButton.getElement().setAttribute("title", "Logout (Ctrl+L)");
        logoutButton.addThemeVariants(LUMO_SMALL);
        logoutButton.addClickListener(logout -> {
            VaadinSession.getCurrent().getSession().invalidate();
            UI.getCurrent().navigate("signin");
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
