package uz.pdp.animalshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.animalshop.entity.Animal;
import uz.pdp.animalshop.repo.AnimalRepository;
import uz.pdp.animalshop.service.interfaces.BaseService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnimalService implements BaseService<Animal, UUID> {
    private final AnimalRepository animalRepository;

    @Override
    public List<Animal> findAll() {
        return animalRepository.findAll();
    }

    @Override
    public Optional<Animal> findById(UUID id) {
        return animalRepository.findById(id);
    }

    @Override
    public Animal save(Animal animal) {
        return animalRepository.save(animal);
    }

    @Override
    public void delete(UUID uuid) {
        animalRepository.deleteById(uuid);
    }
}
