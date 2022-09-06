package bg.tu.varna.informationSystem.service;

import bg.tu.varna.informationSystem.common.Messages;
import bg.tu.varna.informationSystem.dto.book.BookResponseDto;
import bg.tu.varna.informationSystem.dto.borrow.BorrowResponseDto;
import bg.tu.varna.informationSystem.dto.readingroom.ReadingRoomDto;
import bg.tu.varna.informationSystem.dto.users.UserResponseDto;
import bg.tu.varna.informationSystem.entity.ReadingRoom;
import bg.tu.varna.informationSystem.exception.BadRequestException;
import bg.tu.varna.informationSystem.repository.ReadingRoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReadingRoomService {
    private final ReadingRoomRepository readingRoomRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReadingRoomService(ReadingRoomRepository readingRoomRepository, ModelMapper modelMapper) {
        this.readingRoomRepository = readingRoomRepository;
        this.modelMapper = modelMapper;
    }

    public ReadingRoom findById(Long id) {
        return readingRoomRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(Messages.READING_ROOM_NOT_FOUND));
    }

    public List<ReadingRoomDto> findAll() {
        return readingRoomRepository.findAll().stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    private ReadingRoomDto convertToResponseDto(ReadingRoom readingRoom) {
        ReadingRoomDto readingRoomDto = modelMapper.map(readingRoom, ReadingRoomDto.class);
        return readingRoomDto;
    }
}
