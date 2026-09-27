package com.collage.skillplacementportal.controller.user;

import com.collage.skillplacementportal.dto.user.AuthDTO;
import com.collage.skillplacementportal.dto.user.LoginDTO;
import com.collage.skillplacementportal.dto.user.LoginResponseDTO;
import com.collage.skillplacementportal.dto.user.UserResponseDTO;
import com.collage.skillplacementportal.entity.user.User;
import com.collage.skillplacementportal.entity.user.UserRole;
import com.collage.skillplacementportal.security.JwtService;
import com.collage.skillplacementportal.service.user.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public UserResponseDTO register(
            @RequestBody AuthDTO authDTO) {

        UserRole role;

        try {
            role = UserRole.valueOf(
                    authDTO.getRole().toUpperCase()
            );
        } catch (Exception exception) {
            throw new RuntimeException(
                    "Invalid role. Use STUDENT or ADMIN"
            );
        }

        User user = userService.registerUser(
                authDTO.getUsername(),
                authDTO.getPassword(),
                role,
                authDTO.getStudentId()
        );

        return UserResponseDTO.fromUser(user);
    }
    @PostMapping("/login")
    public LoginResponseDTO login(
            @RequestBody LoginDTO loginDTO) {

        User user =
                userService.authenticate(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                );

        String token =
                jwtService.generateToken(
                        user.getUsername(),
                        user.getRole().name()
                );

        return new LoginResponseDTO(
                "Login successful",
                user.getId(),
                user.getUsername(),
                user.getRole().name(),
                token
        );
    }
}