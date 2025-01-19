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
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@EntityListeners(AuditingEntityListener.class)
@Entity(name = "LIBRARY_USER")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tableGenerator")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    Long id

    @Column(name = "FIRST_NAME", nullable = false)
    String firstName

    @Column(name = "LAST_NAME", nullable = false)
    String lastName

    @Column(name = "EMAIL", unique = true, nullable = false)
    String email

    @Column(name = "PHONE", nullable = true)
    String phone

    @OneToMany(mappedBy = "borrowedBy", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    Set<Borrow> borrows = new HashSet<>()
}
