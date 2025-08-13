package ru.nntu.avito.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nntu.avito.application.ItemInitializer;
import ru.nntu.avito.application.ItemService;
import ru.nntu.avito.domain.ItemEntity;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemInitializerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemInitializer itemInitializer;

    @Test
    void run_ShouldAddItemsIfDatabaseIsEmpty() {
        // Условие: БД пуста
        when(itemService.all()).thenReturn(Collections.emptyList());

        itemInitializer.run(null);

        // Проверяем, что для каждого товара из ITEMS вызвался itemService.add()
        verify(itemService, times(ItemInitializer.ITEMS.size())).add(anyString(), anyInt());
    }

    @Test
    void run_ShouldNotAddItemsIfDatabaseIsNotEmpty() {
        // Условие: БД не пуста
        when(itemService.all()).thenReturn(List.of(new ItemEntity()));

        itemInitializer.run(null);

        // Проверяем, что add() не вызывался
        verify(itemService, never()).add(anyString(), anyInt());
    }
}
