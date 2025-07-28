package cz.demo.librarymanagement.application.rest;

import cz.demo.librarymanagement.application.servicelayer.PublisherService;
import cz.demo.librarymanagement.dto.PublisherCreateDto;
import cz.demo.librarymanagement.dto.PublisherDto;
import cz.demo.librarymanagement.rest.PublisherRESTInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;

@RestController
public class PublisherRESTController implements PublisherRESTInterface {

    @Autowired
    PublisherService publisherService;

    @Override
    public ResponseEntity<PublisherDto> createPublisher(@RequestBody PublisherCreateDto createDto) {
        PublisherDto response = publisherService.createPublisher(createDto);
        URI location = URI.create(String.format("/publishers/%d", response.getId()));

        return ResponseEntity.created(location).body(response);
    }

    @Override
    public ResponseEntity<PublisherDto> getPublisher(@PathVariable("publisherId") Long publisherId) {
        PublisherDto response = publisherService.getPublisher(publisherId);
        return ResponseEntity.ok(response);
    }

    @Override
    public PagedModel<EntityModel<PublisherDto>> getPublishers(@RequestParam Map<String, String> queryParams) {
        return null;
    }

    @Override
    public ResponseEntity<PublisherDto> deletePublisher(@PathVariable("publisherId") Long publisherId) {
        return null;
    }
}
