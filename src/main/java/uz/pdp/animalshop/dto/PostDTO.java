package uz.pdp.animalshop.dto;

import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private UUID userId;
    private String description;
    private String title;
    private String phone;
    private String animalName;
    private UUID categoryId;

    private String gender;
    @ElementCollection
    private List<String> imagesUrls;
    private List<MultipartFile> files;
}
