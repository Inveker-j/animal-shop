package uz.pdp.animalshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostForm {
    private PostDTO postDTO;
    private List<MultipartFile> images;
}