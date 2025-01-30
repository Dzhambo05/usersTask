package ru.test.task.servises;

import ru.test.task.entities.User;
import ru.test.task.entities.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<User> getAllUsers();
    User putUser(UserDto user);
    User getUser(UUID id);
    User setContact(UUID id, String contact);
    List<String> getContacts(UUID id);
    List<String> getDefiniteContacts(UUID id, String contact);

}
