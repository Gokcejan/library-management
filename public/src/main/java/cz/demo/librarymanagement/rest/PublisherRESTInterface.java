package cz.demo.librarymanagement.rest;

import cz.demo.librarymanagement.dto.PublisherCreateDto;
import cz.demo.librarymanagement.dto.PublisherDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public interface PublisherRESTInterface {

    @RequestMapping(value = "/publishers", method = RequestMethod.POST)
    ResponseEntity<PublisherDto> createPublisher(@RequestBody PublisherCreateDto createDto);

    @RequestMapping(value = "/publishers/{publisherId}", method = RequestMethod.GET)
    ResponseEntity<PublisherDto> getPublisher(@PathVariable("publisherId") Long publisherId);

    @RequestMapping(value = "/publishers", method = RequestMethod.GET)
    PagedModel<EntityModel<PublisherDto>> getPublishers(@RequestParam Map<String, String> queryParams);

    @RequestMapping(value = "/publishers/{publisherId}", method = RequestMethod.DELETE)
    ResponseEntity<PublisherDto> deletePublisher(@PathVariable("publisherId") Long publisherId);

}
