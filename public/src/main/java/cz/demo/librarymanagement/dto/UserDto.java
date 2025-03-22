package cz.demo.librarymanagement.dto;

import cz.demo.librarymanagement.domain.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "users")
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phone;
    private Role role;
}
