package ru.nntu.avito.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nntu.avito.application.ItemService;
import ru.nntu.avito.application.PurchaseService;
import ru.nntu.avito.application.UserItemService;
import ru.nntu.avito.application.UserService;
import ru.nntu.avito.domain.*;
import ru.nntu.avito.exception.InsufficientFundsException;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private ItemService itemService;

    @Mock
    private UserItemService userItemService;

    @InjectMocks
    private PurchaseService purchaseService;

    @Test
    void purchase_Success() {
        UserEntity user = UserEntity.create("user", "pass", 200);
        ItemEntity item = ItemEntity.create("book", 50);

        when(userService.get("user")).thenReturn(user);
        when(itemService.get("book")).thenReturn(item);

        purchaseService.purchase("user", "book", 2); // 2 книги по 50 = 100

        assertThat(user.getWallet()).isEqualTo(100);
        verify(userItemService).addItemToUser(user, item, 2);
    }

    @Test
    void purchase_InsufficientFunds() {
        UserEntity user = UserEntity.create("user", "pass", 50);
        ItemEntity item = ItemEntity.create("hoody", 300);

        when(userService.get("user")).thenReturn(user);
        when(itemService.get("hoody")).thenReturn(item);

        assertThatThrownBy(() -> purchaseService.purchase("user", "hoody", 1))
                .isInstanceOf(InsufficientFundsException.class);
    }

    @Test
    void purchase_InvalidQuantity() {
        assertThatThrownBy(() -> purchaseService.purchase("user", "book", 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity must be greater than 0");
    }
}