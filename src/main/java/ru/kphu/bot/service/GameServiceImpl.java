package ru.kphu.bot.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kphu.bot.model.Animal;
import ru.kphu.bot.model.Game;
import ru.kphu.bot.repositories.AnimalRepository;
import ru.kphu.bot.repositories.GameRepository;

import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {
    @Autowired
    GameRepository repository;
    @Autowired
    AnimalRepository animalRepository;

    @Override
    public Game addGame() {
        Random random = new Random();
        List<Animal> animals = animalRepository.findAll();
        int max_value = animals.size();
        Animal animal = animals.get(random.nextInt(max_value));
        Game game = Game
                .builder()
                .id(0)
                .animal(animal)
                .isFinished(false)
                .build();
        repository.save(game);
        return game;
    }
}
