package ru.nntu.avito.application;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nntu.avito.domain.UserEntity;
import ru.nntu.avito.exception.InsufficientFundsException;
import ru.nntu.avito.exception.InsufficientUsernameException;
import ru.nntu.avito.exception.NotFoundException;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CoinTransferService {
    private final UserService userService;
    private final TransactionService transactionService;

    public CoinTransferService(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @Transactional
    public void transferCoins(String senderUsername, String receiverUsername, int amount) {
        UserEntity sender = userService.get(senderUsername);
        UserEntity receiver = userService.get(receiverUsername);

        if (sender == null || receiver == null) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "User not found");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (sender.equals(receiver)) {
            throw new InsufficientUsernameException("Cannot send coins to yourself");
        }
        if (sender.getWallet() < amount) {
            throw new InsufficientFundsException("Insufficient amount of money");
        }

        sender.setWallet(sender.getWallet() - amount);
        receiver.setWallet(receiver.getWallet() + amount);

        userService.edit(sender.getId(), sender);
        userService.edit(receiver.getId(), receiver);

        transactionService.add(amount, sender, receiver);
    }
}
