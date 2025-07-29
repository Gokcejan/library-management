package cz.demo.librarymanagement.ui.views.login;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import cz.demo.librarymanagement.application.servicelayer.UserService;
import cz.demo.librarymanagement.domain.Role;
import cz.demo.librarymanagement.dto.UserCreateDto;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "register")
@PageTitle("Register")
@CssImport("./styles/shared-styles.css")
@AnonymousAllowed
public class RegisterView extends Composite<VerticalLayout> {

    @Autowired
    UserService userService;


    public RegisterView(UserService userService) {
        this.userService = userService;

        TextField firstName = new TextField("First name");
        TextField lastName = new TextField("Last name");
        TextField userName = new TextField("Username");
        EmailField email = new EmailField("E‑mail");
        PasswordField pwd1 = new PasswordField("Password");
        PasswordField pwd2 = new PasswordField("Confirm password");
        TextField phoneNumber = new TextField("Phone number");

        FormLayout form = new FormLayout();
        form.addClassName("register-form");
        form.add(firstName, lastName, userName, email, pwd1, pwd2, phoneNumber);

        form.setColspan(userName,2);
        form.setColspan(email,2);
        form.setColspan(phoneNumber,2);

        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0",1),
                new FormLayout.ResponsiveStep("480px",2)
        );

        Button send = new Button("Register", e ->
                register(firstName.getValue(), lastName.getValue(),
                        userName.getValue(), pwd1.getValue(), pwd2.getValue(),
                        email.getValue(), phoneNumber.getValue())
        );

        RouterLink link = new RouterLink("Log in", LoginView.class);

        VerticalLayout root = getContent();
        root.addClassName("register-view");
        root.setWidthFull();
        root.setAlignItems(FlexComponent.Alignment.CENTER);
        root.add(new H2("Register"), form, send, link);
    }

    private void register(String firstName, String lastName, String userName, String password1, String password2,
                          String email, String phoneNumber) {

        UserCreateDto dto = new UserCreateDto(firstName,lastName, userName, password1, email, phoneNumber, Role.USER);

        if (userName.trim().isEmpty()){
            Notification.show("Enter a username");
        } else if (password1.isEmpty() || password2.isEmpty()) {
            Notification.show("Enter a password");
        } else if (!password1.equals(password2)){
            Notification.show("Passwords don't match");
        } else {
            userService.createUser(dto);
            Notification.show("Registration succeed");
        }

    }

}
