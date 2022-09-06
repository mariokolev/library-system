package bg.tu.varna.informationSystem.controller;

import bg.tu.varna.informationSystem.dto.borrow.BorrowRequestDto;
import bg.tu.varna.informationSystem.dto.borrow.BorrowResponseDto;
import bg.tu.varna.informationSystem.dto.borrow.BorrowReturnRequestDto;
import bg.tu.varna.informationSystem.dto.borrow.BorrowReturnResponseDto;
import bg.tu.varna.informationSystem.service.BorrowService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/borrows")
public class BorrowController {
    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('BORROW_BOOKS')")
    public BorrowResponseDto save(@Valid @RequestBody BorrowRequestDto borrowRequestDto) {
        return borrowService.save(borrowRequestDto);
    }

    @PutMapping("{id}/return")
    @PreAuthorize("hasAnyAuthority('BORROW_BOOKS')")
    public BorrowReturnResponseDto update(@PathVariable Long id, @Valid @RequestBody BorrowReturnRequestDto borrowReturnRequestDto) {
        return borrowService.update(id, borrowReturnRequestDto);
    }

    @GetMapping
    public List<BorrowResponseDto> findAll() {
        return borrowService.findAll();
    }

    @GetMapping("reader/{id}")
    @PreAuthorize("hasAnyAuthority('VIEW_BORROW_BOOKS')")
    public List<BorrowResponseDto> findAllByReader(@PathVariable Long id) {
        return borrowService.findAllByReader(id);
    }

    @GetMapping("reader/{id}/not-returned")
    @PreAuthorize("hasAnyAuthority('VIEW_BORROW_BOOKS')")
    public List<BorrowResponseDto> findAllNotReturnedByReader(@PathVariable Long id) {
        return borrowService.findAllNotReturnedByReader(id);
    }

    @GetMapping("/operator/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_VIEW_ALL_BORROW_BOOKS')")
    public List<BorrowResponseDto> findAllByOperator(@PathVariable Long id) {
        return borrowService.findAllByOperator(id);
    }
}
