package cz.demo.librarymanagement.dto;

import cz.demo.librarymanagement.domain.BorrowStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowUpdateDto {
    private BorrowStatus status;
    private LocalDateTime returnDate;
}
