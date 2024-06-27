package uz.pdp.animalshop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
public class UserFavouritePosts extends BaseEntity {
    @ManyToOne
    private User user;
    @ManyToMany
    private List<Post> post;
}
