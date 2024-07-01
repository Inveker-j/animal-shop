package uz.pdp.animalshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Message {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid default uuid_generate_v4()")
    private UUID id;
    @ManyToOne
    private User from;
    @ManyToOne
    private User to;
    private String message;
    @Column(columnDefinition = "boolean default false")
    private Boolean isDeletedFrom;
    @Column(columnDefinition = "boolean default false")
    private Boolean isDeletedTo;
    @CreationTimestamp
    private LocalDateTime createdAt;


}
