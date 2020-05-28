package ru.kphu.bot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kphu.bot.model.User;

public interface UserRepository extends JpaRepository<User,Integer> {
}
