package cz.demo.librarymanagement.ui.views.login;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import cz.demo.librarymanagement.application.servicelayer.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "login")
@PageTitle("Login")
@CssImport("./styles/shared-styles.css")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    LoginForm login = new LoginForm();

    @Autowired
    UserService userService;

    public LoginView() {
        addClassName("login-view");
        setSizeFull();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        login.setAction("login");

        add(
                new H1("Library management"),
                login,
        new RouterLink("Register", RegisterView.class)
        );


    }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        boolean error = event.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error");
        login.setError(error);
    }

}
