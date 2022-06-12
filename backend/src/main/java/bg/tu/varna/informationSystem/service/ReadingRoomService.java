package bg.tu.varna.informationSystem.service;

import bg.tu.varna.informationSystem.common.Messages;
import bg.tu.varna.informationSystem.entity.ReadingRoom;
import bg.tu.varna.informationSystem.exception.BadRequestException;
import bg.tu.varna.informationSystem.repository.ReadingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReadingRoomService {
    private final ReadingRoomRepository readingRoomRepository;

    @Autowired
    public ReadingRoomService(ReadingRoomRepository readingRoomRepository) {
        this.readingRoomRepository = readingRoomRepository;
    }

    public ReadingRoom findById(Long id) {
        return readingRoomRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(Messages.READING_ROOM_NOT_FOUND));
    }
}
