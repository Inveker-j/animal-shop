package uz.pdp.animalshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Post extends BaseEntity {
    private String title;
    private String description;
    private String phone;

    //TODO add location
    @OneToOne(cascade = CascadeType.ALL)
    private Animal animal;
    @ManyToOne
    private User user;

    @ElementCollection
    private List<String> imagesUrls;

}
