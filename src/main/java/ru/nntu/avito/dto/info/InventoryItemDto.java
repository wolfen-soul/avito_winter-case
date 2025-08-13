package ru.nntu.avito.dto.info;

public class InventoryItemDto extends BaseItemDto {
    private Integer quantity;

    public InventoryItemDto(String name, Integer quantity) {
        super(name);
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
