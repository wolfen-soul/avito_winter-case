package ru.nntu.avito.dto.response;

import java.util.List;

public record JwtResponse(String username, List<String> roles, String token) {}