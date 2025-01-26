package cz.demo.librarymanagement.core


import groovy.transform.CompileStatic
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.restdocs.JUnitRestDocumentation
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Shared

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

@CompileStatic
abstract class WebMockSpec extends IntegrationSpec {

    @Autowired
    WebApplicationContext context

    // Tímto konstruktoru rovnou říkáte, kam se mají snippety generovat:
    ManualRestDocumentation restDocumentation = new ManualRestDocumentation("build/doc-api-adoc")

    MockMvc mockMvc

    def setup() {
        // Zapne REST Docs kontext pro konkrétní test (Spock-voláno před každou testovací metodou)
        restDocumentation.beforeTest(getClass(), specificationContext.currentIteration.name)

        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(print())
                .build()
    }

    def cleanup() {
        // Vypne REST Docs kontext (Spock-voláno po každé testovací metodě)
        restDocumentation.afterTest()
    }
}