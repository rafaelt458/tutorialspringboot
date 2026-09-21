package com.laboratorio.matricula_service.client;

import com.laboratorio.matricula_service.model.dto.CursoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "curso-service",
        url = "${curso-service.url}"
)
public interface CursoClient {
    @GetMapping("/api/cursos/{codigo}")
    CursoResponse getCursoByCodigo(@PathVariable(value = "codigo") String codigo);
}