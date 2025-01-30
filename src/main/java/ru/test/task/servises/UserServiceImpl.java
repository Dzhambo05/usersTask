package ru.test.task.servises;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.test.task.entities.User;
import ru.test.task.entities.UserDto;
import ru.test.task.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService  {
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    public static final String EMAIL_REGEX = "^[\\w-\\.]+@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,}$";
    public static final String PHONE_REGEX = "^(\\d{3}[- .]?){2}\\d{4}$";
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User putUser(UserDto userDto) {
        if (userDto != null && validContact(userDto)) {
            User user = new User();
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setPhoneNumber(userDto.getPhoneNumber());
            return userRepository.save(user);
        } else {
            logger.error("Error in putUser: {}", userDto);
            throw new IllegalArgumentException("Exception into putUser method");
        }
    }

    public User getUser(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            logger.error("User is not find by id: {}", id);
            throw new IllegalArgumentException("Exception into getUser method");
        }
    }

    @Transactional
    public User setContact(UUID id, String contact) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (contact.matches(EMAIL_REGEX)) {
                user.getEmail().add(contact);
                userRepository.save(user);
            } else if (contact.matches(PHONE_REGEX)){
                user.getPhoneNumber().add(contact);
                userRepository.save(user);
            } else {
                throw new IllegalArgumentException("Exception into setContact method");
            }
            return user;
        } else {
            throw new IllegalArgumentException("Exception into setContact method");
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
            logger.error("User is not find by id: {}", id);
            throw new IllegalArgumentException("Exception into getContacts method");
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
            } else {
                logger.error("User is not present with id: {}", id);
                logger.error("Or user has not valid contact: {}", contact);
                throw new IllegalArgumentException("Exception into getDefiniteContacts method");
            }
            return contacts;
        }
        //Поставлен на случай, если метод отработает неправильно
        //Либо иное, хаотичное поведение.
        return null;
    }

    private boolean validContact(UserDto user) {
        logger.info("Validation user with phoneNumber {} and email {}", user.getPhoneNumber(), user.getEmail());
        return user.getEmail().stream().allMatch(x -> x.matches(EMAIL_REGEX))
                && user.getPhoneNumber().stream().allMatch(x -> x.matches(PHONE_REGEX));
    }
    private boolean validContact(User user) {
        logger.info("Validation user with phoneNumber {} and email {}", user.getPhoneNumber(), user.getEmail());
        return user.getEmail().stream().allMatch(x -> x.matches(EMAIL_REGEX))
                && user.getPhoneNumber().stream().allMatch(x -> x.matches(PHONE_REGEX));
    }
}
