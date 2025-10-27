package com.laboratorio.springboot57.chutes.model;

import java.util.List;

public record ChutesChatRequest(
        String model,
        List<ChutesMessage> messages,
        int max_tokens,
        double temperature,
        int n
) {
}