package uz.pdp.animalshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.animalshop.entity.Post;
import uz.pdp.animalshop.entity.User;
import uz.pdp.animalshop.entity.UserFavouritePosts;

import java.util.List;
import java.util.UUID;

public interface UserFavouritePostsRepository extends JpaRepository<UserFavouritePosts, UUID> {

    List<Post> findPostsByUser(User user);
}