package cz.demo.librarymanagement.application.servicelayer;

import cz.demo.librarymanagement.application.domain.Publisher;
import cz.demo.librarymanagement.application.domain.factory.PublisherMapper;
import cz.demo.librarymanagement.application.domain.repository.PublisherRepository;
import cz.demo.librarymanagement.application.exceptions.NotFoundException;
import cz.demo.librarymanagement.dto.PublisherCreateDto;
import cz.demo.librarymanagement.dto.PublisherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
public class PublisherService {

    @Autowired
    PublisherMapper publisherMapper;

    @Autowired
    PublisherRepository publisherRepository;

    public PublisherDto createPublisher(PublisherCreateDto createDto) {
        Publisher publisher = publisherMapper.toEntity(createDto);
        Publisher savedPublisher = publisherRepository.save(publisher);
        return publisherMapper.toDto(savedPublisher);
    }

    public PublisherDto getPublisher(Long publisherId) {
        Publisher publisher = findPublisher(publisherId);
        return publisherMapper.toDto(publisher);
    }

    public Publisher findPublisher(Long publisherId) {
        Optional<Publisher> userOptional = publisherRepository.findOneById(publisherId);
        return userOptional.orElseThrow(() -> new NotFoundException(format("The Publisher [%s] not found.", publisherId)));

    }
}