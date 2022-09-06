package bg.tu.varna.informationSystem.controller;

import bg.tu.varna.informationSystem.dto.readingroom.ReadingRoomDto;
import bg.tu.varna.informationSystem.exception.BadRequestException;
import bg.tu.varna.informationSystem.service.ReadingRoomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reading-rooms")
public class ReadingRoomController {
    private final ReadingRoomService readingRoomService;

    public ReadingRoomController(ReadingRoomService readingRoomService) {
        this.readingRoomService = readingRoomService;
    }

    @GetMapping
    public List<ReadingRoomDto> findAll() {
        return readingRoomService.findAll();
    }
}
