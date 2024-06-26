package uz.pdp.animalshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.animalshop.entity.Attachment;

import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
    @Modifying
    @Query(value = "update attachment a set a.is_deleted = true where a.id = ?1",nativeQuery = true)
    void deleteById(UUID id);
}