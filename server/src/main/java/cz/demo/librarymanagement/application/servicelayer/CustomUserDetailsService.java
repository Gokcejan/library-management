package cz.demo.librarymanagement.application.servicelayer;

import cz.demo.librarymanagement.application.domain.User;
import cz.demo.librarymanagement.application.domain.UserPrincipal;
import cz.demo.librarymanagement.application.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUser(username);

        return new UserPrincipal(user);
    }

    private User findUser(String username) {
        Optional<User> versionOptional = userRepository.findOneByUsername(username);
        return versionOptional.orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
    }
}
