package cz.demo.librarymanagement.dto;

import cz.demo.librarymanagement.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Role role;
}
