package ru.nntu.avito.application;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nntu.avito.domain.ItemEntity;
import ru.nntu.avito.domain.UserEntity;
import ru.nntu.avito.domain.UserItem;
import ru.nntu.avito.exception.NotFoundException;
import ru.nntu.avito.repo.ItemRepo;
import ru.nntu.avito.repo.UserItemRepo;

import java.util.List;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UserItemService {
    private final UserItemRepo userItemRepo;

    public UserItemService(UserItemRepo userItemRepo) {
        this.userItemRepo = userItemRepo;
    }

    @Transactional
    public UserItem createNewUserItem(UserEntity user, ItemEntity item) {
        return UserItem.create(user, item);
    }

    @Transactional
    public void addItemToUser(UserEntity user, ItemEntity item, int quantity) {
        UserItem userItem = userItemRepo.findByUserAndItem(user, item)
                .orElseGet(() -> createNewUserItem(user, item));

        userItem.setQuantity(userItem.getQuantity() + quantity);
        userItemRepo.save(userItem);
    }
}
