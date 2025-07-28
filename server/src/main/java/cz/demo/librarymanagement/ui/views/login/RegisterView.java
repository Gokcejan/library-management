package cz.demo.librarymanagement.ui.views.login;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import cz.demo.librarymanagement.application.servicelayer.UserService;
import cz.demo.librarymanagement.domain.Role;
import cz.demo.librarymanagement.dto.UserCreateDto;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "register")
@PageTitle("Register")
@CssImport("./styles/shared-styles.css")
@AnonymousAllowed
public class RegisterView extends Composite {

    @Autowired
    UserService userService;

    @Override
    protected Component initContent(){
        TextField firstName = new TextField("First name");
        TextField lastName = new TextField("Last name");
        TextField userName = new TextField("Username");
        PasswordField password1 = new PasswordField("Password");
        PasswordField password2 = new PasswordField("Confirm password");
        EmailField email = new EmailField("email");
        TextField phoneNumber = new TextField("Phone number");


        return new VerticalLayout(
                new H2("Register"),
                firstName,
                lastName,
                userName,
                password1,
                password2,
                email,
                phoneNumber,
                new Button("Send", event -> register(
                        firstName.getValue(),
                        lastName.getValue(),
                        userName.getValue(),
                        password1.getValue(),
                        password2.getValue(),
                        email.getValue(),
                        phoneNumber.getValue()
                ))


        );
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
