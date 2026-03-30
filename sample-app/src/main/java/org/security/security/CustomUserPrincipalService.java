package org.security.security;

import org.security.entity.User;
import org.security.repository.UserRepository;
import org.security.contract.AuthUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserPrincipalService {

    private final UserRepository repository;

    public CustomUserPrincipalService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<AuthUser> loadByUsername(String username) {
        return repository.findByUsername(username)
                .map(this::mapToAuthUser);
    }

    public boolean registerUser(String username, String password) {
        if (repository.findByUsername(username).isPresent()) {
            return false;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(List.of("ROLE_USER"));
        repository.save(user);

        return true;
    }

    public boolean registerAdmin(String username, String password) {

        if (repository.findByUsername(username).isPresent()) {
            return false;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(List.of("ROLE_ADMIN"));
        repository.save(user);
        return true;
    }

    private AuthUser mapToAuthUser(User user) {
        return new AuthUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
        );
    }
}