package cz.demo.librarymanagement.application.rest;

import cz.demo.librarymanagement.application.servicelayer.BorrowService;
import cz.demo.librarymanagement.application.utils.PaginationUtil;
import cz.demo.librarymanagement.dto.BorrowCreateDto;
import cz.demo.librarymanagement.dto.BorrowDto;
import cz.demo.librarymanagement.dto.BorrowUpdateDto;
import cz.demo.librarymanagement.rest.BorrowRESTInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class BorrowRESTController implements BorrowRESTInterface {

    @Autowired
    private PagedResourcesAssembler<BorrowDto> pagedResourcesAssembler;

    @Autowired
    BorrowService borrowService;


    @Override
    public ResponseEntity<BorrowDto> createBorrow(@RequestBody BorrowCreateDto createDto) {
        BorrowDto response = borrowService.createBorrow(createDto);
        URI location = URI.create(String.format("/borrows/%d", response.getId()));

        return ResponseEntity.created(location).body(response);
    }

    @Override
    public ResponseEntity<BorrowDto> getBorrow(@PathVariable Long borrowId) {
        BorrowDto response = borrowService.getBorrow(borrowId);
        return ResponseEntity.ok(response);
    }

    @Override
    public PagedModel<EntityModel<BorrowDto>> getBorrows(@RequestParam Map<String, String> queryParams) {
        Pageable pageable = PaginationUtil.resolvePageable(queryParams);

        Page<BorrowDto> borrowDtoPage = borrowService.getAllBorrows(pageable);

        return pagedResourcesAssembler.toModel(borrowDtoPage,
                BorrowDto -> EntityModel.of(BorrowDto,
                        linkTo(methodOn(BorrowRESTController.class).getBorrow(BorrowDto.getId())).withSelfRel()
                )
        );
    }

    @Override
    public ResponseEntity<BorrowDto> updateBorrow(@PathVariable("borrowId") Long borrowId, @RequestBody BorrowUpdateDto updateDto) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteBorrow(@PathVariable("borrowId") Long borrowId) {
        return null;
    }

    @Override
    public ResponseEntity<BorrowDto> returnBorrow(@PathVariable("borrowId") Long borrowId) {
        BorrowDto response = borrowService.returnBorrow(borrowId);
        return ResponseEntity.ok(response);
    }
}

