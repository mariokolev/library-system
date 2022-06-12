package bg.tu.varna.informationSystem.repository;

import bg.tu.varna.informationSystem.dto.borrow.BorrowResponseDto;
import bg.tu.varna.informationSystem.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {

    @Query("from Borrow b where reader_id = :reader_id")
    List<Borrow> findAllByReader(@Param("reader_id") Long id);

    @Query("from Borrow b where reader_id = :reader_id and date_returned is null")
    List<Borrow> findAllNotReturnedByReader(@Param("reader_id") Long id);
}
