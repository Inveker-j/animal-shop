package uz.pdp.animalshop.repo;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.animalshop.entity.Post;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    @Modifying
    @Query(value = "update post p set is_deleted = true where p.id = ?1", nativeQuery = true)
    void deleteById(@NonNull UUID id);

    Page<Post> findByIsDeletedFalse(Pageable pageable);
}