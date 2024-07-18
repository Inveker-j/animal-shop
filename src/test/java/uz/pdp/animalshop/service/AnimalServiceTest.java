package uz.pdp.animalshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import uz.pdp.animalshop.entity.Animal;
import uz.pdp.animalshop.entity.User;
import uz.pdp.animalshop.entity.enums.Gander;
import uz.pdp.animalshop.repo.AnimalRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;

class AnimalServiceTest {

    private AnimalService animalService;
    private AnimalRepository animalRepository;

    @BeforeEach
    public void setUp() {
        this.animalRepository = Mockito.mock(AnimalRepository.class);
        this.animalService = new AnimalService(animalRepository);
    }

    @Test
    void save() {
        Animal animal = new Animal();
        animal.setId(UUID.randomUUID());
        animal.setName("Xo'roz");
        animal.setGander(Gander.FEMALE);
        animal.setIsDeleted(false);

        Mockito.when(animalRepository.save(animal)).thenReturn(animal);
        Animal savedAnimal = animalService.save(animal);

        assertNotNull(savedAnimal);
        assertEquals(animal.getName(), savedAnimal.getName());
    }

    @Test
    void deleteById() {
        Animal animal = new Animal();
        animal.setId(UUID.randomUUID());
        animal.setName("Xo'roz");
        animal.setGander(Gander.FEMALE);
        animal.setIsDeleted(false);

        Mockito.when(animalRepository.save(animal)).thenReturn(animal);
        animalRepository.save(animal);



        Mockito.when(animalRepository.findById(animal.getId())).thenReturn(Optional.of(animal));
        Optional<Animal> savedAnimal = animalRepository.findById(animal.getId());
        assertThat(savedAnimal).isPresent();


        animalService.deleteById(animal.getId());

        Mockito.when(animalRepository.findById(animal.getId())).thenReturn(Optional.empty());
        Optional<Animal> deletedAnimal = animalRepository.findById(animal.getId());
        assertThat(deletedAnimal).isEmpty();

    }

}
