package uz.pdp.animalshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.animalshop.entity.Post;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final String IMAGE_DIRECTORY = "images";

    public String saveImage(MultipartFile image, Post post, int i) throws IOException {
        File rootDirectory = new File(ResourceUtils.getURL("classpath:").getPath()).getAbsoluteFile();
        File imageDirectory = new File(rootDirectory, IMAGE_DIRECTORY);

        if (!imageDirectory.exists()) {
            imageDirectory.mkdirs();
        }



        String filename = post.getId() + "_" + i;
        Path filepath = Paths.get(imageDirectory.getAbsolutePath(), filename);


        // Save the image to the file system
         return Files.write(filepath, image.getBytes()).toString();

    }
}
