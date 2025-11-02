package com.oliver.portfolio.endpoint.dto;

import java.time.Instant;

public record MessageDto(
    String sender,
    String content,
    Instant timestamp
) {}
