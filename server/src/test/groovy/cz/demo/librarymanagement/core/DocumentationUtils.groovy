package cz.demo.librarymanagement.core

import org.springframework.restdocs.hypermedia.LinkDescriptor
import org.springframework.restdocs.hypermedia.LinksSnippet
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.ResponseFieldsSnippet

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.halLinks
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.halLinks
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields

class DocumentationUtils {

    static ResponseFieldsSnippet withFields(List<FieldDescriptor> descriptorsList, FieldDescriptor... descriptors) {
        descriptorsList.addAll(Arrays.asList(descriptors))
        return withFields(descriptorsList)
    }
    static ResponseFieldsSnippet withFields(List<FieldDescriptor> descriptorsList, List<FieldDescriptor> descriptorsList2 ,FieldDescriptor... descriptors) {
        descriptorsList.addAll(Arrays.asList(descriptors))
        descriptorsList.addAll(descriptorsList2)
        //descriptorsList.addAll(Arrays.asList(descriptorsList2))
        return withFields(descriptorsList)
    }

    static ResponseFieldsSnippet withFields(List<FieldDescriptor> descriptorsList) {
        return responseFields(descriptorsList)
    }

    static ResponseFieldsSnippet withFields(FieldDescriptor... descriptors) {
        return responseFields(descriptors)
    }

    static LinksSnippet withLinks(List<LinkDescriptor> descriptorsList, LinkDescriptor... descriptors) {
        descriptorsList.addAll(Arrays.asList(descriptors))
        return withLinks(descriptorsList)
    }

    static LinksSnippet withLinks(List<LinkDescriptor> descriptorsList) {
        return links(halLinks(), descriptorsList)
    }

    static LinksSnippet withLinks(LinkDescriptor... descriptors) {
        return links(halLinks(), descriptors)
    }
}
