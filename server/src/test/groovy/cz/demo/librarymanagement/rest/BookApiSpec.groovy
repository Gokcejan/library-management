package cz.demo.librarymanagement.rest

import cz.demo.librarymanagement.application.domain.Author
import cz.demo.librarymanagement.application.domain.Publisher
import cz.demo.librarymanagement.application.domain.repository.AuthorRepository
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class BookApiSpec extends BaseSpec implements CleanUpDb {

    @Autowired
    PublisherRepository publisherRepository

    @Autowired
    AuthorRepository authorRepository

    def "create book"() {

        given:
        createTestPublishers()
        createTestAuthors()
        List<Publisher> publisherList = publisherRepository.findAll() as List<Publisher>
        List<Author> authorList = authorRepository.findAll() as List<Author>
        Long publisherId = publisherList.get(0).id
        Long authorId = authorList.get(0).id

        when:
        ResultActions postBookResponse = mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
            {
                "title": "The Joke",
                "publisherId": "${publisherId}",
                "authorId": "${authorId}"
            }
        """))

        then:
        true

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
        def authorBodyMap = toMap(authorBody)
        ResultActions postAuthorResponse = mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorBody)
        )

        def postAuthorResponseBody = extractBodyFromResponseAsMap(postAuthorResponse)
        postAuthorResponse.andExpect(status().isCreated())

        return authorRepository.findAll()
    }

}
