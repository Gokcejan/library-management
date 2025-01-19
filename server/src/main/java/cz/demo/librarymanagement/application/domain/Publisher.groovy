package cz.demo.librarymanagement.application.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

import java.time.LocalDateTime

@EntityListeners([AuditingEntityListener.class])
@Entity(name = "PUBLISHER")
class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tableGenerator")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    Long id

    @Column(name = "NAME", nullable = false)
    String name

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    LocalDateTime createdAt

    @LastModifiedDate
    @Column(name = "UPDATED_AT")
    LocalDateTime updatedAt

    @OneToMany(mappedBy = "relatedPublisher", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    Set<Book> books = new HashSet<>()
}
