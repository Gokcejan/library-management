package cz.demo.librarymanagement.application.domain.repository;

import cz.demo.librarymanagement.application.domain.Author;
import cz.demo.librarymanagement.application.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    Optional<Book> findOneById(Long id);
}
