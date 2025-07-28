package cz.demo.librarymanagement.rest

import cz.demo.librarymanagement.core.CleanUpDb
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.ResultActions

import static cz.demo.librarymanagement.rest.TestData.defaultPublisherBody
import static cz.demo.librarymanagement.rest.TestData.defaultUserBody
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class PublisherApiSpec extends BaseSpec implements CleanUpDb {

    def "create publisher"() {

        given:
        def publisherBody = defaultPublisherBody()
        def publisherBodyMap = toMap(publisherBody)

        when:
        ResultActions postPublisherResponse = mockMvc.perform(post("/publishers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(publisherBody)
        )

        def postPublisherResponseBody = extractBodyFromResponseAsMap(postPublisherResponse)

        then:
        postPublisherResponse.andExpect(status().isCreated())
        postPublisherResponseBody.name == publisherBodyMap.name

    }

    def 'get single publisher'() {

        given:
        def publisherBody = defaultPublisherBody()
        def publisherBodyMap = toMap(publisherBody)

        ResultActions postPublisherResponse = mockMvc.perform(post("/publishers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(publisherBody)
        )

        String postPublisherLocation = extractLocationFromHeader(postPublisherResponse)
        postPublisherResponse.andExpect(status().isCreated())
        postPublisherLocation.contains("http://library-management/publishers/")

        when:
        ResultActions getPublisherResponse = mockMvc.perform(get(postPublisherLocation))
        def getPublisherResponseBody = extractBodyFromResponseAsMap(getPublisherResponse)

        then:
        getPublisherResponse.andExpect(status().isOk())
        getPublisherResponseBody.name == publisherBodyMap.name

    }

}
