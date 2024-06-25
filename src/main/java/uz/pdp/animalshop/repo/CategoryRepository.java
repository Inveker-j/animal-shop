package uz.pdp.animalshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.animalshop.entity.Category;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    @Modifying
    @Query(value = "update category c set c.is_deleted = true where c.id = ?1",nativeQuery = true)
    void deleteById(UUID id);
}