package cz.demo.librarymanagement.ui;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import cz.demo.librarymanagement.application.servicelayer.BookService;
import cz.demo.librarymanagement.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Route("")
@CssImport("./styles/shared-styles.css")
public class MainView extends VerticalLayout {

    private final BookForm form;
    Grid<BookDto> grid = new Grid<>(BookDto.class);
    TextField filterText = new TextField();
    private BookService bookService;

    public MainView(BookService bookService) {
        this.bookService = bookService;
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureFilter();

        form= new BookForm();

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(filterText, content);
        updateList();
    }

    private void configureGrid() {
        grid.removeAllColumns();
        grid.addClassName("book-grid");
        grid.setSizeFull();
        grid.addColumn(BookDto::getId)
                .setHeader("ID")
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
    }

    private void configureFilter() {
        filterText.setPlaceholder("Filter by Author");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
    }

    private void updateList() {
        String filterValue = filterText.getValue();

        Page<BookDto> pageOfBooks = bookService.getAllBooks(filterValue, PageRequest.of(0, 30));
        List<BookDto> books = pageOfBooks.getContent();
        grid.setItems(books);

    }

}
