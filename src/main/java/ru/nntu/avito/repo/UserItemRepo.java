package ru.nntu.avito.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nntu.avito.domain.ItemEntity;
import ru.nntu.avito.domain.UserEntity;
import ru.nntu.avito.domain.UserItem;
import java.util.Optional;

@Repository
public interface UserItemRepo extends JpaRepository<UserItem, Long> {
    Optional<UserItem> findByUserAndItem(UserEntity user, ItemEntity item);
}