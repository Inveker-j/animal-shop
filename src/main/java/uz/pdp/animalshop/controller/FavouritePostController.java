package uz.pdp.animalshop.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.animalshop.dto.FavouritePostDTO;
import uz.pdp.animalshop.entity.FavouritePost;
import uz.pdp.animalshop.entity.Post;
import uz.pdp.animalshop.entity.User;
import uz.pdp.animalshop.service.FavouritePostService;
import uz.pdp.animalshop.service.PostService;
import uz.pdp.animalshop.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favourite-post")
public class FavouritePostController {
    private final FavouritePostService favouritePostService;
    private final UserService userService;
    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> favouritePost(@RequestBody FavouritePostDTO favouritePostDTO) {

        Optional<User> optionalUser =
                userService.findById(favouritePostDTO.getUserId());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }
        User user = optionalUser.get();

        Optional<Post> optional = postService.findById(favouritePostDTO.getPostId());

        if (optional.isEmpty()) {
            return ResponseEntity.badRequest().body("Post not found");
        }
        Post post = optional.get();



        Optional<FavouritePost> optionalFavouritePost =
                favouritePostService.findByUserId(favouritePostDTO.getUserId());
        if (optionalFavouritePost.isEmpty()) {
            FavouritePost favouritePost = new FavouritePost();
            favouritePost.setUser(user);
            favouritePost.setPosts(new ArrayList<>(){{add(post);}});
            favouritePostService.save(favouritePost);
            return ResponseEntity.ok().build();
        }

        FavouritePost favouritePost = optionalFavouritePost.get();

        List<Post> posts = favouritePost.getPosts();
        posts.add(post);
        favouritePost.setPosts(posts);
        favouritePostService.save(favouritePost);


        return ResponseEntity.ok().body(favouritePostDTO);
    }
}
