package cz.demo.librarymanagement.application.servicelayer;

import cz.demo.librarymanagement.application.domain.Author;
import cz.demo.librarymanagement.application.domain.Book;
import cz.demo.librarymanagement.application.domain.Publisher;
import cz.demo.librarymanagement.application.domain.factory.BookMapper;
import cz.demo.librarymanagement.application.domain.repository.BookRepository;
import cz.demo.librarymanagement.application.exceptions.NotFoundException;
import cz.demo.librarymanagement.dto.BookCreateDto;
import cz.demo.librarymanagement.dto.BookDto;
import cz.demo.librarymanagement.dto.BookUpdateDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    @Autowired
    PublisherService publisherService;

    public BookDto createBook(BookCreateDto createDto) {
        Author author = authorService.findAuthor(createDto.getAuthorId());
        Publisher publisher = publisherService.findPublisher(createDto.getPublisherId());

        Book book = bookMapper.toEntity(createDto, author, publisher);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    public BookDto getBook(Long bookId) {
        Book book = findBook(bookId);
        return bookMapper.toDto(book);
    }
    @Transactional
    public Page<BookDto> getAllBooks(String filterText, Pageable pageable) {
        Pageable effective = pageable.getSort().isUnsorted()
                ? PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Direction.ASC, "id"))
                : pageable;

        Page<Book> page = (filterText == null || filterText.isBlank())
                ? bookRepository.findAll(effective)
                : bookRepository.findAllFilteredByAuthorLastName(filterText, effective);

        return page.map(bookMapper::toDto);
    }

    public void deleteBook(Long bookId) {

        Book book = findBook(bookId);

        if (book == null) {
            throw new IllegalArgumentException("Book not found: " + bookId);
        }
        bookRepository.delete(book);

    }

     public Book findBook(Long bookId) {
        Optional<Book> bookOptional = bookRepository.findOneById(bookId);
        return bookOptional.orElseThrow(() -> new NotFoundException(format("The Book [%s] not found.", bookId)));
    }

    @Transactional
    public BookDto updateBook(Long bookId, BookUpdateDto dto) {
        Book book = findBook(bookId);
        Author author = authorService.findAuthor(dto.getAuthorId());
        Publisher publisher = publisherService.findPublisher(dto.getPublisherId());

        Book updatedBook = bookMapper.updateEntity(book, dto, author, publisher);

        Book savedBook = bookRepository.save(updatedBook);
        return bookMapper.toDto(savedBook);

    }
}
