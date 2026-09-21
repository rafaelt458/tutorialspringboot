package com.laboratorio.matricula_service.client;

import com.laboratorio.matricula_service.model.dto.EstudianteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "estudiante-service",
        url = "${estudiante-service.url}"
)
public interface EstudianteClient {
    @GetMapping("/api/estudiantes/{id}")
    EstudianteResponse getEstudianteById(@PathVariable(value = "id") Integer id);
}