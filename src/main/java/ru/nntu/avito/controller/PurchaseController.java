package ru.nntu.avito.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.nntu.avito.application.PurchaseService;
import ru.nntu.avito.security.UserPrincipal;

@RestController
@RequestMapping("/api/buy")
@Tag(name = "Purchase API", description = "Buying items")
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/{item}")
    @Operation(summary = "Execute a purchase")
    public ResponseEntity<?> buyItem(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                             @PathVariable("item") String itemName,
                                             @RequestParam("quantity") int quantity) {
        String username = userPrincipal.getUsername();
        purchaseService.purchase(username, itemName, quantity);
        return ResponseEntity.ok("Purchase is completed");
    }
}
