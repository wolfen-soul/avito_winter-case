package ru.nntu.avito.dto.info;

public class BaseItemDto {
    private String name;

    public BaseItemDto(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
