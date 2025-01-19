package cz.demo.librarymanagement.application.domain

import jakarta.persistence.*
import lombok.Getter
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener.class)
@Entity(name = "AUTHOR")
@Getter
class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tableGenerator")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    Long id

    @Column(name = "FIRST_NAME", nullable = false)
    String firstName

    @Column(name = "LAST_NAME", nullable = false)
    String lastName

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    LocalDateTime createdAt

    @LastModifiedDate
    @Column(name = "UPDATED_AT")
    LocalDateTime updatedAt

    @OneToMany(mappedBy = "relatedAuthor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    Set<Book> books = new HashSet<>()
}
