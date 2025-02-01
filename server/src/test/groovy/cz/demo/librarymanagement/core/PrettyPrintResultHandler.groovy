package cz.demo.librarymanagement.core

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultHandler

class PrettyPrintResultHandler implements ResultHandler {

    private final ObjectMapper objectMapper

    PrettyPrintResultHandler() {

        this.objectMapper = new ObjectMapper()
    }

    @Override
    void handle(MvcResult result) throws Exception {

        String rawBody = result.getResponse().getContentAsString()

        if (rawBody != null && rawBody.length() > 0
                && result.getResponse().getContentType() != null
                && result.getResponse().getContentType().contains("json")) {

            try {
                JsonNode jsonNode = objectMapper.readTree(rawBody)

                String pretty = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode)

                System.out.println("=== PRETTY JSON RESPONSE (MockMvc) ===")
                System.out.println(pretty)
            } catch (Exception e) {
                System.out.println("=== RAW RESPONSE (MockMvc) ===")
                System.out.println(rawBody)
            }
        }
    }
}
