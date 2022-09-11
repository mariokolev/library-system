package bg.tu.varna.informationSystem.repository;

import bg.tu.varna.informationSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select permissions.permission_name  from roles_permissions "
            + " join permissions on roles_permissions.permission_id  = permissions.id "
            + " join roles on roles_permissions.role_id = roles.id "
            + " join users on roles.id = users.role_id where users.username = :username ", nativeQuery = true)
    List<String> getUserPermissions(@Param("username") String username);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query(value = "select u.* from users u " +
            "inner join roles r on u.role_id = r.id " +
            "where u.id = :id and r.role_name = :role_name"
            , nativeQuery = true)
    Optional<User> findById(@Param("id") Long id, @Param("role_name") String roleName);

    @Query(value = "select u.* from users u " +
            "inner join roles r on u.role_id = r.id " +
            "where r.role_name = :role_name", nativeQuery = true)
    List<User> findAllByRole(@Param("role_name") String roleName);
    @Query(value = "SELECT u FROM User u WHERE u.id <> :id")
    List<User> findAll(@Param("id") Long id);

    @Query(value = "SELECT u FROM User u WHERE u.isActive = :isActive")
    List<User> findAll(@Param("isActive") Boolean isActive);

    @Query(value = "select u.* from users u " +
            "inner join roles r on u.role_id = r.id " +
            "where r.role_name = :role_name and u.active = :isActive", nativeQuery = true)
    List<User> findAll(@Param("role_name") String roleName, @Param("isActive") Boolean isActive);
}
