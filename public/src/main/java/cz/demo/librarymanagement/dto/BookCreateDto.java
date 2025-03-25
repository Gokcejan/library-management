package cz.demo.librarymanagement.dto;

import cz.demo.librarymanagement.domain.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateDto {

    private String title;
    private BookStatus status;
    private Long authorId;
    private Long publisherId;
}
