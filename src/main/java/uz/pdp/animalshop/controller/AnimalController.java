package uz.pdp.animalshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.animalshop.dto.AnimalDTO;
import uz.pdp.animalshop.entity.Animal;
import uz.pdp.animalshop.entity.Category;
import uz.pdp.animalshop.entity.enums.Gander;
import uz.pdp.animalshop.service.AnimalService;
import uz.pdp.animalshop.service.CategoryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/animal")
@RequiredArgsConstructor
public class AnimalController {
    private final AnimalService animalService;
    private final CategoryService categoryService;

    @GetMapping("/settings/get-animals")
    public ResponseEntity<?> findAllAnimals() {
        List<Animal> availableAnimals = animalService.findAnailableAnimals();
        List<AnimalDTO> animalDTOS = new ArrayList<>();
        for (Animal animal : availableAnimals) {
            AnimalDTO animalDTO = new AnimalDTO();
            animalDTO.setId(animal.getId());
            animalDTO.setCategoryName(animal.getCategory().getName());
            animalDTO.setGander(animal.getGander());
            animalDTOS.add(animalDTO);
        }
        return ResponseEntity.ok().body(animalDTOS);
    }

    @PostMapping("/settings/save-animal")
    public ResponseEntity<?> saveAnimal(@RequestBody Animal animal,
                                        @RequestParam UUID categoryId,
                                        @RequestParam String gander) {

        Optional<Category> optionalCategory = categoryService.findById(categoryId);

        AnimalDTO animalDTO = new AnimalDTO();

        try {
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                animalDTO.setCategoryName(category.getName());

                animal.setCategory(category);
                animal.setGander(Gander.valueOf(gander));
                animalDTO.setGander(Gander.valueOf(gander));
            }

            animalService.save(animal);

        } catch (Exception e) {
            return ResponseEntity.status(RuntimeException.class.getModifiers()).build();
        }
        animalDTO.setName(animal.getName());
        animalDTO.setId(animal.getId());

        return ResponseEntity.ok().body(animalDTO);
    }

    @PutMapping("/settings/edit-animal")
    public ResponseEntity<?> editAnimal(@RequestBody Animal animal,
                                        @RequestParam UUID animalId,
                                        @RequestParam String gander,
                                        @RequestParam UUID categoryId) {

        Optional<Animal> optionalAnimal = animalService.findById(animalId);
        Optional<Category> optionalCategory = categoryService.findById(categoryId);

        AnimalDTO animalDTO = new AnimalDTO();
        animalDTO.setName(animal.getName());
        animalDTO.setGander(Gander.valueOf(gander));

        if (optionalAnimal.isPresent()) {
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                animal.setCategory(category);
                animalDTO.setCategoryName(category.getName());
                Animal animal1 = optionalAnimal.get();
                animal.setId(animal1.getId());

                animalService.save(animal);
            }

        }
        return ResponseEntity.ok().body(animalDTO);
    }

}
