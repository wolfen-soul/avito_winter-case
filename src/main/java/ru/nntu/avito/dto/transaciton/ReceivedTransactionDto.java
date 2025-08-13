package ru.nntu.avito.dto.transaciton;

public class ReceivedTransactionDto {
    private String fromUser;
    private Integer amount;

    public String getFromUser() {
        return fromUser;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
