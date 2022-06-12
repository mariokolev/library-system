package bg.tu.varna.informationSystem.repository;

import bg.tu.varna.informationSystem.entity.ReadingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingRoomRepository extends JpaRepository<ReadingRoom, Long> {
}
