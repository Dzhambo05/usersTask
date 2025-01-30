package ru.test.task.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.test.task.entities.User;
import ru.test.task.entities.UserDto;
import ru.test.task.servises.UserServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    //Добавление нового клиента
    @PostMapping("/putUser")
    public User putUser(@RequestBody UserDto user) {
        return userServiceImpl.putUser(user);
    }
    //Добавление нового контакта клиента
    @PostMapping("/setContact/{id}")
    public User setContact(@PathVariable UUID id, String contact) {
        return userServiceImpl.setContact(id, contact);
    }
    //Получение списка клиентов
    @GetMapping("/allUsers")
    public List<User> allUsers() {
        return userServiceImpl.getAllUsers();
    }
    //Получение информации по заданному клиенту
    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable UUID id) {
        return userServiceImpl.getUser(id);
    }
    //Получение списка контактов заданного клиента
    @GetMapping("/getContacts/{id}")
    public List<String> getContacts(@PathVariable UUID id) {
        return userServiceImpl.getContacts(id);
    }
    //Получение списка контактов заданного типа
    @GetMapping("/getDefiniteContacts/{id}/{contact}")
    public List<String> getDefiniteContacts(@PathVariable UUID id, @PathVariable String contact) {
        return userServiceImpl.getDefiniteContacts(id, contact);
    }

}
