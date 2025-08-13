package ru.nntu.avito.dto.transaciton;

public class SentTransactionDto {
    private String toUser;
    private Integer amount;

    public String getToUser() {
        return toUser;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
