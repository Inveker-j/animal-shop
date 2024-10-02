package uz.pdp.animalshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Post extends BaseEntity {
    private String title;
    private String description;
    private String phone;
    @OneToOne(cascade = CascadeType.ALL)
    private Animal animal;
    @ManyToOne
    private User user;

    @ElementCollection
    private List<String> imagesUrls;

}
