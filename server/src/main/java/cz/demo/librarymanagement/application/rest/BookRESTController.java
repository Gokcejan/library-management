package cz.demo.librarymanagement.application.rest;

import cz.demo.librarymanagement.application.servicelayer.BookService;
import cz.demo.librarymanagement.application.utils.PaginationUtil;
import cz.demo.librarymanagement.dto.BookCreateDto;
import cz.demo.librarymanagement.dto.BookDto;
import cz.demo.librarymanagement.dto.BookUpdateDto;
import cz.demo.librarymanagement.rest.BookRESTInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class BookRESTController implements BookRESTInterface {

    @Autowired
    private PagedResourcesAssembler<BookDto> pagedResourcesAssembler;

    @Autowired
    BookService bookService;

    @Override
    public ResponseEntity<BookDto> createBook(@RequestBody BookCreateDto createDto) {
        BookDto response = bookService.createBook(createDto);
        URI location = URI.create(String.format("/books/%d", response.getBookId()));

        return ResponseEntity.created(location).body(response);
    }

    @Override
    public ResponseEntity<BookDto> getBook(@PathVariable Long bookId) {
        BookDto response = bookService.getBook(bookId);
        return ResponseEntity.ok(response);
    }

    @Override
    public PagedModel<EntityModel<BookDto>> getBooks(@RequestParam Map<String, String> queryParams) {
        Pageable pageable = PaginationUtil.resolvePageable(queryParams);

        Page<BookDto> bookDtoPage = bookService.getAllBooks(null, pageable);

        return pagedResourcesAssembler.toModel(bookDtoPage,
                bookDto -> EntityModel.of(bookDto,
                        linkTo(methodOn(BookRESTController.class).getBook(bookDto.getBookId())).withSelfRel()
                )
        );
    }

    @Override
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<BookDto> updateBook(@PathVariable("bookId") Long bookId, @RequestBody BookUpdateDto updateDto) {
        BookDto response = bookService.updateBook(bookId, updateDto);
        return ResponseEntity.ok(response);
    }
}
