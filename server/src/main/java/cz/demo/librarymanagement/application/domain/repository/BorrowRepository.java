package cz.demo.librarymanagement.application.domain.repository;

import cz.demo.librarymanagement.application.domain.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BorrowRepository extends JpaRepository<Borrow, Long>, JpaSpecificationExecutor<Borrow> {

    Optional<Borrow> findOneById(Long borrowId);
}
