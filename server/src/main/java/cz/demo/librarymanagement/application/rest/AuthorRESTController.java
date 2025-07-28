package cz.demo.librarymanagement.application.rest;

import cz.demo.librarymanagement.application.servicelayer.AuthorService;
import cz.demo.librarymanagement.application.utils.PaginationUtil;
import cz.demo.librarymanagement.dto.AuthorCreateDto;
import cz.demo.librarymanagement.dto.AuthorDto;
import cz.demo.librarymanagement.rest.AuthorRESTInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class AuthorRESTController implements AuthorRESTInterface {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private PagedResourcesAssembler<AuthorDto> pagedResourcesAssembler;

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

    @Override
    public PagedModel<EntityModel<AuthorDto>> getAuthors(Map<String, String> queryParams) {
        Pageable pageable = PaginationUtil.resolvePageable(queryParams);

        Page<AuthorDto> authorDtoPage = authorService.getAllAuthors(pageable);

        return pagedResourcesAssembler.toModel(authorDtoPage,
                authorDto -> EntityModel.of(authorDto,
                        linkTo(methodOn(AuthorRESTController.class).getAuthor(authorDto.getId())).withSelfRel()
                )
        );
    }
}
