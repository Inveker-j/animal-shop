package uz.pdp.animalshop.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import uz.pdp.animalshop.entity.Post;
import uz.pdp.animalshop.entity.User;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.*;

class ImageServiceTest {

    private ImageService imageService;
    private Post post;
    private User user;
    @BeforeEach
    void setUp() {
        imageService = new ImageService();

        post = new Post();
        post.setId(UUID.randomUUID());

        user = new User();
        user.setId(UUID.randomUUID());
    }

    @Test
    public void saveImageWithPost() {
        MockMultipartFile image = new MockMultipartFile("image", "image.jpg",  "image/jpeg", "test image content".getBytes());

        String path = imageService.saveImage(image, post, 1);

        File imageDirectory = new File("images");
        assertThat(imageDirectory.exists()).isTrue();

        Path filepath = Paths.get(imageDirectory.getAbsolutePath(), post.getId() + "_1.jpg");
        assertThat(Files.exists(filepath)).isTrue();

//        assertThat(path).isEqualTo(filepath.toString());
        assertEquals(path,filepath.toString());
    }

    @Test
    public void saveImageWithUser() {
        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "test image content".getBytes());

        String path = imageService.saveImage(image, user, 1);

        File imageDirectory = new File("images");
        assertThat(imageDirectory.exists()).isTrue();


        Path filepath = Paths.get(imageDirectory.getAbsolutePath(), user.getId() + "_1.jpg");
        assertThat(Files.exists(filepath)).isTrue();


        assertThat(path).isEqualTo(filepath.toString());

    }


}