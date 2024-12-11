package ru.kata.springboot_security.demo.services;

import ru.kata.springboot_security.demo.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
}
