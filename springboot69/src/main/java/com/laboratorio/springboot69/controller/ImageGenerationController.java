package com.laboratorio.springboot69.controller;

import com.laboratorio.springboot69.model.ImageParam;
import com.laboratorio.springboot69.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.image.ImageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ImageGenerationController {
    private final ImageService imageService;

    @GetMapping("/image")
    public ResponseEntity<ImageResponse> generateImage(@RequestBody ImageParam imageParam) {
        return ResponseEntity.ok(this.imageService.createImage(imageParam));
    }
}