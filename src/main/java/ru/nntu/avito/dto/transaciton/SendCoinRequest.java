package ru.nntu.avito.dto.transaciton;

public class SendCoinRequest {
    private String user;
    private Integer amount;

    public String getUser() {
        return user;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
