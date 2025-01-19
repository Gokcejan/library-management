package cz.demo.librarymanagement.application.domain.repository;

import cz.demo.librarymanagement.application.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {

    Optional<Author> findOneById(Long id);
}
