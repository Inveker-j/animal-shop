package uz.pdp.animalshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.animalshop.entity.Animal;

import java.util.UUID;

public interface AnimalRepository extends JpaRepository<Animal, UUID> {
    @Modifying
    @Query(value = "update animal a set a.is_deleted = true where a.id = ?1",nativeQuery = true)
    void deleteById(UUID animalId);
}