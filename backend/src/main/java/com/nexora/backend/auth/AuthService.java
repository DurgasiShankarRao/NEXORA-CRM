package com.nexora.backend.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nexora.backend.entity.User;
import com.nexora.backend.service.UserService;

@Service
public class AuthService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public AuthService(UserService userService,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {

        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {

        User user = userService.getUserByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidLoginException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            throw new InvalidLoginException("Invalid email or password");
        }

        if (!user.getActive()) {

            throw new InvalidLoginException("User account is inactive");
        }

        String role = user.getRole().getName().name();

        String token = jwtService.generateToken(
                user.getId(),
                user.getEmail(),
                role
        );

        return new LoginResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                role,
                token
        );
    }
}