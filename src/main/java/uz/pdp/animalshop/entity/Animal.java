package uz.pdp.animalshop.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.animalshop.entity.enums.Gander;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
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
}
