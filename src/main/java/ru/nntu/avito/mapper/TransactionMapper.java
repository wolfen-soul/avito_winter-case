package ru.nntu.avito.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nntu.avito.domain.TransactionEntity;
import ru.nntu.avito.dto.transaciton.ReceivedTransactionDto;
import ru.nntu.avito.dto.transaciton.SentTransactionDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(target = "fromUser", source = "sender.username")
    @Mapping(target = "amount", source = "amount")
    ReceivedTransactionDto toReceivedDto(TransactionEntity transactionEntity);

    @Mapping(target = "toUser", source = "receiver.username")
    @Mapping(target = "amount", source = "amount")
    SentTransactionDto toSentDto(TransactionEntity transactionEntity);

    List<ReceivedTransactionDto> toReceivedDtoList(List<TransactionEntity> transactionEntities);
    List<SentTransactionDto> toSentDtoList(List<TransactionEntity> transactionEntities);
}
