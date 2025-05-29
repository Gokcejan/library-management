package cz.demo.librarymanagement.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import cz.demo.librarymanagement.application.domain.Book;
import cz.demo.librarymanagement.application.domain.repository.BookRepository;
import cz.demo.librarymanagement.application.exceptions.NotFoundException;
import cz.demo.librarymanagement.application.servicelayer.BookService;
import cz.demo.librarymanagement.dto.BookCreateDto;
import cz.demo.librarymanagement.dto.BookDto;
import cz.demo.librarymanagement.dto.BookUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Route("")
@CssImport("./styles/shared-styles.css")
public class MainView extends VerticalLayout {

    private final BookForm form;
    Grid<BookDto> grid = new Grid<>(BookDto.class);
    TextField filterText = new TextField();
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    public MainView(BookService bookService) {
        this.bookService = bookService;
        addClassName("list-view");
        setSizeFull();

        configureGrid();


        form = new BookForm();
        form.addListener(BookForm.UpdateEvent.class, this::updateBook);
        form.addListener(BookForm.DeleteEvent.class, this::deleteBook);
        form.addListener(BookForm.CreateEvent.class, this::createBook);
        form.addListener(BookForm.CloseEvent.class, event -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();
        closeEditor();
    }

    private void createBook(BookForm.CreateEvent evt) {

        BookCreateDto dto = new BookCreateDto();
        dto.setTitle(evt.getBookDto().getTitle());
        dto.setStatus(evt.getBookDto().getStatus());
        dto.setAuthorId(evt.getBookDto().getAuthorId());
        dto.setPublisherId(evt.getBookDto().getPublisherId());

        bookService.createBook(dto);

        Notification.show("Book created successfully");
        updateList();
        clearForm();
        closeEditor();
    }

    private void deleteBook(BookForm.DeleteEvent evt) {

        bookService.deleteBook(evt.getBookDto().getBookId());
        Notification.show("Book deleted successfully");
        updateList();
        clearForm();
        closeEditor();
    }

    private void updateBook(BookForm.UpdateEvent evt) {

        BookUpdateDto dto = new BookUpdateDto();
        dto.setTitle(evt.getBookDto().getTitle());
        dto.setStatus(evt.getBookDto().getStatus());
        dto.setAuthorId(evt.getBookDto().getAuthorId());
        dto.setPublisherId(evt.getBookDto().getPublisherId());

        bookService.updateBook(evt.getBookDto().getBookId(), dto);
        Notification.show("Book updated successfully");
        updateList();
        clearForm();
        closeEditor();

    }

    private void configureGrid() {
        grid.removeAllColumns();
        grid.addClassName("book-grid");
        grid.setSizeFull();
        grid.addColumn(BookDto::getBookId)
                .setHeader("Book ID")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(BookDto::getAuthorLastName)
                .setHeader("Author")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(BookDto::getAuthorId)
                .setHeader("Author ID")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(BookDto::getTitle)
                .setHeader("Title")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(BookDto::getStatus)
                .setHeader("Status")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(BookDto::getPublisherName)
                .setHeader("Publisher")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(BookDto::getPublisherId)
                .setHeader("Publisher ID")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(BookDto::getCreatedAt)
                .setHeader("Created")
                .setSortable(true)
                .setAutoWidth(true);
        grid.addColumn(BookDto::getUpdatedAt)
                .setHeader("Updated")
                .setSortable(true)
                .setAutoWidth(true);

        grid.asSingleSelect().addValueChangeListener(evt -> editBook(evt.getValue()));
    }

    private void editBook(BookDto dto) {
        if (dto == null) {
            closeEditor();
        } else {
            form.setBook(dto);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private Book findBook(Long bookId) {
        Optional<Book> bookOptional = bookRepository.findOneById(bookId);
        return bookOptional.orElseThrow(() -> new NotFoundException(format("The Book [%s] not found.", bookId)));
    }

    private void closeEditor() {
        form.setBook(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter by Author");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addBookButton = new Button("Add book", click -> addBook());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addBookButton);
        toolbar.addClassName("toolbar");

        return toolbar;

    }

    private void addBook() {
        grid.asSingleSelect().clear();
        editBook(new BookDto());
    }

    private void updateList() {
        String filterValue = filterText.getValue();

        Page<BookDto> pageOfBooks = bookService.getAllBooks(filterValue, PageRequest.of(0, 50));
        List<BookDto> books = pageOfBooks.getContent();
        grid.setItems(books);

    }

    private void clearForm() {
        form.bookId.clear();
        form.title.clear();
        form.status.clear();
        form.authorId.clear();
        form.publisherId.clear();
    }

}
