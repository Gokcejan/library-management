package cz.demo.librarymanagement.rest;

import cz.demo.librarymanagement.dto.BorrowCreateDto;
import cz.demo.librarymanagement.dto.BorrowDto;
import cz.demo.librarymanagement.dto.BorrowUpdateDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public interface BorrowRESTInterface {

    @RequestMapping(value = "/borrows", method = RequestMethod.POST)
    ResponseEntity<BorrowDto> createBorrow(@RequestBody BorrowCreateDto createDto);

    @RequestMapping(value = "/borrows/{borrowId}", method = RequestMethod.GET)
    ResponseEntity<BorrowDto> getBorrow(@PathVariable("borrowId") Long borrowId);

    @RequestMapping(value = "/borrows", method = RequestMethod.GET)
    PagedModel<EntityModel<BorrowDto>> getBorrows(@RequestParam Map<String, String> queryParams);

    @RequestMapping(value = "/borrows/{borrowId}", method = RequestMethod.PUT)
    ResponseEntity<BorrowDto> updateBorrow(@PathVariable("borrowId") Long borrowId, @RequestBody BorrowUpdateDto updateDto);

    @RequestMapping(value = "/borrows/{borrowId}", method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteBorrow(@PathVariable("borrowId") Long borrowId);

    @RequestMapping(value = "/borrows/{borrowId}/return", method = RequestMethod.POST)
    ResponseEntity<BorrowDto> returnBorrow(@PathVariable("borrowId") Long borrowId);


}
