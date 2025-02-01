package cz.demo.librarymanagement.rest

import cz.demo.librarymanagement.core.CleanUpDb
import groovy.json.JsonOutput
import org.apache.groovy.json.internal.LazyMap
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.ResultActions

import static cz.demo.librarymanagement.rest.TestData.defaultAuthorBody
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.payload.PayloadDocumentation.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class AuthorApiSpec extends BaseSpec implements CleanUpDb {


    def "create author"() {

        given:
        def authorBody = defaultAuthorBody()
        def authorBodyMap = toMap(authorBody)


        when:
        ResultActions postAuthorResponse = mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorBody)
        )

        def postAuthorResponseBody = extractBodyFromResponseAsMap(postAuthorResponse)

        then:
        postAuthorResponse.andExpect(status().isCreated())
        postAuthorResponseBody.firstName == authorBodyMap.firstName
        postAuthorResponseBody.lastName == authorBodyMap.lastName

        and: "Generate documentation"
        postAuthorResponse.andDo(document("create-author",
                requestFields(
                        fieldWithPath("firstName").description("The first name of the author"),
                        fieldWithPath("lastName").description("The last name of the author")
                ),
                responseFields(
                        fieldWithPath("id").description("The ID of the created author"),
                        fieldWithPath("firstName").description("The first name of the created author"),
                        fieldWithPath("lastName").description("The last name of the created author"),
                        fieldWithPath("books").description("The list of book IDs associated with the author")
                )
        ))

    }

    def 'get single author'() {

        given:
        def authorBody = defaultAuthorBody()
        def authorBodyMap = toMap(authorBody)

        ResultActions postAuthorResponse = mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorBody)
        )

        String postAuthorLocation = extractLocationFromHeader(postAuthorResponse)
        postAuthorResponse.andExpect(status().isCreated())
        postAuthorLocation.contains("http://library-management/authors/")

        when:
        ResultActions getAuthorResponse = mockMvc.perform(get(postAuthorLocation))
        def getAuthorResponseBody = extractBodyFromResponseAsMap(getAuthorResponse)

        then:
        getAuthorResponse.andExpect(status().isOk())
        getAuthorResponseBody.firstName == authorBodyMap.firstName
        getAuthorResponseBody.lastName == authorBodyMap.lastName

    }

    def 'get authors collection'() {

        given: "Create 6 authors"
        def authorBody = defaultAuthorBody()
        def authorBodyMap = toMap(authorBody)
        for (int i = 1; i < 7; i++) {
            authorBodyMap.firstName = "Milan${i}"
            authorBodyMap.lastName = "Kundera${i}"
            mockMvc.perform(post("/authors")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonOutput.class.toJson(authorBodyMap))
            )
        }

        when:
        ResultActions getAuthorsResult = mockMvc.perform(get("/authors?page=0&size=10"))

        then:
        LazyMap body = toMap(getAuthorsResult.andReturn().response.contentAsString)
        getAuthorsResult.andExpect(status().isOk())
        body.page.totalPages == 1
        body.page.size == 10
        body.page.number == 0
        body.page.totalElements == 6

    }

}
