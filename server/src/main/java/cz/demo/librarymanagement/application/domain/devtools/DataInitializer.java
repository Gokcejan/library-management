package cz.demo.librarymanagement.application.domain.devtools;

import cz.demo.librarymanagement.application.domain.Author;
import cz.demo.librarymanagement.application.domain.Book;
import cz.demo.librarymanagement.application.domain.Publisher;
import cz.demo.librarymanagement.application.domain.repository.AuthorRepository;
import cz.demo.librarymanagement.application.domain.repository.BookRepository;
import cz.demo.librarymanagement.application.domain.repository.PublisherRepository;
import cz.demo.librarymanagement.application.servicelayer.UserService;
import cz.demo.librarymanagement.domain.Role;
import cz.demo.librarymanagement.dto.UserCreateDto;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
@Profile("dev")
public class DataInitializer {


    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    PublisherRepository publisherRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserService userService;

    @PostConstruct
    public void init() {
        if (bookRepository.count() > 0) {
            log.info("Test data already exists. Skipping initialization.");
            return;
        }

        log.info("Starting test data initialization...");

        Author author1 = new Author();
        author1.setFirstName("Karel");
        author1.setLastName("Čapek");

        Author author2 = new Author();
        author2.setFirstName("Božena");
        author2.setLastName("Němcová");

        Author author3 = new Author();
        author3.setFirstName("Franz");
        author3.setLastName("Kafka");

        authorRepository.saveAll(Arrays.asList(author1, author2, author3));
        List<Author> authors = Arrays.asList(author1, author2, author3);

        Publisher publisher1 = new Publisher();
        publisher1.setName("České nakladatelství");

        Publisher publisher2 = new Publisher();
        publisher2.setName("Středoevropské vydavatelství");

        Publisher publisher3 = new Publisher();
        publisher3.setName("Literární dům Praha");

        publisherRepository.saveAll(List.of(publisher1, publisher2, publisher3));
        List<Publisher> publishers = List.of(publisher1, publisher2, publisher3);

        List<String> bookTitles = Arrays.asList(
                "Stíny nad Vltavou",
                "Cesta do nitra duše",
                "Město beze snů",
                "Ticho mezi hvězdami",
                "Kronika zapomenutých",
                "Pod kůží reality",
                "Ztracená hora",
                "Poslední dopis",
                "Básníkův úkryt",
                "Mlha nad Krkonošemi",
                "Údolí vlků",
                "Návrat ze tmy",
                "Černé slunce",
                "Tajemství staré knihovny",
                "Na konci času",
                "Zahrada iluzí",
                "Růže z popela",
                "Věž tisíce zrcadel",
                "Legenda o stínu",
                "Píseň větru",
                "Zlomená koruna",
                "Tanec bez rytmu",
                "Kameny mluví",
                "Dotek věčnosti",
                "Cirkus zatracených",
                "Řeka, která spí",
                "Krvavé nebe",
                "Posel bez jména",
                "Kód nesmrtelnosti",
                "Šepot labutí"
        );

        Collections.shuffle(bookTitles);
        Random random = new Random();

        for (String title : bookTitles) {
            Author randomAuthor = authors.get(random.nextInt(authors.size()));
            Publisher randomPublisher = publishers.get(random.nextInt(publishers.size()));

            Book book = new Book();
            book.setTitle(title);
            book.setRelatedAuthor(randomAuthor);
            book.setRelatedPublisher(randomPublisher);

            randomAuthor.getBooks().add(book);
            randomPublisher.getBooks().add(book);

            bookRepository.save(book);
        }

        userService.createUser(new UserCreateDto("Jan", "Kundera", "username",
                "password", "jan@gmail.com", "123456", Role.USER));

        userService.createUser(new UserCreateDto("Iva", "Jerabkova", "iva",
                "iva", "iva@gmail.com", "78956", Role.ADMIN));

        log.info("Ending test data initialization...");
    }
}
