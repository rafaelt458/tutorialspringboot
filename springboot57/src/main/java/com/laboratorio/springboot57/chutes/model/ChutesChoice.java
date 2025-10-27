package com.laboratorio.springboot57.chutes.model;

public record ChutesChoice(
        int index,
        ChutesMessage message,
        String finish_reason,
        int matched_stop
) {
}