package uz.pdp.animalshop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Post extends BaseEntity {
    private String description;
    private String phone;

    //todo add location
    @OneToOne
    private Animal animal;
    @ManyToOne
    private User user;


}
