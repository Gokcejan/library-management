package cz.demo.librarymanagement.application.domain

import cz.demo.librarymanagement.domain.BorrowStatus
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener.class)
@Entity(name = "BORROW")
class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tableGenerator")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    Long id

    @Column(name = "BORROW_DATE", nullable = false)
    LocalDateTime borrowDate

    @Column(name = "RETURN_DATE")
    LocalDateTime returnDate

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    BorrowStatus status = BorrowStatus.BORROWED

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    LocalDateTime createdAt

    @LastModifiedDate
    @Column(name = "UPDATED_AT")
    LocalDateTime updatedAt

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOK_ID", nullable = false)
    Book borrowedBook

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    User borrowedBy

    @OneToMany(mappedBy = "borrow", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    Set<Fine> fines = new HashSet<>()
}
