package bg.tu.varna.informationSystem.repository;

import bg.tu.varna.informationSystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("from Book b where b.status = :status")
    List<Book> findAllByStatus(@Param("status") String status);
}
