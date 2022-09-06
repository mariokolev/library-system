package bg.tu.varna.informationSystem.controller;

import bg.tu.varna.informationSystem.annotations.ValidEnum;
import bg.tu.varna.informationSystem.common.BookStatuses;
import bg.tu.varna.informationSystem.common.Messages;
import bg.tu.varna.informationSystem.dto.book.BookRequestDto;
import bg.tu.varna.informationSystem.dto.book.BookResponseDto;
import bg.tu.varna.informationSystem.dto.book.BookUpdateDto;
import bg.tu.varna.informationSystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_BOOKS')")
    public BookResponseDto save(@Valid @RequestBody BookRequestDto bookRequestDto) {
        return bookService.save(bookRequestDto);
    }

    @PutMapping("{id}/status")
    @PreAuthorize("hasAnyAuthority('MANAGE_BOOKS')")
    public BookResponseDto update(@PathVariable Long id, @Valid @RequestBody BookUpdateDto bookUpdateDto) {
        return bookService.update(id, bookUpdateDto.getStatus());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('VIEW_BOOKS')")
    public BookResponseDto find(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('VIEW_BOOKS')")
    public List<BookResponseDto> findAll() {
        return bookService.findAll();
    }

    @GetMapping("status/{status}")
    @PreAuthorize("hasAnyAuthority('VIEW_BOOKS')")
    public List<BookResponseDto> findAllByStatus(@PathVariable String status) {
        return bookService.findAllByStatus(status);
    }
}
