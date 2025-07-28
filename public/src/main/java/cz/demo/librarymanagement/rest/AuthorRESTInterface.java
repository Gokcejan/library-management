package cz.demo.librarymanagement.rest;

import cz.demo.librarymanagement.dto.AuthorCreateDto;
import cz.demo.librarymanagement.dto.AuthorDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public interface AuthorRESTInterface {

    @RequestMapping(value = "/authors", method = RequestMethod.POST)
    ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorCreateDto createDto);

    @RequestMapping(value = "/authors/{authorId}", method = RequestMethod.GET)
    ResponseEntity<AuthorDto> getAuthor(@PathVariable("authorId") Long authorId);

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    PagedModel<EntityModel<AuthorDto>> getAuthors(@RequestParam Map<String, String> queryParams);
}
