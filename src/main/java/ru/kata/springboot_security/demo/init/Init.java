package ru.kata.springboot_security.demo.init;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.springboot_security.demo.models.Role;
import ru.kata.springboot_security.demo.models.User;
import ru.kata.springboot_security.demo.repository.RoleRepository;
import ru.kata.springboot_security.demo.repository.UserRepository;

import static java.util.Collections.singleton;

@Component
@AllArgsConstructor
public class Init {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder encoder;

    @PostConstruct
    private void init() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);

        User admin = new User("admin", encoder.encode("123"), "admin@mail.ru", singleton(roleAdmin));
        User user1 = new User("user",encoder.encode("345"), "user@mail.ru", singleton(roleUser));

        userRepository.save(admin);
        userRepository.save(user1);
    }
}
