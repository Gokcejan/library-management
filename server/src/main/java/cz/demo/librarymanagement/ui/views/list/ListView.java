package cz.demo.librarymanagement.ui.views.list;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import cz.demo.librarymanagement.application.domain.Author;
import cz.demo.librarymanagement.application.domain.Publisher;
import cz.demo.librarymanagement.application.domain.repository.BookRepository;
import cz.demo.librarymanagement.application.exceptions.NotFoundException;
import cz.demo.librarymanagement.application.servicelayer.*;
import cz.demo.librarymanagement.domain.BookStatus;
import cz.demo.librarymanagement.dto.*;
import cz.demo.librarymanagement.ui.MainLayout;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Route(value = "", layout = MainLayout.class)
@PageTitle("List of Books")
@RolesAllowed({"USER", "ADMIN"})
public class ListView extends VerticalLayout {

    private final BookForm form;
    Grid<BookDto> grid = new Grid<>(BookDto.class);
    TextField filterText = new TextField();
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private PublisherService publisherService;


    public ListView(BookService bookService) {
        this.bookService = bookService;
        addClassName("list-view");
        setSizeFull();

        configureGrid();

        form = new BookForm();
        form.addListener(BookForm.UpdateEvent.class, this::updateBook);
        form.addListener(BookForm.DeleteEvent.class, this::deleteBook);
        form.addListener(BookForm.CreateEvent.class, this::createBook);
        form.addListener(BookForm.BorrowEvent.class, this::borrowBook);
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

        String authorLastName = evt.getBookDto().getAuthorLastName();
        String publisherName = evt.getBookDto().getPublisherName();

        try {
            Author author = authorService.findAuthor(authorLastName);
            Publisher publisher = publisherService.findPublisher(publisherName);

            BookUpdateDto dto = new BookUpdateDto();
            dto.setTitle(evt.getBookDto().getTitle());
            dto.setStatus(evt.getBookDto().getStatus());
            dto.setAuthorId(author.getId());
            dto.setPublisherId(publisher.getId());

            bookService.updateBook(evt.getBookDto().getBookId(), dto);
            Notification.show("Book updated successfully");
            updateList();
            clearForm();
            closeEditor();

        } catch (NotFoundException ex) {
            Notification.show(ex.getMessage());
        }
    }

    private void borrowBook(BookForm.BorrowEvent event) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        UserDto user = userService.getByUsername(username);
        BookDto book = event.getBookDto();

        if (book.getStatus().equals(BookStatus.AVAILABLE)){
            BorrowCreateDto borrowCreateDto = new BorrowCreateDto(book.getBookId(), user.getId());
            borrowService.createBorrow(borrowCreateDto);

            Notification.show("Book borrowed successfully!");
        } else {
            Notification.show("Book is not possible to borrow!");
        }
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
        form.authorLastName.clear();
        form.publisherName.clear();
    }

}
