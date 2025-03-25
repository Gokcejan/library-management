package cz.demo.librarymanagement.application.rest;

import cz.demo.librarymanagement.application.servicelayer.UserService;
import cz.demo.librarymanagement.application.utils.PaginationUtil;
import cz.demo.librarymanagement.dto.*;
import cz.demo.librarymanagement.rest.UserRESTInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserDto> createUser(UserCreateDto createDto) {
        UserDto response = userService.createUser(createDto);
        URI location = URI.create(String.format("/users/%d", response.getId()));

        return ResponseEntity.created(location).body(response);
    }

    @Override
    public ResponseEntity<UserDto> getUser(Long userId) {
        UserDto response = userService.getUser(userId);
        return ResponseEntity.ok(response);
    }

    @Override
    public PagedModel<EntityModel<UserDto>> getUsers(Map<String, String> queryParams) {
        Pageable pageable = PaginationUtil.resolvePageable(queryParams);

        Page<UserDto> userDtoPage = userService.getAllUsers(pageable);

        return pagedResourcesAssembler.toModel(userDtoPage,
                userDto -> EntityModel.of(userDto,
                        linkTo(methodOn(UserRESTController.class).getUser(userDto.getId())).withSelfRel()
                )
        );
    }

    @Override
    public ResponseEntity<UserDto> deleteUser(Long userId) {
        userService.deleteChannel(userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<AuthResponseDto> authenticateUser(UserAuthenticateDto authenticateDto) {
        AuthResponseDto response = userService.authenticateUser(authenticateDto);
        return ResponseEntity.ok(response);
    }

}
