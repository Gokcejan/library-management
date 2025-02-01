package cz.demo.librarymanagement.core

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

@CompileStatic
abstract class WebMockSpec extends IntegrationSpec {

    @Autowired
    WebApplicationContext context

    ManualRestDocumentation restDocumentation = new ManualRestDocumentation("build/doc-api-adoc")

    MockMvc mockMvc

    def setup() {
        restDocumentation.beforeTest(getClass(), specificationContext.currentIteration.name)

        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(print())
                .alwaysDo(new PrettyPrintResultHandler())
                .build()
    }

    def cleanup() {
        restDocumentation.afterTest()
    }
}