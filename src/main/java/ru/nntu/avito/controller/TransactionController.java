package ru.nntu.avito.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nntu.avito.application.CoinTransferService;
import ru.nntu.avito.application.UserService;
import ru.nntu.avito.dto.transaciton.SendCoinRequest;
import ru.nntu.avito.security.UserPrincipal;

@RestController
@RequestMapping("/api/sendCoin")
@Tag(name = "Transaction API", description = "Executing transactions between users")
public class TransactionController {
    private final UserService userService;
    private final CoinTransferService coinTransferService;

    public TransactionController(UserService userService, CoinTransferService coinTransferService) {
        this.userService = userService;
        this.coinTransferService = coinTransferService;
    }

    @PostMapping
    @Operation(summary = "Execute a transaction")
    public ResponseEntity<?> sendCoin(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody SendCoinRequest request) {
        String senderUsername = userPrincipal.getUserEntity().getUsername();
        coinTransferService.transferCoins(senderUsername, userService.get(request.getUser()).getUsername(), request.getAmount());
        return ResponseEntity.ok("Transaction is completed");
    }
}
