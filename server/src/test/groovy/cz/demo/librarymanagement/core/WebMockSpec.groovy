package cz.demo.librarymanagement.core


import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

@CompileStatic
abstract class WebMockSpec extends IntegrationSpec {

    @Autowired
    WebApplicationContext context

    MockMvc mockMvc

    def setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .alwaysDo(print())
                .build()
    }
}
