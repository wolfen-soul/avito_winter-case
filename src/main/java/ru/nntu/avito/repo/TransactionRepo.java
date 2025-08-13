package ru.nntu.avito.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nntu.avito.domain.TransactionEntity;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findBySenderId(Long senderId);
    List<TransactionEntity> findByReceiverId(Long receiverId);
}
