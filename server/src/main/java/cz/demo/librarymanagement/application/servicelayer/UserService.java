package cz.demo.librarymanagement.application.servicelayer;

import com.vaadin.flow.component.notification.Notification;
import cz.demo.librarymanagement.application.domain.User;
import cz.demo.librarymanagement.application.domain.factory.UserMapper;
import cz.demo.librarymanagement.application.domain.repository.UserRepository;
import cz.demo.librarymanagement.application.exceptions.NotFoundException;
import cz.demo.librarymanagement.dto.AuthResponseDto;
import cz.demo.librarymanagement.dto.UserAuthenticateDto;
import cz.demo.librarymanagement.dto.UserCreateDto;
import cz.demo.librarymanagement.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserDto createUser(UserCreateDto createDto) {

        Optional<User> userOptional = userRepository.findOneByUsername(createDto.getUsername());

        if (userOptional.isPresent()) {
            Notification.show("Username already in use");
            throw new IllegalStateException("Username already in use");

        }

        User user = userMapper.toEntity(createDto);
        user.setPassword(passwordEncoder.encode(createDto.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public UserDto getUser(Long userId) {
        User user = findUser(userId);
        return userMapper.toDto(user);
    }

    public AuthResponseDto authenticateUser(UserAuthenticateDto authenticateDto) {
        Optional<User> userOptional = userRepository.findOneByUsername(authenticateDto.getUsername());

        if (userOptional.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(authenticateDto.getPassword(), user.getPassword())) {
            throw new IllegalStateException("Invalid password");
        }

        return new AuthResponseDto("Login successful", user.getId());
    }

    public Page<UserDto> getAllUsers(Pageable pageable) {
        Page<User> authorsPage = userRepository.findAll(pageable);
        return authorsPage.map(userMapper::toDto);
    }

    public void deleteChannel(Long userId) {
        User user = findUser(userId);

        if (user == null) {
            throw new IllegalArgumentException("User not found: " + userId);
        }
        userRepository.delete(user);
    }

    public User findUser(Long userId) {
        Optional<User> userOptional = userRepository.findOneById(userId);
        return userOptional.orElseThrow(() -> new NotFoundException(format("The User [%s] not found.", userId)));
    }
}
