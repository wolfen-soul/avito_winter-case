package ru.nntu.avito.application;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nntu.avito.domain.TransactionEntity;
import ru.nntu.avito.domain.UserEntity;
import ru.nntu.avito.exception.NotFoundException;
import ru.nntu.avito.repo.TransactionRepo;

import java.util.List;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TransactionService {
    private final TransactionRepo transactionRepo;

    public TransactionService(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    public List<TransactionEntity> getSender(Long userId) {
        return transactionRepo.findBySenderId(userId);

    }

    public List<TransactionEntity> getReceiver(Long userId) {
        return transactionRepo.findByReceiverId(userId);

    }

    @Transactional
    public void add(int amount, UserEntity sender, UserEntity receiver) {
        TransactionEntity transaction = TransactionEntity.create(amount, sender, receiver);
        transactionRepo.save(transaction);
    }

    @Transactional
    public String remove(Long id) {
        if (!transactionRepo.existsById(id)) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "Transaction not found");
        }
        transactionRepo.deleteById(id);
        return "Transaction successfully deleted.";
    }

    @Transactional
    public void edit(Long id, TransactionEntity transaction) {
        if (!transactionRepo.existsById(id)) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "Transaction not found");
        }
        transactionRepo.save(transaction);
    }
}

