package ru.nntu.avito.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nntu.avito.application.ItemService;
import ru.nntu.avito.exception.NotFoundException;
import ru.nntu.avito.repo.ItemRepo;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepo itemRepo;

    @InjectMocks
    private ItemService itemService;

    @Test
    void addItem_Success() {
        itemService.add("t-shirt", 80);
        verify(itemRepo).save(argThat(item ->
                item.getItemName().equals("t-shirt") &&
                        item.getPrice() == 80
        ));
    }

    @Test
    void removeItem_NotFound() {
        when(itemRepo.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> itemService.remove(999L))
                .isInstanceOf(NotFoundException.class);
    }
}
