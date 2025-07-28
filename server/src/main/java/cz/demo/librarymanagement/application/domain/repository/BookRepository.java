package cz.demo.librarymanagement.application.domain.repository;

import cz.demo.librarymanagement.application.domain.Author;
import cz.demo.librarymanagement.application.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    Optional<Book> findOneById(Long id);

    @Query(value = """
        SELECT b 
        FROM BOOK b
        LEFT JOIN FETCH b.relatedAuthor a
        LEFT JOIN FETCH b.relatedPublisher p
        WHERE :search IS NULL 
          OR a.lastName LIKE %:search% 
        """,
            countQuery = "SELECT COUNT(b) FROM BOOK b LEFT JOIN b.relatedAuthor a WHERE :search IS NULL OR a.lastName LIKE %:search%"
    )
    Page<Book> findAllFilteredByAuthorLastName(@Param("search") String filterText, Pageable pageable);
}
