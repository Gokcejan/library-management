package cz.demo.librarymanagement.application.domain.factory

import cz.demo.librarymanagement.application.domain.Author
import cz.demo.librarymanagement.dto.AuthorCreateDto
import cz.demo.librarymanagement.dto.AuthorDto
import org.springframework.stereotype.Component

@Component
class AuthorMapper {


    AuthorDto toDto(Author author) {
        AuthorDto dto = new AuthorDto()

        dto.id = author.id
        dto.firstName = author.firstName
        dto.lastName = author.lastName
        dto.books = author.books.sort { it.id }.collect { it.id }

        dto
    }

    Author toEntity(AuthorCreateDto createDto) {
        Author author = new Author()

        author.firstName = createDto.firstName
        author.lastName = createDto.lastName

        author
    }
}
