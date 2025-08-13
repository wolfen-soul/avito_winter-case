package ru.nntu.avito.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nntu.avito.domain.ItemEntity;
import ru.nntu.avito.domain.UserEntity;
import ru.nntu.avito.exception.InsufficientFundsException;

@Service
public class PurchaseService {
    private final UserService userService;
    private final ItemService itemService;
    private final UserItemService userItemService;

    public PurchaseService(UserService userService, ItemService itemService, UserItemService userItemService) {
        this.userService = userService;
        this.itemService = itemService;
        this.userItemService = userItemService;
    }

    @Transactional
    public void purchase(String username, String itemName, int quantity) {
        UserEntity user = userService.get(username);
        ItemEntity item = itemService.get(itemName);

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        int totalPrice = item.getPrice() * quantity;
        if (user.getWallet() < totalPrice) {
            throw new InsufficientFundsException("Insufficient amount of money");
        }

        user.setWallet(user.getWallet() - totalPrice);
        userService.edit(user.getId(), user);

        userItemService.addItemToUser(user, item, quantity);
    }
}
