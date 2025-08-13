package ru.nntu.avito.application;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nntu.avito.domain.ItemEntity;
import ru.nntu.avito.exception.NotFoundException;
import ru.nntu.avito.repo.ItemRepo;

import java.util.List;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ItemService {
    private final ItemRepo itemRepo;

    public ItemService(ItemRepo itemRepo) {
        this.itemRepo = itemRepo;
    }

    public ItemEntity get(String itemName) {
        return itemRepo.findByItemName(itemName)
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, "Item not found"));
    }

    public ItemEntity get(Long id) {
        return itemRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, "Item not found"));
    }

    @Transactional
    public void add(String itemName, int price) {
        ItemEntity item = ItemEntity.create(itemName, price);
        itemRepo.save(item);
    }

    @Transactional
    public String remove(Long id) {
        if (!itemRepo.existsById(id)) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "Item not found");
        }
        itemRepo.deleteById(id);
        return "Item successfully deleted.";
    }

    @Transactional
    public void edit(Long id, ItemEntity item) {
        if (!itemRepo.existsById(id)) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "Item not found");
        }
        itemRepo.save(item);
    }

    public List<ItemEntity> all() {
        return itemRepo.findAll();
    }
}

