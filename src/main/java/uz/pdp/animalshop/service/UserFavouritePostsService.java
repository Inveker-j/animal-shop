package uz.pdp.animalshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.animalshop.entity.Post;
import uz.pdp.animalshop.entity.User;
import uz.pdp.animalshop.entity.UserFavouritePosts;
import uz.pdp.animalshop.repo.UserFavouritePostsRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserFavouritePostsService {
    private final UserFavouritePostsRepository userFavouritePostsRepository;
    private final PostService postService;

    public List<Post> getUserFavouritePosts(User user) {
        return userFavouritePostsRepository.findPostsByUser(user);
    }

    public boolean addPostForUserFavourites(User user, UUID postId) {
//        Optional<Post> optional = postService.findById(postId);
//        if (optional.isPresent()) {
//            Post post = optional.get();
//            userFavouritePostsRepository
//        }
        return true;
    }

}
