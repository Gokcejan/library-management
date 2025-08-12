package cz.demo.librarymanagement.application.domain.factory

import cz.demo.librarymanagement.application.domain.Book
import cz.demo.librarymanagement.application.domain.Borrow
import cz.demo.librarymanagement.application.domain.User
import cz.demo.librarymanagement.dto.BorrowDto
import org.springframework.stereotype.Component

import java.time.LocalDateTime

@Component
class BorrowMapper {


    BorrowDto toDto(Borrow borrow) {
        BorrowDto dto = new BorrowDto()

        dto.id = borrow.id
        dto.borrowDate = borrow.borrowDate
        dto.returnDate = borrow.returnDate
        dto.dueDate = borrow.dueDate
        dto.status = borrow.status
        dto.bookId = borrow.borrowedBook.id
        dto.userId = borrow.borrowedBy.id
        dto.createdAt = borrow.createdAt
        dto.updatedAt = borrow.updatedAt

        dto
    }

    Borrow toEntity(Book relatedBook, User relatedUser) {
        Borrow borrow = new Borrow()

        borrow.borrowDate = LocalDateTime.now()
        borrow.dueDate = LocalDateTime.now().plusDays(30)
        borrow.borrowedBook = relatedBook
        borrow.borrowedBy = relatedUser
        relatedUser.borrows.add(borrow)
        relatedBook.borrows.add(borrow)

        borrow
    }
}
