package ru.nntu.avito.dto.info;

import java.util.List;

public class InfoResponse {
    private int coins;
    private List<InventoryItemDto> inventory;
    private CoinHistoryDto coinHistory;

    public int getCoins() {
        return coins;
    }

    public List<InventoryItemDto> getInventory() {
        return inventory;
    }

    public CoinHistoryDto getCoinHistory() {
        return coinHistory;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setInventory(List<InventoryItemDto> inventory) {
        this.inventory = inventory;
    }

    public void setCoinHistory(CoinHistoryDto coinHistory) {
        this.coinHistory = coinHistory;
    }


    public static class Builder {
        private int coins;
        private List<InventoryItemDto> inventory;
        private CoinHistoryDto coinHistory;

        public Builder coins(int coins) {
            this.coins = coins;
            return this;
        }

        public Builder inventory(List<InventoryItemDto> inventory) {
            this.inventory = inventory;
            return this;
        }

        public Builder coinHistory(CoinHistoryDto coinHistory) {
            this.coinHistory = coinHistory;
            return this;
        }

        public InfoResponse build() {
            InfoResponse response = new InfoResponse();
            response.setCoins(this.coins);
            response.setInventory(this.inventory);
            response.setCoinHistory(this.coinHistory);
            return response;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
