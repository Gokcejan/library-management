package cz.demo.librarymanagement.rest;

import cz.demo.librarymanagement.dto.BookCreateDto;
import cz.demo.librarymanagement.dto.BookDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public interface BookRESTInterface {

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    ResponseEntity<BookDto> createBook(@RequestBody BookCreateDto createDto);

    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.GET)
    ResponseEntity<BookDto> getBook(@PathVariable("bookId") Long bookId);

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    PagedModel<EntityModel<BookDto>> getBooks(@RequestParam Map<String, String> queryParams);

    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteBook(@PathVariable("bookId") Long bookId);
}
