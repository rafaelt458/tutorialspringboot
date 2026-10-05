package com.laboratorio.matricula_service.service.impl;

import com.laboratorio.framework.exception.OpenCircuitException;
import com.laboratorio.framework.exception.OtherRemoteException;
import com.laboratorio.framework.exception.RemoteServiceUnavailableException;
import com.laboratorio.framework.exception.ResourceNotFoundException;
import com.laboratorio.framework.model.dto.EstudianteResponse;
import com.laboratorio.matricula_service.client.EstudianteClient;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstudianteClientService {
    private final EstudianteClient estudianteClient;

    @CircuitBreaker(
            name = "getStudentCB",
            fallbackMethod = "getEstudianteByIdFallback"
    )
    public EstudianteResponse getEstudianteById(Integer id) {
        return this.estudianteClient.getEstudianteById(id);
    }

    public EstudianteResponse getEstudianteByIdFallback(Integer id, Throwable throwable) {
        log.error("Error al obtener el estudiante con id {}. Ejecutando fallback", id, throwable);

        if (throwable instanceof FeignException.NotFound) {
            throw new ResourceNotFoundException("No se ha encontrado el estudiante con id: " + id);
        }

        if (throwable instanceof CallNotPermittedException) {
            String message = "Demasiados intentos, el circuito está abierto";
            throw new OpenCircuitException(message, throwable);
        }

        if (throwable instanceof feign.RetryableException) {
            String message = "No se ha podido realizar la inscripción del estudiante con id " + id + " debido a que el servicio de estudiantes no está disponible en este momento. Por favor, inténtelo más tarde.";
            throw new RemoteServiceUnavailableException(message, throwable);
        }

        throw new OtherRemoteException("Error desconocido al obtener el estudiante con id: " + id, throwable);
    }
}