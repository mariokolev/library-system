package bg.tu.varna.informationSystem.repository;

import bg.tu.varna.informationSystem.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Boolean existsByName(String name);

}
