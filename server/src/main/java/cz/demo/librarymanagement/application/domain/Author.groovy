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
class Author extends AbstractEntity {

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
