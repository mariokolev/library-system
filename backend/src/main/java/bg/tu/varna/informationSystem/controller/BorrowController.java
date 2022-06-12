package bg.tu.varna.informationSystem.controller;

import bg.tu.varna.informationSystem.dto.borrow.BorrowRequestDto;
import bg.tu.varna.informationSystem.dto.borrow.BorrowResponseDto;
import bg.tu.varna.informationSystem.dto.borrow.BorrowReturnRequestDto;
import bg.tu.varna.informationSystem.dto.borrow.BorrowReturnResponseDto;
import bg.tu.varna.informationSystem.service.BorrowService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/borrows")
//@PreAuthorize("hasAnyAuthority('add','view')")
public class BorrowController {
    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @PostMapping
    public BorrowResponseDto save(@Valid @RequestBody BorrowRequestDto borrowRequestDto) {
        return borrowService.save(borrowRequestDto);
    }

    @PutMapping("{id}/return")
    public BorrowReturnResponseDto update(@PathVariable Long id,@Valid @RequestBody BorrowReturnRequestDto borrowReturnRequestDto) {
        return borrowService.update(id, borrowReturnRequestDto);
    }

    @GetMapping
    public List<BorrowResponseDto> findAll() {
        return borrowService.findAll();
    }
}
