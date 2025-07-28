package cz.demo.librarymanagement.dto;

import cz.demo.librarymanagement.domain.BorrowStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Getter
@Setter
@Relation(collectionRelation = "borrows")
public class BorrowDto {

    private Long id;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private LocalDateTime dueDate;
    private BorrowStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long bookId;
    private Long userId;
}
