package com.laboratorio.springboot57.chutes.model;

import java.util.List;

public record ChutesChatResponse(
        String id,
        String object,
        long created,
        String model,
        List<ChutesChoice> choices,
        ChutesUsage usage
) {
}