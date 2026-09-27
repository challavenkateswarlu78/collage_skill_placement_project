package com.collage.skillplacementportal.service.user;

import com.collage.skillplacementportal.entity.user.User;
import com.collage.skillplacementportal.entity.user.UserRole;
import com.collage.skillplacementportal.repository.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register new user
    public User registerUser(
            String username,
            String password,
            UserRole role
            ,Long studentId) {

        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException(
                    "Username already exists"
            );
        }

        User user = new User();

        user.setUsername(username);

        user.setPassword(
                passwordEncoder.encode(password)
        );

        user.setRole(role);

        user.setStudentId(studentId);

        return userRepository.save(user);
    }

    // Find user by username
    public User findByUsername(
            String username) {

        return userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );
    }

    public User authenticate(
            String username,
            String password) {

        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid username or password"
                                )
                        );

        if (!passwordEncoder.matches(
                password,
                user.getPassword())) {

            throw new RuntimeException(
                    "Invalid username or password"
            );
        }

        return user;
    }
}
