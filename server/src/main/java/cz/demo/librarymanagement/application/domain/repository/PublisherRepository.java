package cz.demo.librarymanagement.application.domain.repository;

import cz.demo.librarymanagement.application.domain.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long>, JpaSpecificationExecutor<Publisher> {


    Optional<Publisher> findOneById(Long publisherId);
}
