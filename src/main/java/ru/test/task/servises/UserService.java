package ru.test.task.servises;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.test.task.entities.User;
import ru.test.task.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User putUser(User user) {
        if (validContact(user)) {
            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public User getUser(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Transactional
    public User setContact(UUID id, String contact) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (contact.matches("^[\\w-\\.]+@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,}$")) {
                user.getEmail().add(contact);
                userRepository.save(user);
            } else if (contact.matches("^(\\d{3}[- .]?){2}\\d{4}$")){
                user.getPhoneNumber().add(contact);
                userRepository.save(user);
            } else {
                throw new IllegalArgumentException();
            }
            return user;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public List<String> getContacts(UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);
        List<String> contacts = new ArrayList<>();
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            contacts.addAll(user.getEmail());
            contacts.addAll(user.getPhoneNumber());
            return contacts;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public List<String> getDefiniteContacts(UUID id, String contact) {
        Optional<User> optionalUser = userRepository.findById(id);
        List<String> contacts = new ArrayList<>();
        if (optionalUser.isPresent() && validContact(optionalUser.get())) {
            User user = optionalUser.get();
            if (contact.equalsIgnoreCase("email")) {
                contacts.addAll(user.getEmail());
            } else if (contact.equalsIgnoreCase("phone")) {
                contacts.addAll(user.getPhoneNumber());
            }
            return contacts;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private boolean validContact(User user) {
        return user.getEmail().stream().allMatch(x -> x.matches("^[\\w-\\.]+@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,}$"))
                && user.getPhoneNumber().stream().allMatch(x -> x.matches("^(\\d{3}[- .]?){2}\\d{4}$"));
    }

}
