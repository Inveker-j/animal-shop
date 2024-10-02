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
public class AnimalService {
    private final AnimalRepository animalRepository;


    public Animal save(Animal animal) {
        return animalRepository.save(animal);
    }

    public void deleteById(UUID uuid) {
        animalRepository.deleteById(uuid);
    }

}
