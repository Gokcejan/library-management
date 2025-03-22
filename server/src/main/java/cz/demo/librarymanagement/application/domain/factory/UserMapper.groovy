package cz.demo.librarymanagement.application.domain.factory

import cz.demo.librarymanagement.application.domain.User
import cz.demo.librarymanagement.dto.UserCreateDto
import cz.demo.librarymanagement.dto.UserDto
import org.springframework.stereotype.Component

@Component
class UserMapper {




    UserDto toDto(User user) {
        UserDto dto = new UserDto()

        dto.id = user.id
        dto.firstName = user.firstName
        dto.lastName = user.lastName
        dto.username = user.username
        dto.email = user.email
        dto.phone = user.phone
        dto.role = user.role

        dto
    }


    def User toEntity(UserCreateDto createDto) {
        User user = new User()

        user.firstName = createDto.firstName
        user.lastName = createDto.lastName
        user.username = createDto.username
        user.email = createDto.email
        user.phone = createDto.phone
        user.role = createDto.role

        user
    }
}
