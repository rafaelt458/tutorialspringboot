package com.laboratorio.springboot57.chutes.model;

public record ChutesUsage(
        int prompt_tokens,
        int total_tokens,
        int completion_tokens,
        int reasoning_tokens
) {
}