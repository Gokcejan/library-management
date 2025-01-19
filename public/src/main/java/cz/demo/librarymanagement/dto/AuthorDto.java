package cz.demo.librarymanagement.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;
@Getter
@Setter
@Relation(collectionRelation = "authors")
public class AuthorDto {

    private Long id;
    private String firstName;
    private String lastName;
    private List<Long> books;
}
