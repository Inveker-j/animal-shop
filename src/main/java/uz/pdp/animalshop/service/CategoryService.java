package uz.pdp.animalshop.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.animalshop.entity.Category;
import uz.pdp.animalshop.repo.CategoryRepository;
import uz.pdp.animalshop.service.interfaces.BaseService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(UUID id) {

        return categoryRepository.findById(id);
    }

    public ResponseEntity<?> save(Category category) {

        if (category.getName().isBlank()) {
            return ResponseEntity.badRequest().body("Name cannot be empty");
        }
        return ResponseEntity.ok(categoryRepository.save(category));
    }
}
