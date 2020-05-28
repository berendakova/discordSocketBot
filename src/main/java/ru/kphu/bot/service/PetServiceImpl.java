package ru.kphu.bot.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import ru.kphu.bot.dto.AnimalDto;
import ru.kphu.bot.model.Animal;
import ru.kphu.bot.repositories.AnimalRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PetServiceImpl implements PetService {
    @Autowired
    AnimalRepository animalRepository;
    @Override
    public Animal addAnimal(AnimalDto animalDto) {
           Animal animal = Animal.builder()
                   .name(animalDto.getName())
                   .jpg(animalDto.getJpg())
                   .status(0)
                   .idUser("0")
                   .build();
           animalRepository.save(animal);
           return animal;
    }
    @Override
    public List<AnimalDto> getAllAnimals() {
        return AnimalDto.from(animalRepository.findAll());
    }

    @Override
    public AnimalDto getAnimalByName(String animal) {
        return AnimalDto.from(animalRepository.findAnimalByName(animal));
    }
}
