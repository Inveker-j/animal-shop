package uz.pdp.animalshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.animalshop.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {

    @Query(value = """
            select * from message
            where from_id = :fromId and to_id = :toId and is_deleted_from = false
            or from_id = :toId and to_id = :fromId and is_deleted_to = false
            order by created_at
            """, nativeQuery = true)
    List<Message> getHistory(UUID fromId, UUID toId);

    @Modifying
    @Query(value = "update message set is_deleted_from = true where to_id = :toId", nativeQuery = true)
    void deleteChat(UUID toId);
}