package ru.nntu.avito.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.nntu.avito.application.UserPrincipalService;
import ru.nntu.avito.domain.UserEntity;
import ru.nntu.avito.repo.UserRepo;
import ru.nntu.avito.security.UserPrincipal;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserPrincipalServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserPrincipalService userPrincipalService;

    @Test
    void loadUserByUsername_Success() {
        UserEntity user = UserEntity.create("user", "encodedPass", 100);
        when(userRepo.findByUsername("user")).thenReturn(Optional.of(user));

        var userDetails = userPrincipalService.loadUserByUsername("user");

        assertThat(userDetails)
                .isInstanceOf(UserPrincipal.class)
                .extracting("username")
                .isEqualTo("user");
    }

    @Test
    void loadUserByUsername_UserNotFound() {
        when(userRepo.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userPrincipalService.loadUserByUsername("unknown"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("User not found");
    }
}
