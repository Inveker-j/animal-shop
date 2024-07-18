//package uz.pdp.animalshop.service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.SneakyThrows;
//import org.springframework.stereotype.Service;
//import org.springframework.util.ResourceUtils;
//import org.springframework.web.multipart.MultipartFile;
//import uz.pdp.animalshop.entity.Post;
//import uz.pdp.animalshop.entity.User;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class ImageService {
//    private static final String IMAGE_DIRECTORY = "images";
//
//    @SneakyThrows
//    public String saveImage(MultipartFile image, Object entity, int i) {
//        File rootDirectory = new File(ResourceUtils.getURL("classpath:").getPath()).getAbsoluteFile();
//        File imageDirectory = new File(rootDirectory, IMAGE_DIRECTORY);
//
//        if (imageDirectory.exists()) {
//            imageDirectory.mkdirs();
//        }
//
//
//        UUID id = null;
//
//        if (entity instanceof Post) {
//            id = ((Post) entity).getId();
//        } else if (entity instanceof User) {
//            id = ((User) entity).getId();
//        }
//
//        String filename = id + "_" + i+".jpg";
//        Path filepath = Paths.get(imageDirectory.getAbsolutePath(), filename);
//
//        return Files.write(filepath, image.getBytes()).toString();
//
//    }
//}
package uz.pdp.animalshop.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.animalshop.entity.Post;
import uz.pdp.animalshop.entity.User;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private static final String IMAGE_DIRECTORY = "images";

    @SneakyThrows
    public String saveImage(MultipartFile image, Object entity, int i) {
        File imageDirectory = new File(IMAGE_DIRECTORY);

        if (!imageDirectory.exists()) {
            imageDirectory.mkdirs();
        }

        UUID id = null;
        if (entity instanceof Post) {
            id = ((Post) entity).getId();
        } else if (entity instanceof User) {
            id = ((User) entity).getId();
        }

        String filename = id + "_" + i + ".jpg";
        Path filepath = Paths.get(imageDirectory.getAbsolutePath(), filename);

        return Files.write(filepath, image.getBytes()).toString();
    }

}
