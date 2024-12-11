package ru.kata.springboot_security.demo.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.springboot_security.demo.models.Role;
import ru.kata.springboot_security.demo.models.User;
import ru.kata.springboot_security.demo.repository.RoleRepository;
import ru.kata.springboot_security.demo.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void saveUser(User user) {
        if (user.getRoles() == null) {
            Role roleUser = roleRepository.findByName("ROLE_USER");
            user.setRoles(singleton(roleUser));
        } else {
            user.setRoles(user.getRoles().stream()
                    .map(role -> roleRepository.findById(role.getId()).orElseThrow())
                    .collect(toSet()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void updateUser(Long id,
                           String username,
                           String email,
                           String password,
                           Set<Role> roles) {
        User maybeUser = getUserById(id)
                .orElseThrow();
        maybeUser.setUsername(username);
        maybeUser.setPassword(passwordEncoder.encode(password));
        maybeUser.setEmail(email);
        maybeUser.setCreateDate(maybeUser.getCreateDate());
        maybeUser.setRoles(roles);
        userRepository.save(maybeUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(toList());
    }
}
