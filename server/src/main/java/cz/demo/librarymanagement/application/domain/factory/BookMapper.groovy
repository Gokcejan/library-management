package cz.demo.librarymanagement.application.domain.factory

import cz.demo.librarymanagement.application.domain.Author
import cz.demo.librarymanagement.application.domain.Book
import cz.demo.librarymanagement.application.domain.Publisher
import cz.demo.librarymanagement.dto.BookCreateDto
import cz.demo.librarymanagement.dto.BookDto
import org.springframework.stereotype.Component

@Component
class BookMapper {

    Book toEntity(BookCreateDto createDto, Author author, Publisher publisher) {
        Book book = new Book()

        book.title = createDto.title
        book.relatedAuthor = author
        book.relatedPublisher = publisher
        if (createDto.status != null) {
            book.status = createDto.status
        }

        book
    }

    BookDto toDto(Book book) {
        BookDto dto = new BookDto()

        dto.id = book.id
        dto.title = book.title
        dto.authorId = book.relatedAuthor.id
        dto.publisherId = book.relatedPublisher.id
        dto.status = book.status
        dto.createdAt = book.createdAt
        dto.updatedAt = book.updatedAt

        dto
    }
}
