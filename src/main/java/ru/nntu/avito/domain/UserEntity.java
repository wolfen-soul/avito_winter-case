package ru.nntu.avito.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private Integer wallet;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private final List<UserItem> inventory = new ArrayList<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TransactionEntity> sentTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TransactionEntity> receivedTransactions = new ArrayList<>();

    public Long getId() { return id; }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getWallet() {
        return wallet;
    }

    public List<UserItem> getInventory() { return inventory; }

    public List<TransactionEntity> getSentTransactions() { return sentTransactions; }

    public List<TransactionEntity> getReceivedTransactions() { return receivedTransactions; }

    public List<String> getRoles() {
        return roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWallet(Integer wallet) {
        this.wallet = wallet;
    }

    public void setSentTransactions(List<TransactionEntity> sentTransactions) { this.sentTransactions = sentTransactions; }

    public void setReceivedTransactions(List<TransactionEntity> receivedTransactions) { this.receivedTransactions = receivedTransactions; }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public static UserEntity create(String username, String password, int wallet) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        user.setWallet(wallet);

        List<String> roles = new ArrayList<>();
        roles.add("USER");
        user.setRoles(roles);
        return user;
    }
}
