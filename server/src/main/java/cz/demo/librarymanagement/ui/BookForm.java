package cz.demo.librarymanagement.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import cz.demo.librarymanagement.domain.BookStatus;

public class BookForm extends FormLayout {

    TextField title = new TextField("Title");
    ComboBox<BookStatus> status = new ComboBox<>("Status");
    TextField authorId = new TextField("Author ID");
    TextField publisherId = new TextField("Publisher ID");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Close");


    public BookForm() {
        addClassName("book-form");

        add(
                title,
                status,
                authorId,
                publisherId,
                createButtonsLayout()
        );

    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, close);
    }
}
