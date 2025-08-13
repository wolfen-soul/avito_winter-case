package ru.nntu.avito.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nntu.avito.application.TransactionService;
import ru.nntu.avito.domain.TransactionEntity;
import ru.nntu.avito.domain.UserEntity;
import ru.nntu.avito.exception.NotFoundException;
import ru.nntu.avito.repo.TransactionRepo;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepo transactionRepo;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void addTransaction_Success() {
        UserEntity sender = UserEntity.create("sender", "pass", 100);
        UserEntity receiver = UserEntity.create("receiver", "pass", 50);

        transactionService.add(30, sender, receiver);

        verify(transactionRepo).save(argThat(transaction ->
                transaction.getAmount() == 30 &&
                        transaction.getSender().equals(sender) &&
                        transaction.getReceiver().equals(receiver)
        ));
    }

    @Test
    void getSenderTransactions_ReturnsList() {
        UserEntity sender = UserEntity.create("sender", "pass", 100);
        TransactionEntity transaction = TransactionEntity.create(30, sender, new UserEntity());

        when(transactionRepo.findBySenderId(1L)).thenReturn(List.of(transaction));

        List<TransactionEntity> result = transactionService.getSender(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAmount()).isEqualTo(30);
    }

    @Test
    void removeTransaction_NotFound() {
        when(transactionRepo.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> transactionService.remove(999L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Transaction not found");
    }
}
