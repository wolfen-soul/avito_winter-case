package ru.nntu.avito.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nntu.avito.domain.UserEntity;
import ru.nntu.avito.domain.UserItem;
import ru.nntu.avito.dto.info.CoinHistoryDto;
import ru.nntu.avito.dto.info.InventoryItemDto;

import java.util.List;

@Mapper(componentModel = "spring", uses = TransactionMapper.class)
public interface InfoMapper {
    List<InventoryItemDto> mapToInventoryItemDto(List<UserItem> userItems);

    @Mapping(target = "name", source = "item.itemName")
    @Mapping(target = "quantity", source = "quantity")
    InventoryItemDto userItemToInventoryItemDto(UserItem userItem);

    @Mapping(target = "received", source = "receivedTransactions")
    @Mapping(target = "sent", source = "sentTransactions")
    CoinHistoryDto mapToCoinHistory(UserEntity user);
}
