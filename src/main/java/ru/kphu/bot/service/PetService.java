package ru.kphu.bot.service;

import ru.kphu.bot.dto.AnimalDto;
import ru.kphu.bot.model.Animal;

import java.util.List;

public interface PetService {
    Animal addAnimal(AnimalDto animal);
    List<AnimalDto> getAllAnimals();
    AnimalDto getAnimalByName(String  animal);
}
