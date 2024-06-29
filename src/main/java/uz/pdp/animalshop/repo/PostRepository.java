package uz.pdp.animalshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.animalshop.entity.Post;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    @Modifying
    @Query(value = "update post p set is_delete = true where p.id = ?1", nativeQuery = true)
    void deleteById(UUID id);

    @Modifying
    @Query(value = "select * from post where is_delete = false", nativeQuery = true)
    List<Post> findAllIsDeleteTrue();
}