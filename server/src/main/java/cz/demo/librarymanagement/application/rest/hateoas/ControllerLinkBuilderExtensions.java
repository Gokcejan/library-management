package cz.demo.librarymanagement.application.rest.hateoas;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.net.URI;

import static java.lang.String.format;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class ControllerLinkBuilderExtensions {




    public static URI resourceLocation(String serviceAlias, Object controllerMethod) {
        return URI.create(createHref(serviceAlias, controllerMethod));
    }

    private static String createHref(String serviceAlias, Object controllerMethod) {
        WebMvcLinkBuilder webMvcLinkBuilder = linkTo(controllerMethod);
        String path = webMvcLinkBuilder.toUri().getPath();
        return format("http://%s%s", serviceAlias, path);
    }
}
