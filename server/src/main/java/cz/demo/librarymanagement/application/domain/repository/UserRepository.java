package cz.demo.librarymanagement.application.domain.repository;

import cz.demo.librarymanagement.application.domain.Author;
import cz.demo.librarymanagement.application.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findOneByUsername(String username);

    Optional<User> findOneById(Long userId);
}
