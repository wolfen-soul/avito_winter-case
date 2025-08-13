package ru.nntu.avito.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "items")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private Integer price;

    @OneToMany(mappedBy = "item")
    private final List<UserItem> users = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public Integer getPrice() { return price; }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static ItemEntity create(String itemName, int price) {
        ItemEntity item = new ItemEntity();
        item.setItemName(itemName);
        item.setPrice(price);
        return item;
    }
}
