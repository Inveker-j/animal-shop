package uz.pdp.animalshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.animalshop.entity.enums.Gander;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Animal extends BaseEntity {
    private String name;
    @ManyToOne
    private Category category;
    private Gander gander;
    @OneToMany
    private List<Attachment> attachments;


}
