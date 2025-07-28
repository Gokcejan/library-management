package cz.demo.librarymanagement.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.security.AuthenticationContext;
import cz.demo.librarymanagement.ui.views.login.LoginView;
import cz.demo.librarymanagement.ui.views.list.ListView;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {
    private final AuthenticationContext auth;

    public MainLayout(AuthenticationContext auth) {
        this.auth = auth;
        createHeader();
        createDrawer();
    }

    private void createDrawer() {

        RouterLink listLink = new RouterLink("List", ListView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink loginLink = new RouterLink("Login", LoginView.class);
        loginLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                loginLink,
                listLink

        ));
    }

    private void createHeader() {

        H1 logo = new H1("Library management");
        logo.addClassName("logo");

        Button logout = new Button("Logout", e -> auth.logout());

        HorizontalLayout header= new HorizontalLayout(new DrawerToggle(), logo, logout);
        header.addClassName("header");
        header.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }
}
