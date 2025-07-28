package cz.demo.librarymanagement.dto;

import cz.demo.librarymanagement.domain.BookStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookUpdateDto {

    private Long bookId;
    private String title;
    private BookStatus status;
    private Long authorId;
    private Long publisherId;
}
