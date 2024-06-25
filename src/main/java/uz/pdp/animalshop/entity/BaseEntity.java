package uz.pdp.animalshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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
