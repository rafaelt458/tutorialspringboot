package com.laboratorio.matricula_service.service.impl;

import com.laboratorio.framework.exception.OpenCircuitException;
import com.laboratorio.framework.exception.OtherRemoteException;
import com.laboratorio.framework.exception.RemoteServiceUnavailableException;
import com.laboratorio.framework.exception.ResourceNotFoundException;
import com.laboratorio.framework.model.dto.CursoResponse;
import com.laboratorio.matricula_service.client.CursoClient;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CursoClientService {
    private final CursoClient cursoClient;

    @CircuitBreaker(
            name = "getCourseCB",
            fallbackMethod = "getCursoByCodigoFallback"
    )
    public CursoResponse getCursoByCodigo(String codigo) {
        return this.cursoClient.getCursoByCodigo(codigo);
    }

    public CursoResponse getCursoByCodigoFallback(String codigo, Throwable throwable) {
        log.error("Error al obtener el curso con código {}. Ejecutando fallback", codigo, throwable);

        if (throwable instanceof FeignException.NotFound) {
            throw new ResourceNotFoundException("No se ha encontrado el curso con código: " + codigo);
        }

        if (throwable instanceof CallNotPermittedException) {
            String message = "Demasiados intentos, el circuito está abierto";
            throw new OpenCircuitException(message, throwable);
        }

        if (throwable instanceof feign.RetryableException) {
            String message = "No se ha podido inscribir el estudiante en el curso con código " + codigo + " debido a que el servicio de cursos no está disponible en este momento. Por favor, inténtelo más tarde.";
            throw new RemoteServiceUnavailableException(message, throwable);
        }

        throw new OtherRemoteException("Error desconocido al obtener el curso con código: " + codigo, throwable);
    }
}