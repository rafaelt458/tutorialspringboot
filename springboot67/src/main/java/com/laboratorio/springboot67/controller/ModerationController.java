package com.laboratorio.springboot67.controller;

import com.laboratorio.springboot67.service.ModerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ModerationController {
    public final ModerationService moderationService;

    @GetMapping("/moderation")
    public ResponseEntity<String> moderateMessage(@RequestParam(name = "message") String message){
        return ResponseEntity.ok(this.moderationService.getModerationResult(message));
    }
}