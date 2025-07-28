package cz.demo.librarymanagement.application.servicelayer;

import cz.demo.librarymanagement.application.domain.Book;
import cz.demo.librarymanagement.application.domain.Borrow;
import cz.demo.librarymanagement.application.domain.User;
import cz.demo.librarymanagement.application.domain.factory.BorrowMapper;
import cz.demo.librarymanagement.application.domain.repository.BookRepository;
import cz.demo.librarymanagement.application.domain.repository.BorrowRepository;
import cz.demo.librarymanagement.application.exceptions.NotFoundException;
import cz.demo.librarymanagement.domain.BookStatus;
import cz.demo.librarymanagement.domain.BorrowStatus;
import cz.demo.librarymanagement.dto.BorrowCreateDto;
import cz.demo.librarymanagement.dto.BorrowDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.lang.String.format;

@Service
public class BorrowService {

    @Autowired
    BorrowRepository borrowRepository;

    @Autowired
    BorrowMapper borrowMapper;

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    @Autowired
    BookRepository bookRepository;


    public BorrowDto createBorrow(BorrowCreateDto createDto) {

        Book relatedBook = bookService.findBook(createDto.getBookId());
        User relatedUser = userService.findUser(createDto.getUserId());

        Borrow borrow = borrowMapper.toEntity(relatedBook, relatedUser);
        Borrow savedBorrow = borrowRepository.save(borrow);
        return borrowMapper.toDto(savedBorrow);

    }

    public BorrowDto getBorrow(Long borrowId) {
        Borrow borrow = findBorrow(borrowId);
        return borrowMapper.toDto(borrow);
    }

    public Page<BorrowDto> getAllBorrows(Pageable pageable) {
        return null;
    }

    public BorrowDto returnBorrow(Long borrowId) {
        Borrow borrow = findBorrow(borrowId);
        Long borrowBookId = borrow.getBorrowedBook().getId();
        Book borrowedBook = bookService.findBook(borrowBookId);

        borrow.setStatus(BorrowStatus.RETURNED);
        borrow.setReturnDate(LocalDateTime.now());
        Borrow savedBorrow = borrowRepository.save(borrow);

        borrowedBook.setStatus(BookStatus.AVAILABLE);
        bookRepository.save(borrowedBook);

        return borrowMapper.toDto(savedBorrow);
    }

    Borrow findBorrow(Long borrowId) {
        Optional<Borrow> borrowOptional = borrowRepository.findOneById(borrowId);
        return borrowOptional.orElseThrow(() -> new NotFoundException(format("The Borrow [%s] not found.", borrowId)));
    }
}
