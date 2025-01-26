package cz.demo.librarymanagement.rest

import cz.demo.librarymanagement.core.CleanUpDb
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.ResultActions

import static cz.demo.librarymanagement.rest.TestData.defaultAuthorBody
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.payload.PayloadDocumentation.*
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

}
