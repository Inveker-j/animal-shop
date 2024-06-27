package uz.pdp.animalshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.animalshop.entity.AttachmentContent;

import java.util.Optional;
import java.util.UUID;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, UUID> {
    @Modifying
    @Query(value = "update attachment_content set is_delete = true where id = ?1",nativeQuery = true)
    void deleteById(UUID id);

    Optional<AttachmentContent> findAttachmentContentByAttachmentId(UUID id);
}