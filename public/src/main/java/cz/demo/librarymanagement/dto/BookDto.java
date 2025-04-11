package cz.demo.librarymanagement.dto;

import cz.demo.librarymanagement.domain.BookStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Getter
@Setter
@Relation(collectionRelation = "books")
public class BookDto {

    private Long id;
    private String title;
    private BookStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long authorId;
    private Long publisherId;
    private String authorLastName;
    private String publisherName;
}
