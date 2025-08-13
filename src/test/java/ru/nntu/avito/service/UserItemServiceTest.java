package ru.nntu.avito.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nntu.avito.application.UserItemService;
import ru.nntu.avito.domain.*;
import ru.nntu.avito.repo.UserItemRepo;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserItemServiceTest {

    @Mock
    private UserItemRepo userItemRepo;

    @InjectMocks
    private UserItemService userItemService;

    @Test
    void addItemToUser_NewItem() {
        UserEntity user = UserEntity.create("user", "pass", 100);
        ItemEntity item = ItemEntity.create("book", 50);

        when(userItemRepo.findByUserAndItem(user, item)).thenReturn(Optional.empty());

        userItemService.addItemToUser(user, item, 1);

        verify(userItemRepo).save(argThat(ui ->
                ui.getUser().equals(user) &&
                        ui.getItem().equals(item) &&
                        ui.getQuantity() == 1
        ));
    }
}