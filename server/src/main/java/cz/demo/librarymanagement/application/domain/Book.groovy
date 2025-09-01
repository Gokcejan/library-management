package cz.demo.librarymanagement.application.domain

import cz.demo.librarymanagement.domain.BookStatus
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener.class)
@Entity(name = "BOOK")
class Book extends AbstractEntity implements Comparable<Book> {

    @Column(name = "BOOK_TITLE", nullable = false)
    String title

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    BookStatus status = BookStatus.AVAILABLE

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    LocalDateTime createdAt

    @LastModifiedDate
    @Column(name = "UPDATED_AT")
    LocalDateTime updatedAt

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHOR_ID", nullable = false)
    Author relatedAuthor

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PUBLISHER_ID", nullable = false)
    Publisher relatedPublisher

    @OneToMany(mappedBy = "borrowedBook", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    Set<Borrow> borrows = new HashSet<>()

    @Override
    int compareTo(Book o) {
        return o.id.compareTo(this.id)
    }
}
