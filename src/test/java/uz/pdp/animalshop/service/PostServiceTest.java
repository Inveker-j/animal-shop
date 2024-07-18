package uz.pdp.animalshop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import uz.pdp.animalshop.entity.Post;
import uz.pdp.animalshop.repo.PostRepository;

import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PostServiceTest {
    private PostService postService;
    private PostRepository postRepository;


    @BeforeEach
    void setUp() {
        this.postRepository = Mockito.mock(PostRepository.class);
        this.postService = new PostService(postRepository);
    }


    @Test
    void findById() {
        Post post = new Post();
        post.setId(UUID.randomUUID());

        Mockito.when(postRepository.save(post)).thenReturn(post);

        Mockito.when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        Optional<Post> optional = postService.findById(post.getId());
        Assertions.assertTrue(optional.isPresent());


    }

    @Test
    void save() {
        Post post = new Post();
        post.setId(UUID.randomUUID());

        Mockito.when(postRepository.save(post)).thenReturn(post);
        Mockito.when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        ResponseEntity<?> saved = postService.save(post);
        Assertions.assertNotNull(saved);

        Assertions.assertEquals(200, saved.getStatusCodeValue());
    }

    @Test
    void delete() {
        Post post = new Post();
        post.setId(UUID.randomUUID());

        Mockito.when(postRepository.save(post)).thenReturn(post);
        Post save = postRepository.save(post);

        postService.delete(save.getId());

        Mockito.when(postRepository.findById(post.getId())).thenReturn(Optional.empty());
        Optional<Post> optional = postRepository.findById(post.getId());
        assertTrue(optional.isEmpty());
    }



        @Test
    void getPagination() {
        int page = 0;
        int size = 10;
            Post post = new Post();
            post.setId(UUID.randomUUID());


        Pageable pageable = PageRequest.of(page, size);
        List<Post> posts = Collections.singletonList(post);
        Page<Post> postPage = new PageImpl<>(posts, pageable, posts.size());

        Mockito.when(postRepository.findByIsDeletedFalse(pageable)).thenReturn(postPage);

        Page<Post> result = postService.getPagination(page, size);

        Mockito.verify(postRepository, Mockito.times(1)).findByIsDeletedFalse(pageable);


        assertEquals(result.getSize(),postPage.getSize());
//        assertThat(result.getContent()).hasSize(1);
        assertEquals(result.getContent().get(0).getId(),post.getId());


    }


}