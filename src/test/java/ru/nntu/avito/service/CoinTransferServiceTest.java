package ru.nntu.avito.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import ru.nntu.avito.application.CoinTransferService;
import ru.nntu.avito.application.TransactionService;
import ru.nntu.avito.application.UserService;
import ru.nntu.avito.domain.UserEntity;
import ru.nntu.avito.exception.*;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CoinTransferServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private CoinTransferService coinTransferService;

    @Test
    void transferCoins_Success() {
        UserEntity sender = UserEntity.create("sender", "pass", 100);
        UserEntity receiver = UserEntity.create("receiver", "pass", 50);

        when(userService.get("sender")).thenReturn(sender);
        when(userService.get("receiver")).thenReturn(receiver);

        coinTransferService.transferCoins("sender", "receiver", 30);

        assertThat(sender.getWallet()).isEqualTo(70);
        assertThat(receiver.getWallet()).isEqualTo(80);
        verify(transactionService).add(30, sender, receiver);
    }

    @Test
    void transferCoins_UserNotFound() {
        when(userService.get("unknown")).thenThrow(new NotFoundException(HttpStatus.NOT_FOUND, "User not found"));

        assertThatThrownBy(() -> coinTransferService.transferCoins("unknown", "receiver", 10))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void transferCoins_InsufficientFunds() {
        UserEntity sender = UserEntity.create("sender", "pass", 10);
        UserEntity receiver = UserEntity.create("receiver", "pass", 50);

        when(userService.get("sender")).thenReturn(sender);
        when(userService.get("receiver")).thenReturn(receiver);

        assertThatThrownBy(() -> coinTransferService.transferCoins("sender", "receiver", 20))
                .isInstanceOf(InsufficientFundsException.class);
    }

    @Test
    void transferCoins_SameUser() {
        UserEntity sender = UserEntity.create("user", "pass", 100);

        when(userService.get("user")).thenReturn(sender);

        assertThatThrownBy(() -> coinTransferService.transferCoins("user", "user", 10))
                .isInstanceOf(InsufficientUsernameException.class);
    }
}