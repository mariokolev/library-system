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

    @Query(value = "select "
            + "p.permission_name "
            + "from public.roles r "
            + "inner join users u on u.role_id  = r.id "
            + "left join roles rr on rr.id = r.inherit_role_id "
            + "inner join roles_permissions rp ON r.id = rp.role_id or rr.id = rp.role_id "
            + "inner join permissions p on rp.permission_id = p.id "
            + "where u.username = :username ", nativeQuery = true)
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
}
