package cz.demo.librarymanagement.rest;

import cz.demo.librarymanagement.dto.AuthResponseDto;
import cz.demo.librarymanagement.dto.UserAuthenticateDto;
import cz.demo.librarymanagement.dto.UserCreateDto;
import cz.demo.librarymanagement.dto.UserDto;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;

import java.util.Map;

public interface UserRESTInterface {

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    ResponseEntity<UserDto> createUser(@RequestBody UserCreateDto createDto);

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    ResponseEntity<UserDto> getUser(@PathVariable("userId") Long userId);

    @RequestMapping(value = "/users/authenticate", method = RequestMethod.POST)
    ResponseEntity<AuthResponseDto> authenticateUser(@RequestBody UserAuthenticateDto authenticateDto);

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    PagedModel<EntityModel<UserDto>> getUsers(@RequestParam Map<String, String> queryParams);

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    ResponseEntity<UserDto> deleteUser(@PathVariable("userId") Long userId);


}


