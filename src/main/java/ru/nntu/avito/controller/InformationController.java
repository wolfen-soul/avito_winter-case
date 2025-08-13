package ru.nntu.avito.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.nntu.avito.domain.UserEntity;
import ru.nntu.avito.dto.info.CoinHistoryDto;
import ru.nntu.avito.dto.info.InfoResponse;
import ru.nntu.avito.mapper.InfoMapper;
import ru.nntu.avito.security.UserPrincipal;

@RestController
@RequestMapping("/api/info")
@Tag(name = "Information API", description = "User's information about finances and items")
public class InformationController {
    private final InfoMapper infoMapper;

    public InformationController(InfoMapper infoMapper) {
        this.infoMapper = infoMapper;
    }

    @GetMapping
    @Operation(summary = "Receive an information about authenticated user")
    public ResponseEntity<InfoResponse> getInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        UserEntity user = userPrincipal.getUserEntity();

        CoinHistoryDto coinHistory = infoMapper.mapToCoinHistory(user);
        InfoResponse response = InfoResponse.builder()
                .coins(user.getWallet())
                .inventory(infoMapper.mapToInventoryItemDto(user.getInventory()))
                .coinHistory(coinHistory)
                .build();
        return ResponseEntity.ok(response);
    }
}
