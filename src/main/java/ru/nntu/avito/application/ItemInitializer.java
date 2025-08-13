package ru.nntu.avito.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ItemInitializer implements CommandLineRunner {
    private final ItemService itemService;

    public static final Map<String, Integer> ITEMS = Map.of(
            "t-shirt", 80,
            "cup", 20,
            "book", 50,
            "pen", 10,
            "powerbank", 200,
            "hoody", 300,
            "umbrella", 200,
            "socks", 10,
            "wallet", 50,
            "pink-hoody", 500
    );

    public ItemInitializer(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public void run(String... args) {
        if (itemService.all().isEmpty()) {
            ITEMS.forEach(itemService::add);
        }
    }
}
