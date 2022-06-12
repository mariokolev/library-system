package bg.tu.varna.informationSystem.controller;

import bg.tu.varna.informationSystem.dto.book.BookRequestDto;
import bg.tu.varna.informationSystem.dto.book.BookResponseDto;
import bg.tu.varna.informationSystem.dto.book.BookUpdateDto;
import bg.tu.varna.informationSystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
//@PreAuthorize("hasAnyAuthority('add','view')")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public BookResponseDto save(@Valid @RequestBody BookRequestDto bookRequestDto) {
        return bookService.save(bookRequestDto);
    }

    @PutMapping("{id}/status")
    public BookResponseDto update(@PathVariable Long id, @Valid @RequestBody BookUpdateDto bookUpdateDto) {
        return bookService.update(id, bookUpdateDto.getStatus());
    }

    @GetMapping("{id}")
    public BookResponseDto find(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @GetMapping
    public List<BookResponseDto> findAll() {
        return bookService.findAll();
    }

}
