package uz.pdp.animalshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.animalshop.entity.Animal;
import uz.pdp.animalshop.entity.User;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {
    public UUID id;
    private String description;
    private String phone;
    private User user;

}
