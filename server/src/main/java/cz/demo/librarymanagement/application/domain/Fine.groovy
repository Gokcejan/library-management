package cz.demo.librarymanagement.application.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener.class)
@Entity(name = "FINE")
class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tableGenerator")
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    Long id

    @Column(name = "AMOUNT", nullable = false)
    BigDecimal amount

    @Column(name = "ISSUED_DATE", nullable = false)
    LocalDateTime issuedDate

    @Column(name = "PAID", nullable = false)
    Boolean paid = false

    @Column(name = "PAID_DATE")
    LocalDateTime paidDate

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    LocalDateTime createdAt

    @LastModifiedDate
    @Column(name = "UPDATED_AT")
    LocalDateTime updatedAt

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BORROW_ID", nullable = false)
    Borrow borrow
}
