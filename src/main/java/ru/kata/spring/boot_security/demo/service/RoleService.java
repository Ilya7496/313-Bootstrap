package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Optional;

@Component
public interface RoleService {
    public List<Role> findAll();

    public Optional<Role> findById(long id);

    void save(Role role);

    Role findByRoleName(String roleAdmin);
}
