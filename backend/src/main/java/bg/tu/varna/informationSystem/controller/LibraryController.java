package bg.tu.varna.informationSystem.controller;

import bg.tu.varna.informationSystem.dto.libraries.LibraryResponseDto;
import bg.tu.varna.informationSystem.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/libraries")
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public List<LibraryResponseDto> findAll() {
        return libraryService.findAll();
    }
}
