package ru.nntu.avito.dto.info;

import ru.nntu.avito.dto.transaciton.ReceivedTransactionDto;
import ru.nntu.avito.dto.transaciton.SentTransactionDto;

import java.util.List;

public class CoinHistoryDto {
    private List<ReceivedTransactionDto> received;
    private List<SentTransactionDto> sent;

    public List<ReceivedTransactionDto> getReceived() {
        return received;
    }

    public List<SentTransactionDto> getSent() {
        return sent;
    }

    public void setReceived(List<ReceivedTransactionDto> received) {
        this.received = received;
    }

    public void setSent(List<SentTransactionDto> sent) {
        this.sent = sent;
    }
}
