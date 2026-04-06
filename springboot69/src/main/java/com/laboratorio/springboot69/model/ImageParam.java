package com.laboratorio.springboot69.model;

public record ImageParam(
        String prompt,
        String model,
        int n,
        int width,
        int height,
        float cfgscale,
        int steps,
        String stylePreset
) {
}