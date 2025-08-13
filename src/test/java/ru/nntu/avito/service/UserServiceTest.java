package ru.nntu.avito.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.nntu.avito.application.UserService;
import ru.nntu.avito.exception.NotFoundException;
import ru.nntu.avito.repo.UserRepo;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void addUser_Success() {
        when(passwordEncoder.encode("123")).thenReturn("encodedPass");
        userService.add("newUser", "123", 100);

        verify(userRepo).save(argThat(user ->
                user.getUsername().equals("newUser") &&
                        user.getPassword().equals("encodedPass") &&
                        user.getWallet() == 100
        ));
    }

    @Test
    void getUser_NotFound() {
        when(userRepo.findByUsername("unknown")).thenThrow(new NotFoundException(HttpStatus.NOT_FOUND, "User not found"));

        assertThatThrownBy(() -> userService.get("unknown"))
                .isInstanceOf(NotFoundException.class);
    }
}