package uz.pdp.animalshop.repo;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.animalshop.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Modifying
    @Query(value = "update users  set is_deleted = true where id = ?1", nativeQuery = true)
    void deleteById( UUID id);

    @Query(value = """
            select * from users where is_deleted = false
            """, nativeQuery = true)
    List<User> availableUsers();

    @Query(value = "select * from users where is_deleted = false and id = ?1;", nativeQuery = true)
    Optional<User> findAvailableUserByUserId(UUID userId);

    @Query(value = "select * from users where email = ?1 and is_deleted = false", nativeQuery = true)
    Optional<User> findByEmail(String email);
}