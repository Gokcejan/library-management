package cz.demo.librarymanagement.application.servicelayer;

import cz.demo.librarymanagement.application.domain.Author;
import cz.demo.librarymanagement.application.domain.Book;
import cz.demo.librarymanagement.application.domain.factory.BookMapper;
import cz.demo.librarymanagement.application.domain.repository.BookRepository;
import cz.demo.librarymanagement.application.exceptions.NotFoundException;
import cz.demo.librarymanagement.dto.BookCreateDto;
import cz.demo.librarymanagement.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    AuthorService authorService;

    public BookDto createBook(BookCreateDto createDto) {
        Author author = authorService.findAuthor(createDto.getAuthorId());
        //TODO: add publisher
        Book book = bookMapper.toEntity(createDto, author, null);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    public BookDto getBook(Long bookId) {
        Book book = findBook(bookId);
        return bookMapper.toDto(book);
    }

    public Page<BookDto> getAllBooks(Pageable pageable) {
        Page<Book> booksPage = bookRepository.findAll(pageable);
        return booksPage.map(bookMapper::toDto);
    }

    public void deleteBook(Long bookId) {

        Book book = findBook(bookId);

        if (book == null) {
            throw new IllegalArgumentException("User not found: " + bookId);
        }
        bookRepository.delete(book);

    }

    Book findBook(Long bookId) {
        Optional<Book> bookOptional = bookRepository.findOneById(bookId);
        return bookOptional.orElseThrow(() -> new NotFoundException(format("The Book [%s] not found.", bookId)));
    }
}
