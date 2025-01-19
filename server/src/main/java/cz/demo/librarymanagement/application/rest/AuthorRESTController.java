package cz.demo.librarymanagement.application.rest;

import cz.demo.librarymanagement.application.domain.Author;
import cz.demo.librarymanagement.application.domain.factory.AuthorMapper;
import cz.demo.librarymanagement.application.servicelayer.AuthorService;
import cz.demo.librarymanagement.dto.AuthorCreateDto;
import cz.demo.librarymanagement.dto.AuthorDto;
import cz.demo.librarymanagement.rest.AuthorRESTInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static cz.demo.librarymanagement.application.rest.hateoas.ControllerLinkBuilderExtensions.resourceLocation;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;

@RestController
public class AuthorRESTController implements AuthorRESTInterface {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorMapper authorMapper;


/*
    @Override
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorCreateDto createDto) {
        AuthorDto response = authorService.createAuthor(createDto);
        URI location = URI.create(String.format("/authors/%d", authorDto.getId()));
        return ResponseEntity.created(location).body(response);
    }
*/

    @Override
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorCreateDto createDto) {
        AuthorDto response = authorService.createAuthor(createDto);
        URI location = URI.create(String.format("/authors/%d", response.getId()));

        return ResponseEntity.created(location).body(response);
    }

    @Override
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("authorId") Long authorId) {
        AuthorDto response = authorService.getAuthor(authorId);
        return ResponseEntity.ok(response);
    }
}
