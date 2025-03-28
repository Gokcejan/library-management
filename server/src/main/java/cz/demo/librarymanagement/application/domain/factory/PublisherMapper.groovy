package cz.demo.librarymanagement.application.domain.factory

import cz.demo.librarymanagement.application.domain.Publisher
import cz.demo.librarymanagement.dto.PublisherCreateDto
import cz.demo.librarymanagement.dto.PublisherDto
import org.springframework.stereotype.Component

@Component
class PublisherMapper {

    PublisherDto toDto(Publisher publisher) {
        PublisherDto dto = new PublisherDto()

        dto.id = publisher.id
        dto.name = publisher.name

        dto
    }

    Publisher toEntity(PublisherCreateDto createDto) {
        Publisher publisher = new Publisher()

        publisher.name = createDto.name

        publisher
    }

}
