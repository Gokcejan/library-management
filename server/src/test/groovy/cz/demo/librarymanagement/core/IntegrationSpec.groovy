package cz.demo.librarymanagement.core

import groovy.json.JsonSlurper
import org.apache.groovy.json.internal.LazyMap
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
abstract class IntegrationSpec extends Specification {


    JsonSlurper jsonSlurper = new JsonSlurper()

    LazyMap toMap (String jsonAsString) {
        return jsonSlurper.parseText(jsonAsString) as LazyMap
    }

    LazyMap extractBodyFromResponseAsMap(ResultActions resultActions) {
        toMap(resultActions.andReturn().response.contentAsString)
    }

    String extractLocationFromHeader(ResultActions postUserResponse) {
        return postUserResponse.andReturn().response.getHeader("Location")
    }
}
