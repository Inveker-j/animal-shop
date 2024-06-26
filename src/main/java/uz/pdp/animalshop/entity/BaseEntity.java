package uz.pdp.animalshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue()
    @Column(columnDefinition = "uuid default uuid_generate_v4()")
    private UUID id;
    @Column(columnDefinition = "boolean default false")
    private Boolean isDelete = false;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
