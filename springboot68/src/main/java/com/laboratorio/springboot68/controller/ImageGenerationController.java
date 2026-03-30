package com.laboratorio.springboot68.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ImageGenerationController {
    private final ImageModel imageModel;

    @GetMapping("/image")
    public ResponseEntity<ImageResponse> generateImage(@RequestParam(name = "prompt") String prompt) {
        ImagePrompt imagePrompt = new ImagePrompt(prompt);
        ImageResponse response = this.imageModel.call(imagePrompt);

        return ResponseEntity.ok(response);
    }
}