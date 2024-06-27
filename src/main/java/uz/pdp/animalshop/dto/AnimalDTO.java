package uz.pdp.animalshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.animalshop.entity.enums.Gander;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AnimalDTO {
    private UUID id;
    private String name;
    private String categoryName;
    private Gander gander;
    private List<byte[]> image;
}
