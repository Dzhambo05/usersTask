package ru.test.task.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.test.task.entities.User;
import ru.test.task.servises.UserService;

import java.util.List;
import java.util.UUID;

@RestController
public class MainController {

    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    //Добавление нового клиента
    @PostMapping("/putUser")
    public User putUser(@RequestBody User user) {
        return userService.putUser(user);
    }
    //Добавление нового контакта клиента
    @PostMapping("/setContact/{id}")
    public User setContact(@PathVariable UUID id, String contact) {
        return userService.setContact(id, contact);
    }
    //Получение списка клиентов
    @GetMapping("/allUsers")
    public List<User> allUsers() {
        return userService.getAllUsers();
    }
    //Получение информации по заданному клиенту
    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable UUID id) {
        return userService.getUser(id);
    }
    //Получение списка контактов заданного клиента
    @GetMapping("/getContacts/{id}")
    public List<String> getContacts(@PathVariable UUID id) {
        return userService.getContacts(id);
    }
    //Получение списка контактов заданного типа
    @GetMapping("/getDefiniteContacts/{id}/{contact}")
    public List<String> getDefiniteContacts(@PathVariable UUID id, @PathVariable String contact) {
        return userService.getDefiniteContacts(id, contact);
    }

}
