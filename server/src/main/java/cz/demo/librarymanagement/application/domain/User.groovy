package cz.demo.librarymanagement.application.domain

import cz.demo.librarymanagement.domain.Role
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Enumerated
import jakarta.persistence.EnumType
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@EntityListeners(AuditingEntityListener.class)
@Entity(name = "LIBRARY_USER")
class User extends AbstractEntity {

    @Column(name = "FIRST_NAME", nullable = false)
    String firstName

    @Column(name = "LAST_NAME", nullable = false)
    String lastName

    @Column(name = "EMAIL", unique = true, nullable = false)
    String email

    @Column(name = "PHONE", nullable = true)
    String phone

    @Column(name = "USERNAME", unique = true, nullable = false)
    String username

    @Column(name = "PASSWORD", nullable = false)
    String password

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    Role role

    @OneToMany(mappedBy = "borrowedBy", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    Set<Borrow> borrows = new HashSet<>()

}
