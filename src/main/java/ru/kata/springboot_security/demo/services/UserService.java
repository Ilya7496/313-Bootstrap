package ru.kata.springboot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.springboot_security.demo.models.Role;
import ru.kata.springboot_security.demo.models.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    Optional<User> getUserByUsername(String username);

    void saveUser(User user);

    void updateUser(Long id,
                    String username,
                    String email,
                    String password,
                    Set<Role> roles);

    void deleteUser(Long id);
}
