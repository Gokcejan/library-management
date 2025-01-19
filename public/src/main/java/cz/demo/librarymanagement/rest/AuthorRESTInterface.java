package cz.demo.librarymanagement.rest;

import cz.demo.librarymanagement.dto.AuthorCreateDto;
import cz.demo.librarymanagement.dto.AuthorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface AuthorRESTInterface {

    @RequestMapping(value = "/authors", method = RequestMethod.POST)
    ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorCreateDto createDto);

    @RequestMapping(value = "/authors/{authorId}", method = RequestMethod.GET)
    ResponseEntity<AuthorDto> getAuthor(@PathVariable("authorId") Long authorId);
}
