package cz.demo.librarymanagement.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "publishers")
public class PublisherDto {

    private Long id;
    private String name;

}
