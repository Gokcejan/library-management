package cz.demo.librarymanagement.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import cz.demo.librarymanagement.domain.BookStatus;
import cz.demo.librarymanagement.dto.BookDto;
import lombok.Getter;

public class BookForm extends FormLayout {

    TextField bookId = new TextField("Book ID");
    TextField title = new TextField("Title");
    ComboBox<BookStatus> status = new ComboBox<>("Status");
    TextField authorId = new TextField("Author ID");
    TextField publisherId = new TextField("Publisher ID");

    Button update = new Button("Update");
    Button create = new Button("Create");
    Button delete = new Button("Delete");
    Button close = new Button("Close");

    Binder<BookDto> binder = new BeanValidationBinder<>(BookDto.class);


    public BookForm() {
        addClassName("book-form");

        setResponsiveSteps(
                new ResponsiveStep("0", 1)
        );

        binder.bindInstanceFields(this);
        status.setItems(BookStatus.values());

        add(
                bookId,
                title,
                status,
                authorId,
                publisherId,
                createButtonsLayout()
        );

    }

    public void setBook(BookDto bookDto) {
        binder.setBean(bookDto);

        update.setEnabled(binder.isValid());     // <--- CHANGED: synchronizace stavu tlačítka
        create.setEnabled(binder.isValid());
    }

    private Component createButtonsLayout() {
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        create.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        update.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        update.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        create.addClickListener(click -> validateAndCreate());

        binder.addStatusChangeListener(evt -> {
            boolean valid = binder.isValid();
            update.setEnabled(valid);                          // <--- CHANGED
            create.setEnabled(valid);                          // <--- NEW
        });

        HorizontalLayout buttonsLayout = new HorizontalLayout(update, create, delete, close);
        buttonsLayout.addClassName("buttons");

        return buttonsLayout;
    }

    private void validateAndSave() {
        if (binder.isValid()){
            fireEvent(new UpdateEvent(this, binder.getBean()));
        }
    }

    private void validateAndCreate() {                     // <--- NEW
        if (binder.isValid()) {
            fireEvent(new CreateEvent(this, binder.getBean()));
        }
    }

    // Events
    @Getter
    public static abstract class BookFormEvent extends ComponentEvent<BookForm> {
        private BookDto bookDto;

        protected BookFormEvent(BookForm source, BookDto bookDto) {
            super(source, false);
            this.bookDto = bookDto;
        }

    }

    public static class UpdateEvent extends BookFormEvent {
        UpdateEvent(BookForm source, BookDto bookDto) {
            super(source, bookDto);
        }
    }

    public static class CreateEvent extends BookFormEvent {  // <--- NEW
        CreateEvent(BookForm source, BookDto bookDto) {
            super(source, bookDto);
        }
    }

    public static class DeleteEvent extends BookFormEvent {
        DeleteEvent(BookForm source, BookDto bookDto) {
            super(source, bookDto);
        }

    }

    public static class CloseEvent extends BookFormEvent {
        CloseEvent(BookForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
