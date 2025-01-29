package ru.test.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.test.task.entities.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
