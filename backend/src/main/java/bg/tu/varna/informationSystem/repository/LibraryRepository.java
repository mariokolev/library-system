package bg.tu.varna.informationSystem.repository;

import bg.tu.varna.informationSystem.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {

    @Modifying
    @Query(value = "INSERT INTO users_libraries(user_id,library_id) VALUES(:userId,:libraryId)", nativeQuery = true)
    void assignUserToLibrary(@Param("userId") Long userId, @Param("libraryId") Long libraryId);

    @Query(value = "select libraries.id, libraries.name from users_libraries "
            + "join users on users_libraries.user_id = users.id "
            + "join libraries on users_libraries.library_id  = libraries.id "
            + "where users.id = :userId", nativeQuery = true)
    List<Library> getLibrariesByUserId(@Param("userId") Long userId);


    List<Library> findByIdIn(List<Long> ids);
}
