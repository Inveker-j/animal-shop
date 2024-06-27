package uz.pdp.animalshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.animalshop.dto.UserDto;
import uz.pdp.animalshop.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Modifying
    @Query(value = "update users  set is_delete = true where id = ?1", nativeQuery = true)
    void deleteById(UUID id);

    @Query(value = """
            select * from users where is_delete = false
            """, nativeQuery = true)
    List<User> availableUsers();

    @Query(value = "select * from users where is_delete = false and id = ?1;", nativeQuery = true)
    Optional<User> findAvailableUserByUserId(UUID userId);

    User findByEmail(String email);
}