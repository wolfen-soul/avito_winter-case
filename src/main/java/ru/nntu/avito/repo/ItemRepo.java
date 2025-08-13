package ru.nntu.avito.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nntu.avito.domain.ItemEntity;

import java.util.Optional;

@Repository
public interface ItemRepo extends JpaRepository<ItemEntity, Long> {
    Optional<ItemEntity> findByItemName(String itemName);
}