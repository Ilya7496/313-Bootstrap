package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

@Component
public interface UserService {

    List<User> getAllUsers();

    User getUser(long id);

    void deleteUser(long id);

    public void addNewUser(User user);

    public void edit(User user);
}
