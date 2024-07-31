package uz.pdp.animalshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.animalshop.entity.Animal;
import uz.pdp.animalshop.entity.Category;
import uz.pdp.animalshop.entity.Post;
import uz.pdp.animalshop.entity.User;
import uz.pdp.animalshop.entity.enums.Gander;
import uz.pdp.animalshop.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ImageService imageService;
    private final AnimalService animalService;
    private static Integer i = 0;
    private final FavouritePostService favouritePostService;

    @PostMapping("/save-post")
    public ResponseEntity<?> savePost(@RequestParam("userId") UUID userId,
                                      @RequestParam("description") String description,
                                      @RequestParam("title") String title,
                                      @RequestParam("phone") String phone,
                                      @RequestParam("animalName") String animalName,
                                      @RequestParam("categoryId") UUID categoryId,
                                      @RequestParam("gender") String gender,
                                      @RequestParam("images") List<MultipartFile> images) {


        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Optional<Category> optionalCategory = categoryService.findById(categoryId);
        if (optionalCategory.isEmpty()) {
            return ResponseEntity.badRequest().body("Category not found");
        }


        Animal animal = new Animal();
        animal.setName(animalName);
        animal.setCategory(optionalCategory.get());
        animal.setGander(Gander.valueOf(gender));

        List<String> imageUrl = new ArrayList<>();


        Post post = new Post();
        post.setUser(optionalUser.get());
        post.setDescription(description);
        post.setTitle(title);
        post.setPhone(phone);
        post.setAnimal(animal);

        postService.save(post);

        int counter = 1;
        for (MultipartFile image : images) {

            try {

                ResponseEntity<?> responseEntity = imageService.saveImage(image, post, counter++);
                String urlImage =(String) responseEntity.getBody();
                imageUrl.add(urlImage);
                post.setImagesUrls(imageUrl);
                postService.save(post);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }

        }

        return ResponseEntity.ok(post);
    }


    @GetMapping("get-all")
    public ResponseEntity<?> getAllPosts(@RequestParam(value = "size", defaultValue = "10") int size) {
        int page = i++;
        Page<Post> paginationPost = postService.getPagination(page, size);

        try {
            return ResponseEntity.ok(paginationPost);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Have a problem with PostDTOs, cannot get all the posts");
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deletePost(@RequestParam UUID postId) {

        Optional<Post> postOptional = postService.findById(postId);

        if (postId != null && postOptional.isPresent()) {
            animalService.deleteById(postOptional.get().getAnimal().getId());
            postService.delete(postId);
            return ResponseEntity.ok("Post deleted successfully");
        }
        return ResponseEntity.badRequest().build();
    }


}