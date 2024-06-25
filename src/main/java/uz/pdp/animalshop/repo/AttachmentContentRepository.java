package uz.pdp.animalshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.animalshop.entity.AttachmentContent;

import java.util.UUID;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, UUID> {
    @Modifying
    @Query(value = "update attachment_content ac set ac.is_deleted = true where ac.id = ?1",nativeQuery = true)
    void deleteById(UUID id);
}