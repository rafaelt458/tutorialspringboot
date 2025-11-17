package com.laboratorio.springboot60.controller;

import com.laboratorio.springboot60.modelo.DialogRequest;
import com.laboratorio.springboot60.modelo.DialogResponse;
import com.laboratorio.springboot60.service.DialogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {
    private final DialogService dialogService;

    @PostMapping("/dialog")
    public ResponseEntity<DialogResponse> processInput(@RequestBody DialogRequest request) {
        String userId = request.userId() == null ?
                UUID.randomUUID().toString() : request.userId();

        DialogResponse response = this.dialogService.getDialogResponse(new DialogRequest(userId, request.message()));

        return ResponseEntity.ok(response);
    }
}