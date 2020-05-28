package ru.kphu.bot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kphu.bot.model.Game;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findById(long id);
}