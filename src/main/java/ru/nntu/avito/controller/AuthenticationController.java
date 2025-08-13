package ru.nntu.avito.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.nntu.avito.application.UserService;
import ru.nntu.avito.domain.UserEntity;
import ru.nntu.avito.dto.request.LoginRequest;
import ru.nntu.avito.dto.request.SignupRequest;
import ru.nntu.avito.dto.response.JwtResponse;
import ru.nntu.avito.exception.InsufficientUsernameException;
import ru.nntu.avito.security.JwtTokenProvider;
import ru.nntu.avito.security.UserPrincipal;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication API", description = "User registration/authentication with JWT")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    UserService userService,
                                    JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/signin")
    @Operation(summary = "Authenticate user in system and receive token")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(
                userPrincipal.getUsername(),
                userPrincipal.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority)
                        .toList(),
                jwt));
    }

    @PostMapping("/signup")
    @Operation(summary = "Register new user in system")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userService.existsByUsername(signupRequest.getUsername())) {
            throw new InsufficientUsernameException("User with that username already exists");
        }

        userService.add(signupRequest.getUsername(), signupRequest.getPassword(), 1000);

        return ResponseEntity.ok("User registered successfully!");
    }
}