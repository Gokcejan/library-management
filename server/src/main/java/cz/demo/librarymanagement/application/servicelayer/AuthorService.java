package cz.demo.librarymanagement.application.servicelayer;

import cz.demo.librarymanagement.application.domain.Author;
import cz.demo.librarymanagement.application.domain.factory.AuthorMapper;
import cz.demo.librarymanagement.application.domain.repository.AuthorRepository;
import cz.demo.librarymanagement.application.exceptions.NotFoundException;
import cz.demo.librarymanagement.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
public class AuthorService {

    @Autowired
    AuthorMapper authorMapper;

    @Autowired
    AuthorRepository authorRepository;


    public AuthorDto createAuthor(AuthorCreateDto createDto) {
        Author author = authorMapper.toEntity(createDto);
        Author savedAuthor = authorRepository.save(author);
        return authorMapper.toDto(savedAuthor);
    }


   /* public Author createAuthor(AuthorCreateDto createDto) {
        Author author = authorMapper.toEntity(createDto);
        return authorRepository.save(author);
    }*/

    public AuthorDto getAuthor(Long authorId) {
        Author author = findAuthor(authorId);
        return authorMapper.toDto(author);
    }

    private Author findAuthor(Long authorId) {
        Optional<Author> authorOptional = authorRepository.findOneById(authorId);
        return authorOptional.orElseThrow(() -> new NotFoundException(format("The Author [%s] not found.", authorId)));
    }
}
