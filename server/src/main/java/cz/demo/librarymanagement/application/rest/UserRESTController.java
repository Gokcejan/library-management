package cz.demo.librarymanagement.application.rest;

import cz.demo.librarymanagement.application.servicelayer.UserService;
import cz.demo.librarymanagement.application.utils.PaginationUtil;
import cz.demo.librarymanagement.dto.AuthResponseDto;
import cz.demo.librarymanagement.dto.UserAuthenticateDto;
import cz.demo.librarymanagement.dto.UserCreateDto;
import cz.demo.librarymanagement.dto.UserDto;
import cz.demo.librarymanagement.rest.UserRESTInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserRESTController implements UserRESTInterface {

    @Autowired
    UserService userService;

    @Autowired
    private PagedResourcesAssembler<UserDto> pagedResourcesAssembler;

    @Override
    public ResponseEntity<UserDto> createUser(@RequestBody UserCreateDto createDto) {
        UserDto response = userService.createUser(createDto);
        URI location = URI.create(String.format("/users/%d", response.getId()));

        return ResponseEntity.created(location).body(response);
    }

    @Override
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") Long userId) {
        UserDto response = userService.getUser(userId);
        return ResponseEntity.ok(response);
    }

    @Override
    public PagedModel<EntityModel<UserDto>> getUsers(@RequestParam Map<String, String> queryParams) {
        Pageable pageable = PaginationUtil.resolvePageable(queryParams);

        Page<UserDto> userDtoPage = userService.getAllUsers(pageable);

        return pagedResourcesAssembler.toModel(userDtoPage,
                userDto -> EntityModel.of(userDto,
                        linkTo(methodOn(UserRESTController.class).getUser(userDto.getId())).withSelfRel()
                )
        );
    }

    @Override
    public ResponseEntity<UserDto> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteChannel(userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<AuthResponseDto> authenticateUser(@RequestBody UserAuthenticateDto authenticateDto) {
        AuthResponseDto response = userService.authenticateUser(authenticateDto);
        return ResponseEntity.ok(response);
    }

}
