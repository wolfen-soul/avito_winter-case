package ru.nntu.avito.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "user_items")
public class UserItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private ItemEntity item;

    public Integer getQuantity() {
        return quantity;
    }

    public UserEntity getUser() {
        return user;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public static UserItem create(UserEntity user, ItemEntity item) {
        UserItem userItem = new UserItem();
        userItem.setUser(user);
        userItem.setItem(item);
        userItem.setQuantity(0);
        return userItem;
    }
}
