package ru.kphu.bot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kphu.bot.model.Animal;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal,Integer> {
    Animal findAnimalByName(String name);
    Optional<Animal> getAnimalByName(String name);
    List<Animal> findAnimalByIdUser(String id);
}
