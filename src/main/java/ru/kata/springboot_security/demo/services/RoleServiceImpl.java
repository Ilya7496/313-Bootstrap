package ru.kata.springboot_security.demo.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kata.springboot_security.demo.models.Role;
import ru.kata.springboot_security.demo.repository.RoleRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private RoleRepository repository;

    public List<Role> getAllRoles() {
        return repository.findAll();
    }
}
