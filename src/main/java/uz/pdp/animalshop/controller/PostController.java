package uz.pdp.animalshop.controller;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.antlr.v4.runtime.atn.SemanticContext.Empty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.animalshop.dto.PostDTO;
import uz.pdp.animalshop.dto.PostForm;
import uz.pdp.animalshop.entity.Animal;
import uz.pdp.animalshop.entity.Category;
import uz.pdp.animalshop.entity.Post;
import uz.pdp.animalshop.entity.User;
import uz.pdp.animalshop.entity.enums.Gander;
import uz.pdp.animalshop.service.*;

import java.io.IOException;
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
    private final AnimalService animalService;
    private final CategoryService categoryService;
    private final ImageService imageService;

//    @PostMapping("/save-post")
////    public ResponseEntity<?> savePost(@RequestBody PostDTO postDTO, @RequestParam List<MultipartFile> images) throws IOException {
//    public ResponseEntity<?> savePost(@RequestBody PostDTO postDTO,@RequestParam List<MultipartFile> images) throws IOException {
//
////        List<MultipartFile> images = postDTO.getImages();
//
//
//        if (images.isEmpty()) {
//            return ResponseEntity.badRequest().body("Image is empty");
//        }
//        System.out.println("keldi");
//        System.out.println("postDTO = " + postDTO);
//
//        Optional<Category> optionalCategory = categoryService.findById(postDTO.getCategoryId());
//        if (optionalCategory.isEmpty()) {
//            return ResponseEntity.badRequest().body("Category not found");
//        }
//
//        Category category = optionalCategory.get();
//        Animal animal = new Animal();
//        animal.setCategory(category);
//        animal.setName(postDTO.getAnimalName());
//        animal.setGander(Gander.valueOf(postDTO.getGender()));
//
//        animalService.save(animal);
//
//        Optional<User> optionalUser = userService.findById(postDTO.getUserId());
//        if (optionalUser.isEmpty()) {
//            return ResponseEntity.badRequest().body("User not found");
//        }
//
//        User user = optionalUser.get();
//
//        Post post = new Post();
//        post.setUser(user);
//        post.setAnimal(animal);
//        post.setDescription(postDTO.getDescription());
//        post.setTitle(postDTO.getTitle());
//        post.setPhone(postDTO.getPhone());
//
//
//        postService.save(post);
//
//        List<String> imageUrls = new ArrayList<>();
//
//        int i = 0;
//
//        for (MultipartFile image : images) {
//            try {
//                String imageUrl = imageService.saveImage(image, post, ++i);
//                imageUrls.add(imageUrl);
//
//            } catch (Exception e) {
//                return ResponseEntity.badRequest().body("Error saving image" + image.getOriginalFilename());
//            }
//        }
//        post.setImagesUrls(imageUrls);
//
//        postService.save(post);
//
//        return ResponseEntity.ok(post);
//
//
////        return ResponseEntity.badRequest().body("Error saving the post, check the entered information");
//    }


    @PostMapping("/save-post")
    public ResponseEntity<?> savePost(@RequestParam("userId") UUID userId,
                                      @RequestParam("description") String description,
                                      @RequestParam("title") String title,
                                      @RequestParam("phone") String phone,
                                      @RequestParam("animalName") String animalName,
                                      @RequestParam("categoryId") UUID categoryId,
                                      @RequestParam("gender") String gender,
                                      @RequestParam("images") List<MultipartFile> images) {

        for (MultipartFile image : images) {
            System.out.println("image.getOriginalFilename() = " + image.getOriginalFilename());
        }

        // Handle saving the post using the received parameters and multipart files

        // Example: persist the data to your database

        // Return a response entity indicating success or failure
        return ResponseEntity.ok("Post saved successfully");
    }


    @GetMapping("get-all")
    public ResponseEntity<?> getAllPosts() {
        List<Post> posts = postService.findAll();

        try {
            List<PostDTO> postDTOList = toPostDTO(posts);
            return ResponseEntity.ok(postDTOList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Have a problem with PostDTOs, cannot get all the posts");
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deletePost(@RequestParam UUID postId) {
        try {
            postService.delete(postId);
            return ResponseEntity.ok().body("Post deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Have a problem with postId " + postId + ", cannot delete the post");
        }
    }


    public static List<PostDTO> toPostDTO(List<Post> posts) {
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : posts) {
            PostDTO postDTO = PostDTO.builder()
                    .userId(post.getUser() != null ? post.getUser().getId() : null)
                    .description(post.getDescription())
                    .title(post.getTitle())
                    .phone(post.getPhone())
                    .animalName(post.getAnimal() != null ? post.getAnimal().getName() : null)
                    .categoryId(post.getAnimal() != null && post.getAnimal().getCategory() != null ? post.getAnimal().getCategory().getId() : null)
                    .gender(post.getAnimal() != null && post.getAnimal().getGander() != null ? post.getAnimal().getGander().name() : null)
                    .imagesUrls(post.getImagesUrls())
                    .build();

            postDTOs.add(postDTO);
        }
        return postDTOs;
    }


}