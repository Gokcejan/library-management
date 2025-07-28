package cz.demo.librarymanagement.rest

import cz.demo.librarymanagement.application.domain.Author
import cz.demo.librarymanagement.application.domain.Book
import cz.demo.librarymanagement.application.domain.Publisher
import cz.demo.librarymanagement.application.domain.User
import cz.demo.librarymanagement.application.domain.repository.AuthorRepository
import cz.demo.librarymanagement.application.domain.repository.BookRepository
import cz.demo.librarymanagement.application.domain.repository.PublisherRepository
import cz.demo.librarymanagement.application.domain.repository.UserRepository
import cz.demo.librarymanagement.core.CleanUpDb
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.ResultActions

import static cz.demo.librarymanagement.rest.TestData.defaultAuthorBody
import static cz.demo.librarymanagement.rest.TestData.defaultPublisherBody
import static cz.demo.librarymanagement.rest.TestData.defaultUserBody
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class BorrowApiSpec extends BaseSpec implements CleanUpDb {

    @Autowired
    PublisherRepository publisherRepository

    @Autowired
    AuthorRepository authorRepository

    @Autowired
    BookRepository bookRepository

    @Autowired
    UserRepository userRepository

    def "create borrow"() {

        given:
        createTestUsers()
        createTestBooks()

        List<User> userList = userRepository.findAll() as List<User>
        List<Book> bookList = bookRepository.findAll() as List<Book>
        Long userId = userList.get(0).id
        Long bookId = bookList.get(0).id

        when:
        ResultActions postBorrowResponse = mockMvc.perform(post("/borrows")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
            {
                "userId": "${userId}",
                "bookId": "${bookId}"
            }
        """))

        def postBorrowResponseBody = extractBodyFromResponseAsMap(postBorrowResponse)

        then:
        postBorrowResponse.andExpect(status().isCreated())
        postBorrowResponseBody.userId == userId
        postBorrowResponseBody.bookId == bookId

    }

    List<User> createTestUsers() {

        def userBody = defaultUserBody()
        def userBodyMap = toMap(userBody)

        ResultActions postUserResponse = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userBody)
        )

        def postUserResponseBody = extractBodyFromResponseAsMap(postUserResponse)

        postUserResponse.andExpect(status().isCreated())
        postUserResponseBody.firstName == userBodyMap.firstName
        postUserResponseBody.lastName == userBodyMap.lastName

        return userRepository.findAll()

    }

    List<Book> createTestBooks() {

        createTestPublishers()
        createTestAuthors()
        List<Publisher> publisherList = publisherRepository.findAll() as List<Publisher>
        List<Author> authorList = authorRepository.findAll() as List<Author>
        Long publisherId = publisherList.get(0).id
        Long authorId = authorList.get(0).id

        ResultActions postBookResponse = mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
            {
                "title": "The Joke",
                "publisherId": "${publisherId}",
                "authorId": "${authorId}"
            }
        """))

        postBookResponse.andExpect(status().isCreated())

        return bookRepository.findAll()

    }

    List<Publisher> createTestPublishers() {
        def publisherBody = defaultPublisherBody()
        def publisherBodyMap = toMap(publisherBody)
        ResultActions postPublisherResponse = mockMvc.perform(post("/publishers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(publisherBody)
        )

        def postPublisherResponseBody = extractBodyFromResponseAsMap(postPublisherResponse)

        postPublisherResponse.andExpect(status().isCreated())
        postPublisherResponseBody.name == publisherBodyMap.name

        return publisherRepository.findAll()

    }

    List<Author> createTestAuthors() {

        def authorBody = defaultAuthorBody()
        ResultActions postAuthorResponse = mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorBody)
        )

        postAuthorResponse.andExpect(status().isCreated())

        return authorRepository.findAll()
    }

}
