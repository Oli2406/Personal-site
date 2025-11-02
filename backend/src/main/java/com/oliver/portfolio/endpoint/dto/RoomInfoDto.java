package com.oliver.portfolio.endpoint.dto;

import java.util.List;

public record RoomInfoDto(
    String code,
    List<String> participants,
    List<MessageDto> messages
) {}