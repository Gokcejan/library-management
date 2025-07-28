package cz.demo.librarymanagement.rest

import cz.demo.librarymanagement.application.domain.User
import cz.demo.librarymanagement.application.domain.repository.UserRepository
import cz.demo.librarymanagement.core.CleanUpDb
import groovy.json.JsonOutput
import org.apache.groovy.json.internal.LazyMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.ResultActions

import static cz.demo.librarymanagement.rest.TestData.defaultUserBody
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class UserApiSpec extends BaseSpec implements CleanUpDb {

    @Autowired
    UserRepository userRepository


    def "create user"() {

        given:
        def userBody = defaultUserBody()
        def userBodyMap = toMap(userBody)

        when:
        ResultActions postUserResponse = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userBody)
        )

        def postUserResponseBody = extractBodyFromResponseAsMap(postUserResponse)

        then:
        postUserResponse.andExpect(status().isCreated())
        postUserResponseBody.firstName == userBodyMap.firstName
        postUserResponseBody.lastName == userBodyMap.lastName

    }

    def 'get single user'() {

        given:
        def userBody = defaultUserBody()
        def userBodyMap = toMap(userBody)

        ResultActions postUserResponse = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userBody)
        )

        String postUserLocation = extractLocationFromHeader(postUserResponse)
        postUserResponse.andExpect(status().isCreated())
        postUserLocation.contains("http://library-management/authors/")

        when:
        ResultActions getUserResponse = mockMvc.perform(get(postUserLocation))
        def getUserResponseBody = extractBodyFromResponseAsMap(getUserResponse)

        then:
        getUserResponse.andExpect(status().isOk())
        getUserResponseBody.firstName == userBodyMap.firstName
        getUserResponseBody.lastName == userBodyMap.lastName

    }

    def 'get all users sorted by id descending'() {

        given:
        createTestUsers()

        when:
        ResultActions getUsersResponse = mockMvc.perform(get("/users?sort=id,desc&page=0&size=10"))

        then:
        LazyMap body = toMap(getUsersResponse.andReturn().response.contentAsString)
        getUsersResponse.andExpect(status().isOk())
        body.page.totalPages == 1
        body.page.size == 10
        body.page.number == 0
        body.page.totalElements == 3

    }

    def 'authenticate single user'() {

        given:
        def userBody = defaultUserBody()
        def userBodyMap = toMap(userBody)


        ResultActions postUserResponse = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userBody)
        )
        postUserResponse.andExpect(status().isCreated())

        List<User> versionList = userRepository.findAll() as List<User>
        String username = versionList[0].username
        String password = userBodyMap.password

        when:
        ResultActions authenticateUserResponse = mockMvc.perform(post("/users/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
            {   
                "username": "${username}",
                "password": "${password}"
            }
                                 """
                ))

        then:
        authenticateUserResponse.andExpect(status().isOk())

    }

    def 'delete user'() {

        given:
        def userBody = defaultUserBody()

        ResultActions postUserResponse = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userBody)
        )
        postUserResponse.andExpect(status().isCreated())

        String postUserLocation =  extractLocationFromHeader(postUserResponse)

        when:
        ResultActions deleteUserResponse = mockMvc.perform(delete(postUserLocation))

        then:
        deleteUserResponse.andExpect(status().isNoContent())
    }



    List<User> createTestUsers(){

        def userBody = defaultUserBody()
        def userBodyMap = toMap(userBody)

        for (int i = 1; i < 4; i++) {
            userBodyMap.firstName = "George${i}"
            userBodyMap.lastName = "Trump${i}"
            userBodyMap.username = "george${i}"
            userBodyMap.password = "password${i}"
            userBodyMap.email = "george@trump.com${i}"
            userBodyMap.phone = "123456789${i}"
            userBodyMap.role = "USER"
            mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonOutput.class.toJson(userBodyMap))
            )
        }

        return userRepository.findAll() as List<User>
    }

}
