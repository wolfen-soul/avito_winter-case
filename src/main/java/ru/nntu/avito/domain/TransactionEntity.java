package ru.nntu.avito.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private UserEntity receiver;

    public Integer getAmount() {
        return amount;
    }

    public UserEntity getSender() {
        return sender;
    }

    public UserEntity getReceiver() {
        return receiver;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    public void setReceiver(UserEntity receiver) {
        this.receiver = receiver;
    }

    public static TransactionEntity create(int amount, UserEntity sender, UserEntity receiver) {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAmount(amount);
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        return transaction;
    }
}
